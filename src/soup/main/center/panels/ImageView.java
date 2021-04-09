package soup.main.center.panels;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImageView extends WebPanel implements MouseListener, MouseWheelListener {

    private BufferedImage image;
    private double scale;
    private int xOffset, yOffset;
    private ImagePanel panel;
    public ImageView(BufferedImage image,ImagePanel panel){
        this.image = image;
        this.panel = panel;
        addMouseListener(this);
        addMouseWheelListener(this);
        scale = 1;
        xOffset=0;
        yOffset=0;
        WebButton right = new WebButton(Icons.right);
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.moveRight();
                repaint();
            }
        });
        right.setStyleId(StyleId.buttonIconHover);
        WebButton left = new WebButton(Icons.left);
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.moveLeft();
                repaint();
            }
        });
        left.setStyleId(StyleId.buttonIconHover);

        add(left,BorderLayout.WEST);
        add(right,BorderLayout.EAST);
    }

    public void setImage(BufferedImage im){
        image = im;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width=getWidth(),height=getHeight();
        int imageWidth = image.getWidth(), imageHeight = image.getHeight();

        if(image.getHeight() > height){
            imageHeight = height;
            imageWidth = (int)(image.getWidth() * ((double) height / (double) image.getHeight()));
        }
        if(imageWidth > width){
            imageHeight = (int)(imageHeight * ((double) width / (double) imageWidth));
            imageWidth = width;
        }
        if(imageWidth * scale < width){
            xOffset = (int) (width - (imageWidth * scale)) / 2;
        }
        if(imageHeight * scale < height){
            yOffset = (int) (height - (imageHeight * scale)) / 2;
        }
        if(imageWidth * scale > width){
            xOffset = (int) ((imageWidth * scale) - width) / -2;
        }
        if(imageHeight * scale > height){
            yOffset = (int) ((imageHeight * scale) - height) / -2;
        }
        g.drawImage(image,xOffset,yOffset,(int) (imageWidth * scale),(int) (imageHeight * scale),null);

    }
    public BufferedImage getImage(){
        return image;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse wheel");
        if(e.isControlDown()){
            System.out.println("CT");
            scale += (double) e.getUnitsToScroll()/10.0;
            if(scale < .2){
                scale = .2;
            }
            if(scale > 5){
                scale = 5;
            }
            repaint();
        }
    }
}
