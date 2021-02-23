package soup.main.center.panels;

import com.alee.managers.icon.Icons;
import soup.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends AbstractPanel {
    private ImageView view;

    public ImagePanel(File file, String ext) {
        super(file, ext, Icons.globe);
    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new ImagePanel(file,file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")));
    }

    @Override
    public String saveFile() {
        if(file == null){
            file = FileUtils.saveImageFile();
        }
        try {
            ImageIO.write(view.getImage(),file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    @Override
    public void loadFile(File file) {
        if(file == null){
            return;
        }
        try {
            BufferedImage b = ImageIO.read(file);
            view = new ImageView(b);
            add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
