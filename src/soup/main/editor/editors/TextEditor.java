package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import soup.main.center.CenterController;
import soup.main.center.panels.SpeedReadPanel;
import soup.main.center.panels.TextPanel;
import soup.main.editor.AbstractEditor;
import soup.utils.FileUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TextEditor extends AbstractEditor {

    public TextEditor(){

    }

    @Override
    protected void init() {
        WebButton speedRead = new WebButton("SpeedRead");
        speedRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = ((TextPanel)(CenterController.getCenterController().getSelectedDocument().getComponent())).file;
                File newFile = new File(FileUtils.getSpeedReadFolder() + File.separator + file.getName());
                if(newFile.exists()){
                    newFile.delete();
                }
                try {
                    Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                    CenterController.getCenterController().newPanel(new SpeedReadPanel(newFile,".txt"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        add(speedRead);
    }

}
