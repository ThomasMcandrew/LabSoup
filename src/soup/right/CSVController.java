package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.CSVFile;
import soup.utils.csv.math.CSVMathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CSVController extends AbstractController {
    private CSVFile CSVPanel;
    private static String[] columnNames = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L", "M", "N","O","P","Q","R","S","T","U", "V","W","X", "Y","Z"};
    public CSVController(CenterController controller) {
        super(controller);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.fillRect(0,20,20,20);

    }

    @Override
    protected void init() {
        initMathPanel();

    }


    private void initMathPanel(){
        JPanel math = new JPanel();
        math.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        math.setPreferredSize(new Dimension(200,100));
        JLabel m = new JLabel("Math - add COl");
        JLabel add = new JLabel("ADD");
        JComboBox added = new JComboBox(columnNames);
        JLabel basedOn = new JLabel("based on");
        JComboBox from = new JComboBox(columnNames);
        JLabel where = new JLabel("Where A[i] ");
        String[] operand = new String[] {"=", "<", ">", "<=", ">="};
        JComboBox op = new JComboBox(operand);
        JLabel af = new JLabel("A[i+1]");
        JLabel startRow = new JLabel("Start Row: ");
        JTextField start = new JTextField("0",4);
        JLabel outputCol = new JLabel("Output Col");
        JComboBox col1 = new JComboBox(columnNames);
        JComboBox col2 = new JComboBox(columnNames);
        col1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col2.setSelectedIndex(col1.getSelectedIndex()+1);
            }
        });
        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVMathUtil.addCol(CSVPanel,added.getSelectedIndex(),from.getSelectedIndex(),Integer.parseInt(start.getText()),col1.getSelectedIndex(),col2.getSelectedIndex(), (String) op.getSelectedItem());
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        add(m,c);

        c.gridy++;
        c.gridwidth = 0;
        add(add,c);

        c.gridx++;
        add(added,c);

        c.gridx++;
        add(basedOn,c);

        c.gridx++;
        add(from,c);

        c.gridx = 0;
        c.gridy++;
        add(where,c);

        c.gridx++;
        add(op,c);

        c.gridx++;
        add(af,c);

        c.gridx = 0;
        c.gridy++;
        add(startRow,c);

        c.gridx++;
        add(start,c);

        c.gridx = 0;
        c.gridy++;
        add(outputCol,c);

        c.gridx++;
        add(col1,c);
        c.gridx++;
        add(col2,c);

        c.gridx++;
        add(run,c);


    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        CSVPanel = (CSVFile) panel;
    }
}
