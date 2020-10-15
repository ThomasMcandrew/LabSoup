package soup.utils.config;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Settings extends JFrame {

    public Settings(){
        super("Settings");
        initSettings();
        setPreferredSize(new Dimension(500,500));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }


    private void initSettings(){
        JList ui = new JList(Configuration.LOOK_AND_FEEL_STRINGS);
        ui.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int lnf = ui.getSelectedIndex();
                Configuration.setNewLookAndFeel(lnf);
            }
        });
        add(ui,BorderLayout.CENTER);
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Configuration.save();
                setVisible(false);
                dispose();
            }
        });
        add(save,BorderLayout.SOUTH);
    }

}
