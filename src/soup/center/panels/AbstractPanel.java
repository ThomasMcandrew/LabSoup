package soup.center.panels;

import soup.center.CenterController;
import soup.right.AbstractController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class AbstractPanel extends JPanel {

    public File file;
    public String ext;
    protected int width,height;
    protected AbstractController controller;
    public AbstractPanel(File file, String ext,int width, int height){
        this.file = file;
        this.ext = ext;
        if(width == 0 && height == 0){
            this.width = CenterController.CUR_WIDTH;
            this.height = CenterController.CUR_HEIGHT;
        }else {
            this.width = width;
            this.height = height;
        }
        setLayout(new BorderLayout());
        loadFile();


    }

    public abstract AbstractPanel newPanel(File file,int width, int height);
    public abstract String saveFile();
    protected void loadFile(){
        loadFile(file);
    }
    public abstract void loadFile(File file);



}
