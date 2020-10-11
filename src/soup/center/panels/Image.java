package soup.center.panels;

import soup.center.CenterController;
import soup.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends AbstractPanel{
    private ImageView view;

    public Image(CenterController centerController, File file, String ext, int width, int height) {
        super(centerController, file, ext, width, height);
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return new Image(centerController,file,file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")),width,height);
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
