package MVC.Model;


import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    private final static ArrayList<Socket> clientSockets = new ArrayList<>();
    private final static int PORT = 8080;
    private final static String filePath = "E:\\Save.txt";
    private int startLine = 0;
    private final static AtomicInteger currentID2 = new AtomicInteger(0);

    public static synchronized void testMethod(String input) {
        System.out.println(input);
    }

    public synchronized String updateMaxReceivedId(int currentID, String input) {
        //  ++currentID;
        currentID2.set(currentID);
        currentID2.incrementAndGet();
        String fullMessage = currentID2 + ". " + input;
        System.out.println(fullMessage);
        return fullMessage;
    }

    public static ArrayList<Socket> getClientSockets() {
        return clientSockets;
    }

    public static int getPORT() {
        return PORT;
    }

    public static String getFilePath() {
        return filePath;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }
}


