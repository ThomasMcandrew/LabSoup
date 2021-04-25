package soup.main.center.panels;

import com.alee.managers.icon.Icons;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpeedReadPanel extends AbstractPanel {
    private String text;
    public SpeedReadPanel(File file, String ext) {
        super(file, ext, Icons.accept);
    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new SpeedReadPanel(file,".txt");
    }

    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {
        try {
            Scanner scan = new Scanner(file);
            text = "";
            while (scan.hasNext()){
                text += scan.nextLine() + "\n";
            }
            add(new SpeedReaderSplitPane(text));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
