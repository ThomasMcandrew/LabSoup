package soup.main.center.panels;

import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.icon.Icons;
import soup.utils.FileUtils;
import soup.utils.files.GifUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LayeredImagePanel extends AbstractPanel {
    private ArrayList<BufferedImage> images;
    private int delay;

    public LayeredImagePanel(File file) {
        super(file, ".gif", Icons.globe);
    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new LayeredImagePanel(file);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addImage(BufferedImage b){
        images.add(b);
    }
    public void addImage(BufferedImage[] b){
        for(int i = 0; i < b.length; i++) {
            images.add(b[i]);
        }
    }

    private void init(){
        images = new ArrayList<>();

    }

    @Override
    public String saveFile() {
        if(file == null){
            file = FileUtils.saveGif();
        }

        BufferedImage[] b = new BufferedImage[images.size()];
        for(int i = 0; i < b.length;i++){
            b[i] = images.get(i);
        }
        try {
            GifUtils.writeGIF(b,delay,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    @Override
    public void loadFile(File file)  {
        if(file == null)
            return;

        try {
            BufferedImage[] im = GifUtils.readGif(file);
            for(int i = 0; i < im.length; i++){
                images.add(im[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
