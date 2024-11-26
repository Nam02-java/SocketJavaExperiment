package MVC.Service.ServiceImplenments.File;

import MVC.Service.InterfaceService.File.SaveToFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToFileImplementation implements SaveToFile {
    @Override
    public void saveMessageToFile(File file , String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
