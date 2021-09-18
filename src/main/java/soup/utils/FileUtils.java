package soup.utils;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.filechooser.WebFileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class FileUtils {
    private static File tempFolder = new File(System.getProperty("user.home") + File.separator + ".lsoup" + File.separator + "temp_files");
    private static File labSoupFolder = new File(System.getProperty("user.home") + File.separator + ".lsoup");
    private static File summaryFolder = new File(System.getProperty("user.home") + File.separator + ".lsoup" + File.separator + "summary_files");
    private static File speedReadFolder = new File(System.getProperty("user.home") + File.separator + ".lsoup" + File.separator + "sr_files");
    public static File getTempFolder(){
        if(!tempFolder.exists() || !tempFolder.isDirectory()){
            tempFolder.mkdir();
        }
        return tempFolder;
    }
    public static File getLabSoupFolder(){
        if(!labSoupFolder.exists() || !labSoupFolder.isDirectory()){
            labSoupFolder.mkdir();
        }
        return labSoupFolder;
    }
    public static File getSpeedReadFolder(){
        if(!speedReadFolder.exists() || ! speedReadFolder.isDirectory()){
            speedReadFolder.mkdir();
        }
        return speedReadFolder;
    }
    public static File getSummaryFolder(){
        if(!summaryFolder.exists() || !summaryFolder.isDirectory()){
            summaryFolder.mkdir();
        }
        return summaryFolder;
    }
    public static File[] getTempFiles(){
        return getTempFolder().listFiles();
    }
    public static String getFreeFileName(){
        return "new (" + UUID.randomUUID() + ")";
    }
    public static BufferedImage[] openImages(){
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(null);
        File[] files = fc.getSelectedFiles();
        BufferedImage[] b = new BufferedImage[files.length];
        try {
            for(int i = 0; i < files.length;i++){
                b[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
    public static BufferedImage openImage(){
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        BufferedImage b = null;
        try {
            b = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static File saveGif(){
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("GIF", ".gif"));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(!file.getAbsolutePath().endsWith(".gif")){
                file = new File(file.getAbsolutePath() + ".gif");
            }
            return file;
        }
        return null;
    }

    public static File saveImageFile(){
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(null);
        File file = fc.getSelectedFile();
        for(int i = 0; i < ImageIO.getReaderFileSuffixes().length;i++){
            if(file.getAbsolutePath().endsWith("." + ImageIO.getReaderFileSuffixes()[i])){
                return file;
            }
        }
        return new File(file.getAbsolutePath() + ".png");
    }
    public static File saveCSVFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV", ".csv"));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(!file.getAbsolutePath().endsWith(".csv")){
                file = new File(file.getAbsolutePath() + ".csv");
            }
            return file;
        }
        return null;
    }
    public static File saveTextFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);

        FileNameExtensionFilter[] filters = new FileNameExtensionFilter[]{new FileNameExtensionFilter("Text File", ".txt"),
                new FileNameExtensionFilter("Python", ".py"),
                new FileNameExtensionFilter("HyperText Markup Language (HTML)", ".html", ".htm", ".xhtml", ".shtml", ".jhtml"),
                new FileNameExtensionFilter("Java", ".java",".class"),
                new FileNameExtensionFilter("Ruby", ".rb",".rhtml"),
                new FileNameExtensionFilter("JavaScript", ".js"),
                new FileNameExtensionFilter("PHP", ".php", ".php4", ".php3", ".phtml"),
                new FileNameExtensionFilter("XML", ".xml"),
                new FileNameExtensionFilter("CSS", ".css"),
                new FileNameExtensionFilter("Comma Separated Value", ".csv"),
                new FileNameExtensionFilter("LOG", ".log"),
                new FileNameExtensionFilter("YAML document", ".yml"),
                new FileNameExtensionFilter("README text document", ".readme")};
        for(int i = 0; i < filters.length; i++) {
            fc.addChoosableFileFilter(filters[i]);
        }
        fc.setAcceptAllFileFilterUsed(true);
        fc.setFileFilter(filters[0]);
        int returnVal = fc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String check = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(File.separator));
            if(!check.contains(".")){
                for(int i = 0; i < filters.length; i++){
                    if(fc.getFileFilter() == filters[i]){
                        file = new File(file.getAbsolutePath() + filters[i].getExtensions()[0]);
                    }
                }
            }
            return file;
        }
        return null;
    }
    public static File getDirectory(){
        File file = WebDirectoryChooser.showDialog(null);
        return file;

    }
}
