package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    Button btn1;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public TextField loginField;
    @FXML
    HBox bottomPanel;
    @FXML
    HBox upperPanel;
    @FXML
    TextField loginFiled;
    @FXML
    PasswordField passwordField;
    final String IP_ADRESS = "localhost";
    final int PORT = 8189;
    private boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //authorized
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                setAuthorized(true);
                                break;
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }
                        //блок для разбора сообщений
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/serverClosed")) {
                                break;
                            }
                            textArea.appendText(str + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод для авторизации
    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {            // сначала подключаемся к серверу

            connect();
        }
        try {            // отправка сообщений на сервер для авторизации

            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // метод для отправки сообщений

    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
