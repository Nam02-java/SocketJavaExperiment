package MVC.Controller.Client.Networking.Output;

import MVC.Model.Data;
import MVC.Service.InterfaceService.File.ParseFile;
import MVC.Service.InterfaceService.IO.SocketDataOutput;
import MVC.Service.InterfaceService.IO.UserInputReceiver;
import MVC.Service.LazySingleton.UserName.UserNameManager;
import MVC.Service.ServiceImplenments.File.ParseFileImplementation;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static MVC.StartServer.shareData;

public class OutputDataToServer {
    private SocketDataOutput socketDataOutput;
    private UserInputReceiver userInputReceiver;
    private ParseFile parseFile;
    private File file = new File(Data.getFilePath());


    public OutputDataToServer(SocketDataOutput socketDataOutput, UserInputReceiver userInputReceiver) {
        this.socketDataOutput = socketDataOutput;
        this.userInputReceiver = userInputReceiver;
        this.parseFile = new ParseFileImplementation();

    }

    public void sendData(Socket serverSocket) throws IOException, InterruptedException {
        BufferedReader userInput = userInputReceiver.getData();
        while (true) {

            String messageToSend = UserNameManager.getInstance().getUsername() + " : " + userInput.readLine();

            if (messageToSend.contains("1")) {
                messageToSend = UserNameManager.getInstance().getUsername() + " : - request history data";
            }

            shareData.testMethod(messageToSend);

            socketDataOutput.sendData(serverSocket, messageToSend);
        }
    }
}


