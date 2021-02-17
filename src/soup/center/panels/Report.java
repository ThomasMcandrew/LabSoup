package soup.center.panels;

import soup.center.CenterController;
import soup.center.panels.report.AbstractItem;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

public class Report extends AbstractPanel implements MouseListener, MouseMotionListener {
    private ArrayList<AbstractItem> items;
    private AbstractItem moving;
    private boolean pressed;
    public Report(CenterController centerController, File file, int width, int height) {
        super(centerController, file, ".lsr", width, height);
        items = new ArrayList<>();
        setLayout(null);
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return new Report(centerController,file,width,height);
    }

    public void addItem(AbstractItem i){
        items.add(i);
        add(i);
    }


    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        grabFocus();
        for(int i = 0; i < items.size();i++){
            if(items.get(i).contains(e.getPoint())){
                pressed = true;
                moving = items.get(i);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(pressed){
            pressed = false;
            moving.setBounds(e.getX(),e.getY(),moving.getWidth(),moving.getHeight());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(pressed){
            moving.setBounds(e.getX(),e.getY(),moving.getWidth(),moving.getHeight());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
