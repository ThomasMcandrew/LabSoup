package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;
import soup.main.center.panels.CSVPanel;
import soup.main.editor.AbstractEditor;
import soup.utils.csv.CSVMathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CSVEditor extends AbstractEditor {
    private static String[] columnNames = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L", "M", "N","O","P","Q","R","S","T","U", "V","W","X", "Y","Z"};

    public CSVEditor(){

    }

    @Override
    protected void init() {
        add(initMathPanel());
    }
    private void initSummaryPanel(){
        WebPanel summary = new WebPanel();
        summary.setLayout(new GridBagLayout());
        summary.setPreferredSize(new Dimension(400,400));
        GridBagConstraints c = new GridBagConstraints();
        WebLabel summ = new WebLabel("Summary");

    }
    private WebPanel initMathPanel(){
        WebPanel math = new WebPanel();
        math.setLayout(new GridBagLayout());
        math.setPreferredSize(400,400);
        GridBagConstraints c = new GridBagConstraints();
        WebLabel m = new WebLabel("Math - add COl");
        WebLabel add = new WebLabel("ADD");
        WebComboBox added = new WebComboBox(columnNames);
        WebLabel basedOn = new WebLabel("based on");
        WebComboBox from = new WebComboBox(columnNames);
        WebLabel where = new WebLabel("Where A[i] ");
        String[] operand = new String[] {"=", "<", ">", "<=", ">="};
        WebComboBox op = new WebComboBox(operand);
        WebLabel af = new WebLabel("A[i+1]");
        WebLabel startRow = new WebLabel("Start Row: ");
        WebTextField start = new WebTextField("0",4);
        WebLabel outputCol = new WebLabel("Output Col");
        WebComboBox col1 = new WebComboBox(columnNames);
        WebComboBox col2 = new WebComboBox(columnNames);
        col1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col2.setSelectedIndex(col1.getSelectedIndex()+1);
            }
        });
        WebButton run = new WebButton("Run");
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVMathUtils.addCol((CSVPanel) (CenterController.getCenterController().getSelectedDocument().getComponent()),added.getSelectedIndex(),from.getSelectedIndex(),Integer.parseInt(start.getText()),col1.getSelectedIndex(),col2.getSelectedIndex(), (String) op.getSelectedItem());
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        math.add(m,c);

        c.gridy++;
        c.gridwidth = 1;
        math.add(add,c);

        c.gridx++;
        math.add(added,c);

        c.gridx++;
        math.add(basedOn,c);

        c.gridx++;
        math.add(from,c);

        c.gridx = 0;
        c.gridy++;
        math.add(where,c);

        c.gridx++;
        math.add(op,c);

        c.gridx++;
        math.add(af,c);

        c.gridx = 0;
        c.gridy++;
        math.add(startRow,c);

        c.gridx++;
        math.add(start,c);

        c.gridx = 0;
        c.gridy++;
        math.add(outputCol,c);

        c.gridx++;
        math.add(col1,c);
        c.gridx++;
        math.add(col2,c);

        c.gridx++;
        math.add(run,c);
        return math;

    }

}
