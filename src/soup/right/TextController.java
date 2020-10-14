package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.TextFile;
import soup.center.panels.dumbPanels.TypeRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton playType = new JButton("Play TypeRace!");
        playType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypeRace temp = new TypeRace(controller,textPanel.file,0,0);
                controller.newPanel("Type Race: " + textPanel.file.getName(),temp);
                controller.setSelectedComponent(temp);
            }
        });
        add(playType);
    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        textPanel = (TextFile) panel;
    }
}
