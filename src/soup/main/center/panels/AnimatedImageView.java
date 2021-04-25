package soup.main.center.panels;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.icon.Icons;
import com.alee.managers.icon.set.IconSet;
import com.alee.managers.style.StyleId;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class AnimatedImageView extends WebPanel implements MouseListener, MouseMotionListener,KeyListener {
    private ArrayList<BufferedImage> images;
    private ArrayList<Rectangle> imageBounds;
    private Timer timer;
    private int delay;
    private int frame;
    private int imageArrayOffset;
    private Rectangle imageArrayBounds;
    private Point lastClicked;
    private Boolean mousePressed;
    private Rectangle addBounds;

    public AnimatedImageView(){
        frame = 0;
        delay = 100;
        timer = new Timer(1,null);
        imageArrayOffset = 0;
        imageArrayBounds = null;
        lastClicked = null;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        WebPanel bottom = new WebPanel();
        WebButton back = new WebButton(Icons.left);
        back.setStyleId(StyleId.buttonIconHover);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame -= 1;
                if(frame < 0){
                    frame = images.size()-1;
                }
                repaint();
            }
        });
        WebButton pause = new WebButton(Icons.cross);
        pause.setStyleId(StyleId.buttonIconHover);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
                repaint();
            }
        });
        WebButton play = new WebButton(Icons.accept);
        play.setStyleId(StyleId.buttonIconHover);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
                repaint();
            }
        });
        WebButton forward = new WebButton(Icons.right);
        forward.setStyleId(StyleId.buttonIconHover);
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;
                if(frame >= images.size()){
                    frame = 0;
                }
                repaint();
            }
        });
        WebLabel delayLabel = new WebLabel("Delay:");
        delayLabel.setStyleId(StyleId.labelShadow);
        WebTextField delayField = new WebTextField(4);
        delayField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int temp;
                try{
                    temp = Integer.parseInt(delayField.getText());
                    setDelay(temp);
                }catch (NumberFormatException es){
                    delayField.setText(delay + "");
                }
            }
        });
        delayField.setText(delay + "");
        bottom.setLayout(new FlowLayout());
        bottom.add(back);
        bottom.add(pause);
        bottom.add(play);
        bottom.add(forward);
        bottom.add(delayLabel);
        bottom.add(delayField);
        add(bottom, BorderLayout.SOUTH);
    }

    public void setImages(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        frame = 0;
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
                frame++;
                if(frame >= images.size()){
                    frame = 0;
                }
                repaint();

            }
        });
        timer.start();
    }
    public void stopTimer(){
        timer.stop();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //main image'
        double mainRatio = .75;
        int startX = 5, startY = 5, endX = getWidth()-15, endY = ((int) (getHeight() * mainRatio)) - 5;
        if(images.size() > 0) {
            g.drawImage(images.get(frame), startX, startY, endX - startX, endY - startY, null);
        }
        //array of images
        int boxSizeOfImage = 50,boxSizeWithBuffer= 55;
        Rectangle temp = null;
        imageArrayBounds = new Rectangle(0,endY + 15,getWidth()-15,boxSizeOfImage);
        imageBounds = new ArrayList<>();
        int last = 0;
        if(images.size()>0) {
            for (int i = 0; i < images.size(); i++) {
                temp = new Rectangle(imageArrayOffset + (boxSizeWithBuffer * i), endY + 15, boxSizeOfImage, boxSizeOfImage);
                imageBounds.add(temp);
                if (!(temp.x > endX) && (temp.x + temp.width > -5)) {
                    g.drawImage(images.get(i), temp.x, temp.y, temp.width, temp.height, null);
                    if (i == frame) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.black);
                    }
                    g.drawRect(temp.x, temp.y, temp.width, temp.height);
                }
                last = temp.x + temp.width;
            }
        }
        addBounds = new Rectangle(last + 5, endY + 15, boxSizeOfImage,boxSizeOfImage);
        g.drawRect(addBounds.x,addBounds.y,addBounds.width,addBounds.height);
        g.drawString("+",addBounds.x + (addBounds.width / 2)-2,addBounds.y + (addBounds.height / 2) + 3);

    }
    private void changeImageArrayOffset(int offset){
        imageArrayOffset += offset;
        if(imageArrayOffset > 0){
            imageArrayOffset = 0;
        }
        if(imageArrayOffset < (0 - ((images.size()-1) * 55))){
            imageArrayOffset =  (0 - ((images.size()-1) * 55));
        }
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(addBounds != null){
            if(addBounds.contains(e.getPoint())){
                BufferedImage[] b = FileUtils.openImages();
                if(b != null){
                    if(b.length > 0){
                        for(int i = 0; i < b.length; i++){
                            images.add(b[i]);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastClicked = e.getPoint();
        mousePressed = true;
        grabFocus();
        if(imageBounds != null){
            for(int i = 0; i < imageBounds.size();i++){
                if(imageBounds.get(i).contains(e.getPoint())){
                    frame = i;
                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(imageArrayBounds != null){
            if (imageArrayBounds.contains(lastClicked) && mousePressed) {
                changeImageArrayOffset((e.getPoint().x - lastClicked.x)/100);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!timer.isRunning()){
            if(e.getKeyCode() == KeyEvent.VK_DELETE){
                System.out.println("hmm");
                images.remove(frame);
                repaint();

            }
        }
        System.out.println("sdfa");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
