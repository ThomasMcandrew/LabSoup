package soup.center;

import soup.center.panels.*;
import soup.center.panels.Image;
import soup.display.MainPanel;
import soup.left.FileNode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

public class CenterController extends JTabbedPane implements MouseListener, MouseMotionListener {

    public static int CUR_WIDTH,CUR_HEIGHT;
    private int width, height;
    public MainPanel mainPanel;
    private static AbstractPanel[] panels = new AbstractPanel[]{new TextFile(null,null,".txt",0,0),new CSVFile(null,null,0,0),new Image(null,null,".png",0,0),
            new Image(null,null, ".jpg",0,0),new LayeredImage(null,null,0,0)};
    private ArrayList<AbstractPanel> openPanels;
    public CenterController(MainPanel mainPanel, int width, int height){
        this.width = width;
        this.height = height;
        CUR_HEIGHT = height;
        CUR_WIDTH = width;
        this.mainPanel = mainPanel;
        openPanels = new ArrayList<>();
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(new Dimension(50,50));
        this.addMouseMotionListener(this);
    }



    public void newPanel(String name, AbstractPanel panel){
        openPanels.add(panel);
        add(name, panel);
        setSelectedComponent(panel);
        setTabComponentAt(getTabCount()-1,new CloseableTab(this,name,panel));
    }

    @Override
    public Component add(Component component) {
        return super.add(component);
    }

    public void saveSelectedAs(){
        AbstractPanel panel = (AbstractPanel) getSelectedComponent();
        panel.file = null;
        saveSelected();
    }
    public void saveSelected(){
        AbstractPanel panel = (AbstractPanel) getSelectedComponent();
        String name = panel.saveFile();
        getTabComponentAt(getSelectedIndex()).setName(name);
        setTitleAt(getSelectedIndex(),name);
    }


    public void openFileAsText(FileNode node){openFileAsText(node.file);}
    public void openFileAsText(File file){
        TextFile temp = new TextFile(this, file,".txt",width,height);
        newPanel(file.getName(),temp);
    }

    public void openDirectory(FileNode fileNode){openDirectory(fileNode.file); }
    public void openDirectory(File file){
        FileExplorer ex = new FileExplorer(this, file,width,height);
        newPanel(file.getName(),ex);
    }

    public void openFile(FileNode fileNode){
        openFile(fileNode.file);
    }
    public void openFile(File file){
        if(file.isDirectory()){
            return;
        }
        if(fileOpened(file)){
            return;
        }
        boolean set = false;
        String ext = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        for(int i = 0; i < panels.length; i++){
            if(ext.endsWith(panels[i].ext)){
                AbstractPanel temp = panels[i].newPanel(this,file, width, height);
                newPanel(file.getName(), temp);
                setSelectedComponent(temp);
                set = true;
                break;
            }
        }
        if(!set){
            AbstractPanel temp = panels[0].newPanel(this,file, width, height);
            newPanel(file.getName(), temp);
            setSelectedComponent(temp);
        }

    }

    private boolean fileOpened(File file){
        Component[] c = getComponents();
        AbstractPanel temp;
        for(int i = 0; i < c.length; i++){
            try {
                temp = (AbstractPanel) c[i];
                if (temp.file == file) {
                    setSelectedComponent(c[i]);
                    return true;
                }
            }catch(Exception e){

            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
