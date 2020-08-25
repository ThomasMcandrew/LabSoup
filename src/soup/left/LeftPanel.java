package soup.left;

import soup.center.CenterController;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public FileTree tree;
    private JScrollPane scroller;
    private CenterController controller;
    public LeftPanel(CenterController controller, int width, int height){
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
        this.controller = controller;
        tree = new FileTree(controller);
        scroller = new JScrollPane(tree);
        scroller.setPreferredSize(new Dimension(width, height-40));
        scroller.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
        scroller.setBackground(Color.WHITE);
        scroller.setBorder(null);
        setBackground(Color.WHITE);
        add(scroller);
    }

    public void resizePanel(Dimension d){
        setPreferredSize(d);
        scroller.setPreferredSize(new Dimension((int)d.getWidth(),(int)d.getHeight()-40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
