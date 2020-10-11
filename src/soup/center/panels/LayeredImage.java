package soup.center.panels;

import soup.center.CenterController;
import soup.right.GifController;
import soup.utils.FileUtils;
import soup.utils.files.GifUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LayeredImage extends AbstractPanel {

    private JTabbedPane tabbs;
    private ArrayList<BufferedImage> images;
    private int delay;

    public LayeredImage(CenterController centerController, File file, int width, int height) {
        super(centerController, file, ".gif", width, height);
        if(tabbs == null) {
            tabbs = new JTabbedPane();
        }
        if(images == null) {
            images = new ArrayList<>();
        }
        add(tabbs);
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return new LayeredImage(centerController, file,width,height);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addImage(BufferedImage b){
        tabbs.add(new ImageView(b));
        images.add(b);
    }
    public void addImage(BufferedImage[] b){
        for(int i = 0; i < b.length; i++) {
            tabbs.add(new ImageView(b[i]));
            images.add(b[i]);
        }
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
        if(tabbs == null) {
            tabbs = new JTabbedPane();
        }
        if(images == null) {
            images = new ArrayList<>();
        }
        try {
            BufferedImage[] im = GifUtils.readGif(file);
            for(int i = 0; i < im.length; i++){
                tabbs.add(new ImageView(im[i]));
                images.add(im[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
