package soup.display;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.left.LeftPanel;
import soup.right.RightPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel {
    private int width, height;
    public LeftPanel leftPanel;
    public RightPanel rightPanel;
    public JSplitPane centerSplit;
    public CenterController centerController;
    public CommandPrompt commandPrompt;
    public MainPanel(int width,int height){
        setPreferredSize(new Dimension(width,height));
        setLayout(new BorderLayout());
        centerController = new CenterController(this,(int)(width),height);
        leftPanel = new LeftPanel(centerController, (int)(width*.25),height-30);
        rightPanel = new RightPanel(centerController,(int)(width*.25),(height-30));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setLeftComponent(leftPanel);
        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerSplit.setLeftComponent(centerController);
        centerSplit.setRightComponent(rightPanel);
        centerSplit.setResizeWeight(.8);

        split.setRightComponent(centerSplit);
        split.setDividerLocation(200);

        add(split,BorderLayout.CENTER);
        commandPrompt = new CommandPrompt(this);

        add(commandPrompt,BorderLayout.SOUTH);
        split.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                leftPanel.resizePanel(new Dimension(split.getDividerLocation(),getHeight()+30));
            }
        });

        centerController.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                AbstractPanel p = (AbstractPanel)(centerController.getSelectedComponent());
                rightPanel.setPanel(p);
            }
        });
    }

    public void setRightPanelControl(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
