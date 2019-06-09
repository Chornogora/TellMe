package controller.network;

import model.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

@ServerEndpoint(value = "/tpoint")
public class TournamentSocket extends WebSocketServer {
    private final int port;
    private Set<WebSocket> connections;
    private TournamentController controller;

    private SimpleUser creator;
    private SimpleUser enemy;
    private int timeInterval;
    private int numAnswered;
    private Tournament tournament;
    private List<Test> tests;
    private Test currentTest;
    private STATUS status;

    public enum STATUS{
        NEW, WAITING, STARTED, FINISHED
    }

    private class Timer extends Thread {

        @Override
        public void run(){
            for(int i = timeInterval; i >= 0; --i){
                for(WebSocket socket : connections){
                    socket.send("time:" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

        }
    }

    public TournamentSocket(String hostName, int p, TournamentController cont, SimpleUser user){
        super(new InetSocketAddress(hostName, p));
        port = p;
        connections = new HashSet<>();
        controller = cont;
        creator = user;

        Properties props = new Properties();
        try {
            props.load(new FileReader(new File("serverProps.properties")));
            timeInterval = Integer.parseInt(props.getProperty("timer"));
        } catch (IOException e) {
            System.out.println("Cannot find properties");
            //default value
            timeInterval = 30;
        }

        this.status = STATUS.NEW;
    }

    public void addEnemy(SimpleUser user){
        enemy = user;
        createTests();
        tournament = new Tournament(creator, enemy);
    }

    private void createTests() {
        Iterable<Lesson> lessons = controller.lRepo.findAll();
        List<Test> allTests = new ArrayList<>();
        for(Lesson l : lessons){
            if(l.getLevel() == getLevel()){
                List<Task> lst = l.getTasks();
                for(Task task : lst){
                    if(task instanceof Test){
                        allTests.add(new Test((Test)task));
                    }
                }
            }
        }

        if(allTests.size() <= 10){
            tests = allTests;
        }else{
            for(int i = 0; i < 10; ++i){
                int index = new Random().nextInt() % allTests.size();
                tests.add(allTests.get((index < 0) ? -index : index));
            }
        }

    }

    private void startTournament(){
        for(int i = 0; i < tests.size()/2; ++i){
            currentTest = tests.get(i);
            for(WebSocket socket : connections){
                socket.send("test:" + util.JSONparser.toJSON(currentTest));
            }
            Timer timer = new Timer();
            timer.start();
            try {
                timer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStart() {
        System.out.println("Socket Server has started");
    }

    @OnOpen
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        switch(status){
            case NEW:
                status = STATUS.WAITING;
                conn.send("status:waiting");
                break;
            case WAITING:
                status = STATUS.STARTED;
                for(WebSocket s : connections){
                    s.send("status:started");
                }
                startTournament();
                break;
            default:
                conn.send("Lobby Overloaded");
                return;
        }
        connections.add(conn);
        System.out.println("New user: " + conn.getRemoteSocketAddress());
    }

    @OnClose
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        System.out.println("User left: " + conn.getRemoteSocketAddress());

        if(connections.isEmpty()){
            close();
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String[] request = message.split(":");
        switch(request[0]){

        }
    }

    @OnError
    public void onError(WebSocket conn, Exception e) {
        System.out.println("Something went wrong: ");
        e.printStackTrace();
    }

    private void close(){
        System.out.println("Server Socket has closed");
        SocketDispatcher.freeTournamentPort(port);
        try {
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public STATUS getStatus(){
        return status;
    }

    public Levels getLevel(){
        return creator.getLevel();
    }
}