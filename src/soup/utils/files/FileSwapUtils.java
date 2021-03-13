package soup.utils.files;

import com.alee.managers.notification.NotificationManager;
import soup.main.center.CenterController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileSwapUtils {
    public static void swapImageFilesAsync(File panelsFile, File[] files, String oldExtension, String newExtension,boolean saveAsCopy){
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                swapImageFiles(files, oldExtension, newExtension, saveAsCopy);
                return null;
            }

            @Override
            protected void done() {
                super.done();
                CenterController.getCenterController().closeDocument(CenterController.getCenterController().getDocument(panelsFile.getAbsolutePath()));
                CenterController.getCenterController().openFile(panelsFile);
            }
        };
        swingWorker.execute();

    }
    public static void swapImageFiles(File[] files, String oldExtension, String newExtension,boolean saveAsCopy){
        ArrayList<File> fileList = new ArrayList<>();

        if(oldExtension == "ALL"){
            for(int i = 0; i < files.length; i++){
                for(int j = 0; j < ImageIO.getReaderFileSuffixes().length;j++) {
                    if (files[i].getAbsolutePath().endsWith(ImageIO.getReaderFileSuffixes()[j])) {
                        fileList.add(files[i]);
                    }
                }
            }
        }
        else{
            for(int i = 0; i < files.length; i++){
                if(files[i].getAbsolutePath().endsWith(oldExtension)) {
                    fileList.add(files[i]);
                }
            }
        }
        boolean failed = false;
        File newDir = new File(fileList.get(0).getAbsolutePath().substring(0,fileList.get(0).getAbsolutePath().lastIndexOf(File.separator)) + File.separator + "Output from image converter" + File.separator);
        newDir.mkdir();
        for(int i = 0; i < fileList.size(); i++){
            try {
                BufferedImage b = ImageIO.read(fileList.get(i));
                File out = null;
                if(!newExtension.startsWith(".")){
                    String name = newDir + File.separator + fileList.get(i).getName().substring(0,fileList.get(i).getName().lastIndexOf('.')+1);
                    name += newExtension;
                    out = new File(name);
                }else{
                    String name = newDir + File.separator + fileList.get(i).getName().substring(0,fileList.get(i).getName().lastIndexOf('.'));
                    name += newExtension;
                    out = new File(name);
                }
                ImageIO.write(b,newExtension,out);
            } catch (IOException e) {
                e.printStackTrace();
                failed = true;
                NotificationManager.showInnerNotification("File " + fileList.get(i).getName() + " could not be read correctly");
            }
        }
//        if(!saveAsCopy) {
//            if (!failed) {
//                for (int i = 0; i < fileList.size(); i++) {
//                    fileList.get(i).delete();
//                }
//            }
//        }

    }

}
