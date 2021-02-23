package soup.main.menu;

import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.managers.style.StyleId;
import soup.main.MainPanel;
import soup.main.center.CenterController;
import soup.main.center.panels.CSVPanel;
import soup.main.center.panels.LayeredImagePanel;
import soup.main.center.panels.TextPanel;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class MenuBar extends JMenuBar {

    public MenuBar(){


        init();
    }
    private void init(){
        fileInit();
    }
    private void fileInit(){
        WebMenu file = new WebMenu("File");
        WebMenu createNewFile = new WebMenu("Create New File");
        WebMenuItem newText = new WebMenuItem("TXT");
        newText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterController.getCenterController().newPanel(new TextPanel(null,".txt"));
            }
        });
        WebMenuItem newCsv = new WebMenuItem("CSV");
        newCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterController.getCenterController().newPanel(new CSVPanel(null));
            }
        });
        WebMenuItem newGif = new WebMenuItem("Layered Image");
        newGif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterController.getCenterController().newPanel(new LayeredImagePanel(null));
            }
        });
        createNewFile.add(newText);
        createNewFile.add(newCsv);
        createNewFile.add(newGif);
        file.add(createNewFile);

        WebMenuItem saveAs = new WebMenuItem("Save As");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterController.getCenterController().saveSelectedAs();
            }
        });
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
        file.add(saveAs);
        WebMenuItem save = new WebMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CenterController.getCenterController().saveSelected();
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        file.add(save);

        WebMenu fileTree = new WebMenu("File Tree");
        WebMenuItem setNewHome = new WebMenuItem("Set new file directory");
        setNewHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = FileUtils.getDirectory();
                if(f != null && f.isDirectory()){
                    MainPanel.setNewFileTree(f.getAbsolutePath());
                }
            }
        });
        WebCheckBoxMenuItem toggleHiddenFiles = new WebCheckBoxMenuItem("Toggle Hidden Files");
        toggleHiddenFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.getFileTree().toggleHiddenFiles();
            }
        });
        fileTree.add(setNewHome);
        fileTree.add(toggleHiddenFiles);
        file.add(fileTree);



        add(file);

    }

}
