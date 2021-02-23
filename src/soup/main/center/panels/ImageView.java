package soup.main.center.panels;

import com.alee.laf.panel.WebPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class ImageView extends WebPanel implements MouseListener, MouseWheelListener {

    private BufferedImage image;
    private double scale;
    private int xOffset, yOffset;
    public ImageView(BufferedImage image){
        this.image = image;
        addMouseListener(this);
        addMouseWheelListener(this);
        scale = 1;
        xOffset=0;
        yOffset=0;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageWidth = image.getWidth(), imageHeight = image.getHeight();

        if(image.getHeight() > getHeight()){
            imageHeight = getHeight();
            imageWidth = (int)(image.getWidth() * ((double) getHeight() / (double) image.getHeight()));
        }
        if(imageWidth > getWidth()){
            imageHeight = (int)(imageHeight * ((double) getWidth() / (double) imageWidth));
            imageWidth = getWidth();
        }
        if(imageWidth * scale < getWidth()){
            xOffset = (int) (getWidth() - (imageWidth * scale)) / 2;
        }
        if(imageHeight * scale < getHeight()){
            yOffset = (int) (getHeight() - (imageHeight * scale)) / 2;
        }
        if(imageWidth * scale > getWidth()){
            xOffset = (int) ((imageWidth * scale) - getWidth()) / -2;
        }
        if(imageHeight * scale > getHeight()){
            yOffset = (int) ((imageHeight * scale) - getHeight()) / -2;
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
