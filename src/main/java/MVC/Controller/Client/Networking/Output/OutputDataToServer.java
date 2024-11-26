package MVC.Controller.Client.Networking.Output;

import MVC.Model.Data;
import MVC.Service.InterfaceService.File.SaveToFile;
import MVC.Service.InterfaceService.ID.IDManager;
import MVC.Service.InterfaceService.IO.SocketDataOutput;
import MVC.Service.InterfaceService.IO.UserInputReceiver;
import MVC.Service.LazySingleton.UserName.UserNameManager;
import MVC.Service.ServiceImplenments.File.SaveToFileImplementation;
import MVC.Service.ServiceImplenments.ID.IDManagerImplementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static MVC.StartServer.taskManager;

public class OutputDataToServer {
    private SocketDataOutput socketDataOutput;
    private UserInputReceiver userInputReceiver;
    private IDManager idManager;
    private SaveToFile saveToFile;

    public OutputDataToServer(SocketDataOutput socketDataOutput, UserInputReceiver userInputReceiver) {
        this.socketDataOutput = socketDataOutput;
        this.userInputReceiver = userInputReceiver;
        this.idManager = new IDManagerImplementation();
        this.saveToFile = new SaveToFileImplementation();
    }

    public void sendData(Socket serverSocket) throws IOException, InterruptedException {

        BufferedReader userInput = userInputReceiver.getData();
        File file = new File(Data.getFilePath());

        while (true) {

            String messageToSend = UserNameManager.getInstance().getUsername() + " : " + userInput.readLine();
            Runnable task = new Runnable() {
                @Override
                public void run() {

                    int biggestID = idManager.updateMaxReceivedId(file);
                    String fullMessage = biggestID + "." + " " + messageToSend;

                    if (messageToSend.contains("1")) {
                        /**
                         * request history data
                         * no needed save to file
                         */
                    } else {
                        saveToFile.saveMessageToFile(file, fullMessage);
                    }

                    try {
                        socketDataOutput.sendData(serverSocket, fullMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            taskManager.submitTask(task);
        }
    }
}

