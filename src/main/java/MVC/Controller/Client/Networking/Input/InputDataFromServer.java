package MVC.Controller.Client.Networking.Input;

import MVC.Service.InterfaceService.String.ParseString;
import MVC.Service.InterfaceService.IO.SocketInputReader;
import MVC.Service.ServiceImplenments.String.ParseStringImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class InputDataFromServer {
    private BufferedReader inFromServer;
    private SocketInputReader socketInputReader;
    private ParseString parseString;

    public InputDataFromServer(SocketInputReader socketInputReader) {
        this.socketInputReader = socketInputReader;
        this.parseString = new ParseStringImplementation();
    }

    public void receiveData(Socket socket) throws IOException {
        inFromServer = socketInputReader.getData(socket);
        new Thread(() -> {
            try {
                String messageFromServer;
                int currentMessageId;
                Integer historySize = 0;
                Map<Integer, String> map = new TreeMap<>();

                while ((messageFromServer = inFromServer.readLine()) != null) {

                    if (historySize == 0 && messageFromServer.contains("History Size:")) {
                        historySize = parseString.getHistorySize(messageFromServer);
                        continue;
                    }

                    currentMessageId = parseString.getIDMessage(messageFromServer);

                    map.put(currentMessageId, messageFromServer);

                    long historyCount = 0;
                    for (String value : map.values()) {
                        if (value.contains("Old message")) {
                            historyCount++;
                        }
                    }

                    if (historyCount >= historySize) {
                        historySize = 0;
                        printMessage(map);
                    } else if (historyCount == 0) {
                        printMessage(map);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void printMessage(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer id = entry.getKey();
            String message = entry.getValue();
            System.out.println(message);
        }
        map.clear();
    }
}

