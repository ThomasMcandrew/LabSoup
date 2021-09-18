package soup.main.center.panels;

import com.alee.managers.icon.Icons;
import com.alee.managers.notification.NotificationManager;
import soup.utils.FileUtils;
import soup.utils.files.GifUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnimatedImagePanel extends AbstractPanel {
    private ArrayList<BufferedImage> images;
    private AnimatedImageView animatedImageView;
    public AnimatedImagePanel(File file) {
        super(file, ".gif", Icons.underlineHover);

    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new AnimatedImagePanel(file);
    }
    public BufferedImage[] convertToArray(){
        BufferedImage[] b = new BufferedImage[images.size()];
        for(int i = 0; i < b.length; i++){
            b[i] = images.get(i);
        }
        return b;
    }
    @Override
    public String saveFile() {
        NotificationManager.showInnerNotification("saving gifs is a little strange at the moment, not implemented right now");
        int i = 0;
        if(i == 0){
            return null;
        }
        if(file == null){
            file = FileUtils.saveGif();
        }
        try {
            GifUtils.writeGIF(convertToArray(),animatedImageView.getDelay(),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    @Override
    public void loadFile(File file) {
        try {
            images = GifUtils.readGifAsList(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(images == null){
            images = new ArrayList<>();
        }
        animatedImageView = new AnimatedImageView();
        animatedImageView.startTimer();
        add(animatedImageView);
        animatedImageView.setImages(images);
    }
}
