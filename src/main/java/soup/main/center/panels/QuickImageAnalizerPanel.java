package soup.main.center.panels;

import javax.swing.*;
import java.io.File;

public class QuickImageAnalizerPanel extends AbstractPanel{

    public QuickImageAnalizerPanel(File file, String ext, Icon icon){
        super(file,ext,icon);

    }

    @Override
    public AbstractPanel newPanel(File file) {
        return null;
    }

    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {
        if(file.isDirectory()){

        }else{

        }
    }
}
