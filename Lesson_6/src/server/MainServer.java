package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServer {

    private Vector<ClientHandler> clients;

    public MainServer() throws SQLException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            AuthService.connect();

            // System.out.println(AuthService.getNickByLoginAndPass("login12", "pass1"));

            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    // подписываем клиента на рассылку
    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    // отписываем клиента от рассылки сообщений
    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void broadCastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public void sendPersonalMsg(ClientHandler clientHandler, String token, String token1) {

    }
}
