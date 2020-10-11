package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.CSVFile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.nio.file.FileStore;

public class RightPanel extends JPanel {

    private JPanel thisPanel;
    private CenterController controller;
    private AbstractPanel current;
    private JPanel nullPanel;
    private CSVController CSV;
    private TextController TEXT;
    private FileController FILE;
    private ImageController IMAGE;
    private GifController GIF;
    private String NullPanel = "NULL";
    private String CSVString = "CSV";
    private String TextString = "TEXT";
    private String FIleString = "FILE";
    private String ImageString = "IMAGE";
    private String GifString = "GIF";
    public RightPanel(CenterController controller, int width, int height){
        this.controller = controller;
        thisPanel = this;
        setMaximumSize(new Dimension(200, JViewport.HEIGHT));
        setMinimumSize(new Dimension(0,0));
        setPreferredSize(new Dimension(200, height));
        init();
    }

    public void setPanel(AbstractPanel panel){
        current = panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    private void init(){
        CardLayout layout = new CardLayout();
        setLayout(layout);
        nullPanel = new JPanel();
        CSV = new CSVController(controller);
        TEXT = new TextController(controller);
        FILE = new FileController(controller);
        IMAGE = new ImageController(controller);
        GIF = new GifController(controller);
        add(nullPanel, NullPanel);
        add(CSV,CSVString);
        add(TEXT,TextString);
        add(FILE, FIleString);
        add(IMAGE,ImageString);
        add(GIF,GifString);
        controller.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                current = (AbstractPanel) controller.getSelectedComponent();
                if(current == null){
                    return;
                }
                switch (current.ext){
                    case ".txt":
                        layout.show(thisPanel,TextString);
                        TEXT.setPanel(current);
                        break;
                    case ".csv":
                        layout.show(thisPanel,CSVString);
                        CSV.setPanel(current);
                        break;
                    case ".png":
                        layout.show(thisPanel,ImageString);
                        IMAGE.setPanel(current);
                        break;
                    case ".gif":
                        layout.show(thisPanel,GifString);
                        GIF.setPanel(current);
                        break;
                    case "\\":
                        layout.show(thisPanel,FIleString);
                        FILE.setPanel(current);
                        break;
                    default:
                        layout.show(thisPanel,NullPanel);
                        break;
                }
            }
        });
    }
}
