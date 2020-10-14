package soup.center.panels.dumbPanels;

import soup.center.CenterController;
import soup.center.CloseableTab;
import soup.center.panels.AbstractPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends AbstractPanel {

    private Timer timer;
    private JButton start;
    private Dimension apple;
    private ArrayList<Dimension> body;
    private int blockswide,blockshigh;
    private Random random;
    private int addBody;
    private int dir;//0 - not started, 1 - up, 2 - down, 3 - left, 4 - right
    private int x=0,y=0;
    private int boxSize = 20;
    public Snake(CenterController centerController, int width, int height) {
        super(centerController, null, "snake", width, height);
        //centerController.setTabComponentAt(centerController.getSelectedIndex(),new CloseableTab(centerController,"snake",this));
        random = new Random();
        addBody = 0;
        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(start);
                start();
                grabFocus();
                requestFocus();
            }
        });
        add(start,BorderLayout.CENTER);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    if(dir != 1) {
                        dir = 2;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    if(dir != 4) {
                        dir = 3;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    if(dir != 3) {
                        dir = 4;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    if(dir != 2) {
                        dir = 1;
                    }
                }
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                blockshigh = getHeight()/boxSize;
                blockswide = getWidth()/boxSize;

            }
        });
        setFocusable(true);
        setRequestFocusEnabled(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                grabFocus();
                requestFocus();
            }
        });

    }

    private void start(){
        initTimer();
        dir = 0;
        body = new ArrayList<>();
        body.add(new Dimension(2,2));
        newApple();
    }

    private void newApple(){
        apple = new Dimension(random.nextInt(blockswide),random.nextInt(blockshigh));
        for(int i = 0; i < body.size();i++){
            if(apple.width ==body.get(i).width && apple.height == body.get(i).height){
                newApple();
                return;
            }
        }

    }

    private void initTimer(){
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dir == 0){
                    repaint();
                    return;
                }
                Dimension toAdd = null;
                for(int i = body.size()-1; i > 0;i--){
                    if(addBody > 0 && i == body.size()-1){
                        toAdd = new Dimension( body.get(i).width, body.get(i).height);
                        addBody--;
                    }
                    body.get(i).height = body.get(i-1).height;
                    body.get(i).width = body.get(i-1).width;
                }

                switch (dir){
                    case 1:
                        if(addBody > 0 && 1 == body.size()){
                            toAdd = new Dimension( body.get(0).width, body.get(0).height);
                            addBody--;
                        }
                        body.get(0).height = body.get(0).height-1;
                        break;
                    case 2:
                        if(addBody > 0 && 1 == body.size()){
                            toAdd = new Dimension( body.get(0).width, body.get(0).height);
                            addBody--;
                        }
                        body.get(0).height = body.get(0).height+1;
                        break;
                    case 3:
                        if(addBody > 0 && 1 == body.size()){
                            toAdd = new Dimension( body.get(0).width, body.get(0).height);
                            addBody--;
                        }
                        body.get(0).width = body.get(0).width-1;
                        break;
                    case 4:
                        if(addBody > 0 && 1 == body.size()){
                            toAdd = new Dimension( body.get(0).width, body.get(0).height);
                            addBody--;
                        }
                        body.get(0).width = body.get(0).width+1;
                        break;
                }
                if(toAdd != null) {
                    body.add(toAdd);
                }
                if(apple.width ==body.get(0).width && apple.height == body.get(0).height){
                    addBody += 5;
                    newApple();
                }
                for(int i = 1; i < body.size();i++){
                    if(body.get(0).width ==body.get(i).width && body.get(0).height == body.get(i).height){
                        gameOver();
                        return;
                    }
                }
                if(body.get(0).width < 0 || body.get(0).height < 0 || body.get(0).height > blockshigh|| body.get(0).width > blockswide){
                    gameOver();
                    return;
                }
                repaint();
            }
        });
        timer.start();
    }

    private void gameOver(){
        timer.stop();
        timer = null;
        JButton over = new JButton("Game Over, Play Again?");
        over.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(over);
                start();
                grabFocus();
                requestFocus();
            }
        });
        add(over,BorderLayout.CENTER);

    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for(int i = 0; i < body.size(); i++){
            g.drawRect(body.get(i).width * boxSize,body.get(i).height * boxSize,boxSize,boxSize);
        }

        g.setColor(Color.red);
        g.fillRect(apple.width * boxSize,apple.height * boxSize,boxSize,boxSize);
        g.drawRect(0,0,blockswide * boxSize,blockshigh * boxSize);

    }

    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {

    }
}
