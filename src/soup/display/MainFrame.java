package soup.display;

import soup.menubar.MenuBar;
import soup.utils.config.Configuration;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainPanel mainPanel;
    public MenuBar menuBar;
    private int width, height;
    public MainFrame(){
        super("LabSoup");
        Configuration.frame = this;
        Configuration.load();
        width = 800;
        height = 600;
        initBefore();
        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);
        mainPanel = new MainPanel(width, height);
        add(mainPanel);
        initAfter();
    }

    private void initBefore(){
        setPreferredSize(new Dimension(width,height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initAfter(){
        setVisible(true);
        pack();
    }


}
