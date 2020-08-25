package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.TextFile;

import java.awt.*;

public class TextController extends AbstractController {

    private TextFile textPanel;
    public TextController(CenterController controller) {
        super(controller);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(20,20,20,20);

    }

    @Override
    protected void init() {

    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        textPanel = (TextFile) panel;
    }
}
