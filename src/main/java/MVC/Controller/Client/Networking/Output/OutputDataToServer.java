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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static MVC.StartServer.taskManager;

public class OutputDataToServer {
    private SocketDataOutput socketDataOutput;
    private UserInputReceiver userInputReceiver;
    private IDManager idManager;
    private SaveToFile saveToFile;
    private BlockingQueue<String> rawMessageQueue;

    public OutputDataToServer(SocketDataOutput socketDataOutput, UserInputReceiver userInputReceiver) {
        this.socketDataOutput = socketDataOutput;
        this.userInputReceiver = userInputReceiver;
        this.idManager = new IDManagerImplementation();
        this.saveToFile = new SaveToFileImplementation();
        this.rawMessageQueue = new LinkedBlockingQueue<>();
    }

    public void start(Socket serverSocket) throws InterruptedException {
        new Thread(() -> receiveAndSendMessages(serverSocket)).start();
        Runnable initializeID = new Runnable() {
            @Override
            public void run() {
                File file = new File(Data.getFilePath());
                while (true) {
                    try {
                        String messageToSend = rawMessageQueue.take();
                        int biggestID = idManager.updateMaxReceivedId(file);
                        String fullMessage = biggestID + "." + " " + messageToSend;

                        if (!messageToSend.contains("1")) {
                            saveToFile.saveMessageToFile(file, fullMessage);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        taskManager.submitTask(initializeID);
    }

    private void receiveAndSendMessages(Socket serverSocket) {
        try (BufferedReader userInput = userInputReceiver.getData()) {
            while (true) {
                String messageToSend = UserNameManager.getInstance().getUsername() + " : " + userInput.readLine();

                socketDataOutput.sendData(serverSocket, messageToSend);

                rawMessageQueue.put(messageToSend);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


