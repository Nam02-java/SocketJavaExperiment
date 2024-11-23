package MVC;

import MVC.Controller.Client.ClientManager;
import MVC.Model.Data;
import MVC.Service.LazySingleton.ID.BiggestID;
import MVC.Service.LazySingleton.UserName.UserNameManager;

import java.io.File;
import java.io.IOException;

public class Nam02_Client2 {



    public static void main(String[] args) throws IOException {
        BiggestID.getInstance().setBiggestID(new File(Data.getFilePath()));
        UserNameManager.getInstance().setUsername("Nam02");

        ClientManager clientManager = new ClientManager();
        clientManager.initializeClient();

    }
}
