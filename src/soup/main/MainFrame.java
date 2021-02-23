package soup.main;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.window.WebFrame;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;
import soup.main.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends WebFrame {

    public MainFrame(){
        super("LabSoup");
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(WebFrame.HIDE_ON_CLOSE);
        setStyleId(StyleId.frameDecorated);

        setJMenuBar(new MenuBar());

        MainPanel mainPanel = MainPanel.getMainPanel();
        add(mainPanel,BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                CenterController.saveCurrentConfiguration();
                MainPanel.getMainPanel().saveFileTree();
                System.exit(0);
            }
        });
        CenterController.loadConfiguration();
        setVisible(true);
        pack();
    }


}
