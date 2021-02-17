package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.Report;
import soup.center.panels.report.TextItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportController extends AbstractController {

    private Report report;
    public ReportController(CenterController controller) {
        super(controller);
    }

    @Override
    protected void init() {
        JTextField test = new JTextField(10);
        JButton b = new JButton("POOP");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] f = test.getText().split(",");
                int[] j = new int[4];
                for(int i = 0 ; i < 4; i++){
                    j[i] = Integer.parseInt(f[i]);
                }
                report.addItem(new TextItem(j[0],j[1],j[2],j[3]));
                controller.repaint();
            }
        });
        add(test);
        add(b);

    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        report = (Report) panel;
    }
}
