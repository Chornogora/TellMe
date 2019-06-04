package controller.network;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

@ServerEndpoint(value = "/point")
public class ChatSocket extends WebSocketServer {
    public String hostName;
    private final ChatController controller;
    private final int port;
    private Set<WebSocket> connections;

    public ChatSocket(String hostName, int p, ChatController cc) throws BindException{
        super(new InetSocketAddress(hostName, p));
        port = p;
        connections = new HashSet<>();
        controller = cc;
    }

    public void onStart() {
        System.out.println("Socket Server has started");
    }

    @OnOpen
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        System.out.println("New user: " + conn.getRemoteSocketAddress());
    }

    @OnClose
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        System.out.println("User left: " + conn.getRemoteSocketAddress());

        if(connections.size() == 0){
            close();
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        class Message{
            int chatId;
            int userId;
            String message;
        }

        Message mess = new Message();
        try(Scanner sc = new Scanner(message)){
            mess.chatId = sc.nextInt();
            mess.userId = sc.nextInt();
            StringBuilder builder = new StringBuilder();
            while(sc.hasNext()) {
                builder.append(sc.next()).append(' ');
            }
            mess.message = builder.toString();
        }

        String messageJson = controller.addMessage(mess.chatId, mess.userId, mess.message);

        for (WebSocket sock : connections) {
            sock.send(messageJson);
        }
    }

    @OnError
    public void onError(WebSocket conn, Exception e) {
        System.out.println("Something went wrong: ");
        e.printStackTrace();
    }

    private void close(){
        SocketDispatcher.freePort(port);
        try {
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}