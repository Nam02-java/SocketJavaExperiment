package MVC;

import MVC.Controller.Server.ServerManager;
import MVC.Model.Data;
import MVC.Service.InterfaceService.Log.ConsoleLoggerServer;
import MVC.Service.ServiceImplenments.Log.ConsoleLoggerServerImplementation;

import java.io.IOException;

public class StartServer {


    public static Data shareData;

    public static void main(String[] args) throws IOException {
        shareData = new Data();
        ConsoleLoggerServer consoleLoggerServer = new ConsoleLoggerServerImplementation();
        consoleLoggerServer.save();
        ServerManager serverManager = new ServerManager();
        serverManager.initializeServer();
    }
}


