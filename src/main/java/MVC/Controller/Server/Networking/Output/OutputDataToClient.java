package MVC.Controller.Server.Networking.Output;

import MVC.Model.Data;
import MVC.Service.Enum.Status;
import MVC.Service.InterfaceService.File.ParseFile;
import MVC.Service.InterfaceService.IO.SocketDataOutput;
import MVC.Service.InterfaceService.Log.ReadLogServer;
import MVC.Service.InterfaceService.String.ParseString;
import MVC.Service.LazySingleton.ID.IDManager;
import MVC.Service.LazySingleton.Status.StatusManager;
import MVC.Service.ServiceImplenments.File.ParseFileImplementation;
import MVC.Service.ServiceImplenments.String.ParseStringImplementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class OutputDataToClient {
    private SocketDataOutput socketDataOutput;
    private ReadLogServer readLogServer;
    //private Data data;
    private Data shareData;
    private ParseFile parseFile;
    private ParseString parseString;
    private File file = new File(Data.getFilePath());

    public OutputDataToClient(SocketDataOutput socketDataOutput, ReadLogServer readLogServer, Data shareData) {
        this.socketDataOutput = socketDataOutput;
        this.readLogServer = readLogServer;
        //  this.data = data;
        this.shareData = shareData;
        this.parseFile = new ParseFileImplementation();
        this.parseString = new ParseStringImplementation();
    }

    public void sendData(Socket clientSocket, BufferedReader inFromClient) {
        String messageFromClient;

        try {
            while ((messageFromClient = inFromClient.readLine()) != null) {


                if (messageFromClient.contains("- request history data")) {
                    StatusManager.getInstance().setCurrentStatus(Status.LOADING);
                    List<String> listChatHistory = readLogServer.read(shareData);

                    socketDataOutput.sendData(clientSocket, "History Size: " + listChatHistory.size());

                    for (String message : listChatHistory) {
                        socketDataOutput.sendData(clientSocket, "Old message ( " + message + " )");
                    }

                } else {
                    for (Socket socket : Data.getClientSockets()) {
                        if (socket != clientSocket) {

                            String ID = null;
                            String fullMessage;

                            if (file.length() == 0) {
                                IDManager.getInstance().updateMaxReceivedId(1);
                                ID = IDManager.getInstance().getMaxReceivedId() + "." + " ";
                            } else if (file.length() >= 1) {
                                fullMessage = shareData.updateMaxReceivedId(parseFile.getBiggestID(file), messageFromClient);
                                socketDataOutput.sendData(socket, fullMessage);

                            }

                            fullMessage = ID + messageFromClient;
                            System.out.println(fullMessage);
                            socketDataOutput.sendData(socket, fullMessage);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
