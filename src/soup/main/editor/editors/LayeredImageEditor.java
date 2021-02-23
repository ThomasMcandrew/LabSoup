package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import soup.main.center.CenterController;
import soup.main.center.panels.LayeredImagePanel;
import soup.main.editor.AbstractEditor;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayeredImageEditor extends AbstractEditor {
    private int speedCheck;
    public LayeredImageEditor() {
        super();
    }

    @Override
    protected void init() {
        WebButton add = new WebButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((LayeredImagePanel)(CenterController.getCenterController().getSelectedDocument().getComponent())).addImage(FileUtils.openImage());
            }
        });
        add(add);
        WebLabel delayLabel = new WebLabel("Delay");
        add(delayLabel);
        WebTextField speed = new WebTextField("1",4);
        speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    speedCheck = Integer.parseInt(speed.getText());
                    ((LayeredImagePanel)(CenterController.getCenterController().getSelectedDocument().getComponent())).setDelay(speedCheck);
                }catch (NumberFormatException es){
                    speed.setText(Integer.toString(speedCheck));
                }
            }
        });
        add(speed);
        WebButton preview = new WebButton("Preview");
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

}

