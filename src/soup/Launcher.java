package soup;

import com.alee.extended.canvas.WebCanvas;
import com.alee.extended.language.LanguageChooser;
import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageManager;
import com.alee.utils.ImageUtils;
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
