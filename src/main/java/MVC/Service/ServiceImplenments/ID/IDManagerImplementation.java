package MVC.Service.ServiceImplenments.ID;

import MVC.Service.InterfaceService.File.ParseFile;
import MVC.Service.InterfaceService.ID.IDManager;
import MVC.Service.ServiceImplenments.File.ParseFileImplementation;

import java.io.File;

public class IDManagerImplementation implements IDManager {
    private ParseFile parseFile;

    public IDManagerImplementation() {
        this.parseFile = new ParseFileImplementation();
    }

    @Override
    public int updateMaxReceivedId(File file) {
        int biggestID = parseFile.getBiggestID(file);
        biggestID += 1;
        return biggestID;
    }
}
