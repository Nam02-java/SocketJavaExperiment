package MVC;

import MVC.Controller.Server.ServerManager;
import MVC.Service.NewSingleThreadExecutor.SingleThreadTaskManager;

import java.io.IOException;

public class StartServer {

    public static SingleThreadTaskManager taskManager = new SingleThreadTaskManager();

    public static void main(String[] args) throws IOException {

        ServerManager serverManager = new ServerManager();
        serverManager.initializeServer();
    }
}


