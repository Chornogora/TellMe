package controller.network;

import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;

class SocketDispatcher {

    private static String hostName;

    private static LinkedList<Integer> ports;
    //pairs port - chatId
    private static Map<Integer, Long> chats = new HashMap<>();

    static{
        ports = new LinkedList<>();

        try {
            Properties props = new Properties();
            props.load(new FileReader("target/classes/serverProps.properties"));
            hostName=props.getProperty("host");
        }catch(IOException e){
            System.out.println("Cannot find properties");
            hostName = "localhost";
        }

        //<Checking port>
        boolean connected;
        for(int i = 1200; i < 65535; ++i) {
            try(ServerSocket ss = new ServerSocket()){
                ss.bind(new InetSocketAddress(InetAddress.getByName(hostName), i), 1);
                ports.addLast(i);
            }catch (IOException ex) {
                continue;
            }
        }
        //</Checking port>
    }

    static int createChatSocket(ChatController controller, long chatId){
        try {
            if(ports.size() == 0){
                return -1;
            }
            int port = ports.removeFirst();
            ChatSocket wss = new ChatSocket(hostName, port, controller);
            wss.start();
            chats.put(port, chatId);
            return port;
        }catch(BindException e){
            return createChatSocket(controller, chatId);
        }
    }

    static int getChatPort(long chatId){
        Optional<Integer> opt = chats.keySet().stream().reduce((acc, key)->{
                if(chats.get(key) == chatId) {
                    acc = key;
                }
                return acc;
            });
        return opt.orElse(-1);
    }

    static void freePort(int port){
        ports.addLast(port);
        chats.remove(port);
    }
}
