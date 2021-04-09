package soup.main.center.panels;

import com.alee.laf.panel.WebPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimatedImageView extends WebPanel {
    private ArrayList<BufferedImage> images;
    private Timer timer;
    private int delay;
    public AnimatedImageView(){

    }

    public void setImages(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        startTimer();
    }

    public void startTimer(){
        if(timer.isRunning()){
            timer.stop();
        }
        timer = new Timer(delay,null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }
    public void stopTimer(){
        timer.stop();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

}
