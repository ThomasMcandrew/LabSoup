package soup;

import com.alee.laf.WebLookAndFeel;
import soup.main.MainFrame;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args){
        WebLookAndFeel.install();
        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });

    }

}
