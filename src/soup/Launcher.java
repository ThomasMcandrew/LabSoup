package soup;

import soup.display.MainFrame;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

}
