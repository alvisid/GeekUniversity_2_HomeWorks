package src.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private MainServer server;
    private String nick;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        //authorization
                        while (true) {
                            String str = in.readUTF();
                            //егистрация пользователя
                            if (str.startsWith("addUser")) {
                                String[] tokens = str.split(" ");
                                AuthService.addUser(tokens[1], tokens[2], tokens[3]);
                                sendMsg("Регистрация пользователя прошла успешно");
                            }
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    if (!server.isNickBusy(newNick)) {
                                        sendMsg("/authok");
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Учетная запись уже используется.");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль.");
                                }
                            }
                        }
                        //send message
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.startsWith("/end")) {
                                    out.writeUTF("/serverClosed");
                                    break;
                                }
                                if (str.startsWith("/w")) {
                                    String[] tokens = str.split(" ", 3);
                                    String m = str.substring(tokens[1].length() + 4);
                                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                            } else {
                                server.broadCastMsg(ClientHandler.this, nick + " " + str);
                            }
                            System.out.println("Client: " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    server.unsubscribe(ClientHandler.this);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
