package soup.center.panels.report;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractItem extends JPanel {


    protected int x,y,width,height;
    public AbstractItem(int x, int y, int width,int height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        setBounds(x, y, width, height);
        setPreferredSize(new Dimension(width, height));
        setLocation(x,y);
        init();
    }

    protected abstract void init();

    public abstract void save();
    public abstract void load();

}
