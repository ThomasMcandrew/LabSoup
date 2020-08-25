package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;

import javax.swing.*;

public abstract class AbstractController extends JPanel {

    protected CenterController controller;
    public AbstractController(CenterController controller){
        this.controller = controller;
        init();
    }
    protected abstract void init();
    protected abstract void setPanel(AbstractPanel panel);

}
