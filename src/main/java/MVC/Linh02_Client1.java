package MVC;

import MVC.Controller.Client.ClientManager;
import MVC.Service.LazySingleton.UserName.UserNameManager;

import java.io.File;
import java.io.IOException;

public class Linh02_Client1 {
    public static void main(String[] args) throws IOException {

        UserNameManager.getInstance().setUsername("Linh02");

        ClientManager clientManager = new ClientManager();
        clientManager.initializeClient();

    }
}
