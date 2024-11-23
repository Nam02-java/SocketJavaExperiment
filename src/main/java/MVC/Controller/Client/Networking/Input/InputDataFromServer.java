package MVC.Controller.Client.Networking.Input;

import MVC.Service.InterfaceService.File.ParseFile;
import MVC.Service.InterfaceService.String.ParseString;
import MVC.Service.InterfaceService.IO.SocketInputReader;
import MVC.Service.ServiceImplenments.File.ParseFileImplementation;
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
    private ParseFile parseFile;

    public InputDataFromServer(SocketInputReader socketInputReader) {
        this.socketInputReader = socketInputReader;
        this.parseString = new ParseStringImplementation();
        this.parseFile = new ParseFileImplementation();
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

                    long historyCount = map.values().stream().filter(value -> value.contains("Old message")).count();

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
        map.forEach((id, message) -> System.out.println(id + ": " + message));
        map.clear();
    }
}


//    if (currentMessageId < saveID) {
//                        System.out.println(messageFromServer);
//                        continue;
//                    } else if (currentMessageId > saveID) {
//                        System.out.println(messageFromServer);
//                    }
//
//                    saveID = currentMessageId;

//  if (messageFromServer.contains("Old message")) {
//                        /**
//                         * count+=1 to 4 represents the distance betwee
//                         * each load of history (each load of 5 messages)
//                         */
//                        count += 1;
//                        if (count == 5) {
//                            StatusManager.getInstance().setCurrentStatus(Status.RELAX);
//                            count = 0;
//                        }
//                    }
//
//                    currentMessageId = parseString.getIDMessage(messageFromServer);
//                    messageMap.put(currentMessageId, messageFromServer);
//
//                    if (StatusManager.getInstance().getCurrentStatus() == Status.RELAX) {
//                        for (String message : messageMap.values()) {
//                            System.out.println(message);
//                        }
//                        messageMap.clear();
//                    }