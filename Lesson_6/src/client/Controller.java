package src.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller {


    @FXML
    Button btnPass;
    @FXML
    TextField regNickField;
    @FXML
    PasswordField regPassField;
    @FXML
    TextField regLogField;
    @FXML
    HBox regPanel;
    @FXML
    TextField messageField;
    @FXML
    TextArea chatArea;
    @FXML
    Button btn1;
    @FXML
    TextField loginField;
    @FXML
    HBox bottomPanel;
    @FXML
    HBox upperPanel;
    @FXML
    TextField loginFiled;
    @FXML
    PasswordField passwordField;
    @FXML
    ListView<String> clientList;


    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;
    private boolean isAuthorized;

    List<TextArea> textAreas;

    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        textAreas = new ArrayList<>();
        textAreas.add(chatArea);
    }

    private void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);

            clientList.setVisible(false);
            clientList.setManaged(false);
            regPanel.setVisible(true);
            regPanel.setManaged(true);

        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);

            clientList.setVisible(true);
            clientList.setManaged(true);
            regPanel.setVisible(false);
            regPanel.setManaged(false);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            Thread thread = new Thread(() -> {

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                try {
                    //authorized
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/authok")) {
                            setAuthorized(true);
                            break;
                        } else {
//                            messageField.appendText(str + "\n");
                            for (TextArea o : textAreas) {
                                o.appendText(str + "\n");
                            }
                        }
                    }
                    //блок для разбора сообщений
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/serverClosed")) {
                                break;
                            }
                            if (str.startsWith("/clientList")) {
                                String[] tokens = str.split(" ");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    }
                                });
//                                chatArea.appendText(str + "\n");
                            }
                        } else {
                            chatArea.appendText(str + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }
            });
            thread.setDaemon(true);
            thread.start();
//                }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод для авторизации
    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            // сначала подключаемся к серверу

            connect();
        }
        try {
            // отправка сообщений на сервер для авторизации

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
            out.writeUTF(messageField.getText());
            messageField.clear();
            messageField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF("/addUser " + regLogField.getText() + " " + regPassField.getText() + " " + regNickField.getText());
            regNickField.clear();
            regLogField.clear();
            regPassField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            MiniStage ms = new MiniStage(clientList.getSelectionModel().getSelectedItem(), out, textAreas);
            ms.show();
        }
    }
}
