package soup.utils.config;

import net.infonode.gui.laf.InfoNodeLookAndFeel;
import net.infonode.gui.laf.InfoNodeLookAndFeelThemes;
import soup.display.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Configuration {

    public static MainFrame frame;

    public static File folder = new File(System.getProperty("user.home") + File.separator + ".labsoup" + File.separator);

    public static int currentLookAndFeel;

    public static LookAndFeel[] LOOK_AND_FEELS = new LookAndFeel[]{
            new InfoNodeLookAndFeel(InfoNodeLookAndFeelThemes.getGrayTheme()),
            new InfoNodeLookAndFeel(InfoNodeLookAndFeelThemes.getBlueIceTheme()),
            new InfoNodeLookAndFeel(InfoNodeLookAndFeelThemes.getSoftGrayTheme()),
            new InfoNodeLookAndFeel(InfoNodeLookAndFeelThemes.getDarkBlueGreenTheme()),
            new InfoNodeLookAndFeel(),
    };
    public static String[] LOOK_AND_FEEL_STRINGS = new String[]{"javax.swing.plaf.nimbus.NimbusLookAndFeel",
            "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
            UIManager.getSystemLookAndFeelClassName(),
            "javax.swing.plaf.metal.MetalLookAndFeel"
    };



    public static void save(){
        if(!folder.exists()){
            folder.mkdir();
        }
        File cfg = new File(folder.getAbsolutePath() + File.separator + "lscfg.cfg");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(cfg);
            fileWriter.write(Integer.toString(currentLookAndFeel));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(){
        Scanner scan = null;
        File f = new File(folder.getAbsolutePath() +File.separator + "lscfg.cfg");
        if(f.exists()) {
            try {
                scan = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int i = scan.nextInt();
            setNewLookAndFeel(i);
        }
    }

    public static void setNewLookAndFeel(int i){
        if(i < LOOK_AND_FEELS.length) {
            try {
                UIManager.setLookAndFeel(LOOK_AND_FEELS[i]);
                currentLookAndFeel = i;
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }else{
            try {
                UIManager.setLookAndFeel(LOOK_AND_FEEL_STRINGS[i-LOOK_AND_FEELS.length]);
                currentLookAndFeel = i;
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
