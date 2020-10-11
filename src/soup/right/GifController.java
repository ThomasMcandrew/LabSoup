package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.LayeredImage;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GifController extends AbstractController {

    private LayeredImage layeredImage;
    private int speedCheck;
    public GifController(CenterController controller) {
        super(controller);
    }

    @Override
    protected void init() {
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layeredImage.addImage(FileUtils.openImage());
            }
        });
        add(add);
        JLabel delayLabel = new JLabel("Delay");
        add(delayLabel);
        JTextField speed = new JTextField("1",4);
        speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try{
                  speedCheck = Integer.parseInt(speed.getText());
                  layeredImage.setDelay(speedCheck);
              }catch (NumberFormatException es){
                  speed.setText(Integer.toString(speedCheck));
              }
            }
        });
        add(speed);
        JButton preview = new JButton("Preview");
        preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(preview);
    }
    public int getSpeedCheck(){
        return speedCheck;
    }
    @Override
    protected void setPanel(AbstractPanel panel) {
        this.layeredImage = (LayeredImage)panel;
    }
}
