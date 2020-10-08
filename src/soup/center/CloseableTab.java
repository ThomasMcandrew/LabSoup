package soup.center;

import soup.center.panels.AbstractPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseableTab extends JPanel {
    public CenterController controller;
    public JLabel label;
    public JButton close;
    private AbstractPanel panel;
    public CloseableTab(CenterController controller, String name, AbstractPanel panel){
        label = new JLabel(name);
        close = new JButton("x");
        this.controller = controller;
        this.panel = panel;
        setBackground(Color.white);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.remove(panel);
            }
        });
        close.setBorder(null);
        close.setBackground(Color.WHITE);
        close.setPreferredSize(new Dimension(25,25));
        add(label);
        add(close);
    }
    public void setName(String name){
        label.setText(name);
    }


}
