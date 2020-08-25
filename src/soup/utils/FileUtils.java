package soup.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileUtils {
    public static File saveCSVFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV", ".csv"));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(!file.getAbsolutePath().endsWith(".csv")){
                file = new File(file.getAbsolutePath() + ".csv");
            }
            return file;
        }
        return null;
    }
    public static File saveTextFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", ".txt"));
        fc.setAcceptAllFileFilterUsed(true);
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(!file.getAbsolutePath().endsWith(".txt")){
                file = new File(file.getAbsolutePath() + ".txt");
            }
            return file;
        }
        return null;
    }
}
