package MVC.Model;


import MVC.Service.InterfaceService.File.ParseFile;
import MVC.Service.ServiceImplenments.File.ParseFileImplementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Data {
    private final static ArrayList<Socket> clientSockets = new ArrayList<>();
    private final static int PORT = 8080;
    private final static String filePath = "E:\\Save.txt";
    private int startLine = 0;
    private ParseFile parseFile;
    private File file = new File(Data.getFilePath());

    public Data() {
        this.parseFile = new ParseFileImplementation();
    }



    public String updateMaxReceivedId(String input) {
        int biggestID = parseFile.getBiggestID(file);
        biggestID += 1;
        String fullMessage = biggestID + "." + " " + input;
        saveMessageToFile(fullMessage);
        return fullMessage;
    }
    public void saveMessageToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


