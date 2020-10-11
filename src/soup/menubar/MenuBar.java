package soup.menubar;

import soup.center.panels.AbstractPanel;
import soup.center.panels.CSVFile;
import soup.center.panels.LayeredImage;
import soup.center.panels.TextFile;
import soup.display.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    private JMenu file;
    private MainFrame mainFrame;
    public MenuBar(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        fileInit();
    }



    private void fileInit(){
        file = new JMenu("File");
        JMenu createNewFile = new JMenu("Create New File");
        JMenuItem newText = new JMenuItem("TXT");
        newText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mainPanel.centerController.newPanel("new",new TextFile(mainFrame.mainPanel.centerController,null,0,0));
            }
        });
        JMenuItem newCsv = new JMenuItem("CSV");
        newCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mainPanel.centerController.newPanel("new",new CSVFile(mainFrame.mainPanel.centerController,null,0,0));
            }
        });
        JMenuItem newGif = new JMenuItem("Layered Image");
        newGif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mainPanel.centerController.newPanel("new",new LayeredImage(mainFrame.mainPanel.centerController,null,0,0));
            }
        });
        createNewFile.add(newText);
        createNewFile.add(newCsv);
        createNewFile.add(newGif);
        file.add(createNewFile);

        JMenuItem saveAs = new JMenuItem("Save As");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mainPanel.centerController.saveSelectedAs();
            }
        });
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
        file.add(saveAs);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mainPanel.centerController.saveSelected();
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        file.add(save);
        add(file);
    }

}
