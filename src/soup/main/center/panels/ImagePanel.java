package soup.main.center.panels;

import com.alee.managers.icon.Icons;
import soup.utils.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImagePanel extends AbstractPanel {
    private ImageView view;
    private ArrayList<File> filesInDir;
    private ArrayList<BufferedImage> imagesInDir;
    private JProgressBar pb;
    private ImagePanel thisPanel;
    public ImagePanel(File file, String ext) {
        super(file, ext, Icons.globe);
        filesInDir = new ArrayList<>();
        imagesInDir = new ArrayList<>();
        thisPanel = this;
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                File[] files = new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator))).listFiles();
                pb = new JProgressBar(0,files.length);
                pb.setString("Loading images in directory...");
                pb.setStringPainted(true);
                thisPanel.add(pb, BorderLayout.SOUTH);

                BufferedImage temp;
                for(int i = 0; i < files.length; i++){
                    pb.setValue(i);

                    temp = null;
                    try{
                        temp = ImageIO.read(files[i]);
                        filesInDir.add(files[i]);
                        imagesInDir.add(temp);
                    }catch (Exception e){

                    }
                }
                thisPanel.remove(pb);
                return null;
            }
        };
        if(file != null){
            worker.execute();
        }

    }

    public void moveLeft(){
        for(int i = 0; i < filesInDir.size(); i++){
            if(filesInDir.get(i).getName().compareTo(file.getName())==0){
                if(i == 0){
                    file = filesInDir.get(filesInDir.size()-1);
                    view.setImage(imagesInDir.get(imagesInDir.size()-1));
                    documentData.setTitle(filesInDir.get(imagesInDir.size()-1).getName());
                }else{
                    file = filesInDir.get(i-1);
                    view.setImage(imagesInDir.get(i-1));
                    documentData.setTitle(filesInDir.get(i-1).getName());
                }
                return;
            }
        }
    }
    public void moveRight(){
        for(int i = 0; i < filesInDir.size(); i++){
            if(filesInDir.get(i).getName().compareTo(file.getName())==0){
                if(i +1 >= filesInDir.size()){
                    file = filesInDir.get(0);
                    view.setImage(imagesInDir.get(0));
                    documentData.setTitle(filesInDir.get(0).getName());
                }else{
                    file = filesInDir.get(i+1);
                    view.setImage(imagesInDir.get(i+1));
                    documentData.setTitle(filesInDir.get(i+1).getName());

                }
                return;
            }
        }
    }
    @Override
    public AbstractPanel newPanel(File file) {
        return new ImagePanel(file,file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")));
    }

    @Override
    public String saveFile() {
//        if(file == null){
//            file = FileUtils.saveImageFile();
//        }
//        try {
//            ImageIO.write(view.getImage(),file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")),file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file.getName();
        return "";
    }

    @Override
    public void loadFile(File file) {
        if(file == null){
            return;
        }
        try {
            BufferedImage b = ImageIO.read(file);
            view = new ImageView(b,this);
            add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
