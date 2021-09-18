package soup.main.editor.editors;

import com.alee.extended.accordion.AccordionPane;
import com.alee.extended.accordion.WebAccordion;
import com.alee.extended.overlay.WebOverlay;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.laf.window.WebFrame;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;
import soup.main.center.panels.CSVPanel;
import soup.main.center.panels.TableModel;
import soup.main.editor.AbstractEditor;
import soup.utils.FileUtils;
import soup.utils.csv.CSVMathUtils;
import soup.utils.csv.CSVUtils;
import soup.utils.csv.Summary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CSVEditor extends AbstractEditor {
    private static String[] columnNames = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L", "M", "N","O","P","Q","R","S","T","U", "V","W","X", "Y","Z"};

    public CSVEditor(){

    }
    /*
    Plan of attack
    Data cleaning
    Every regression possible
    p value analysis
    Charts for everything
     */
    @Override
    protected void init() {
        
        WebAccordion accordion = new WebAccordion();
        accordion.addPane("Data Cleaning",initCleanPanel());
        accordion.addPane("Math",initMathPanel());
        accordion.addPane("Statistics",initSummaryPanel());

        //add(accordion,BorderLayout.NORTH);
        add(accordion,BorderLayout.CENTER);

    }

    private WebPanel initCleanPanel() {
        WebAccordion accordion = new WebAccordion();


        accordion.addPane("Find and Replace",initFindAndReplacePanel());
        accordion.addPane("Remove",initRemovePanel());



        WebPanel panel = new WebPanel();

        panel.add(accordion);
        return panel;
    }
    private WebPanel initPopulatePanel(){
        WebPanel populate = new WebPanel();
        WebLabel outputColLabel = new WebLabel("Output Column");
        WebComboBox outputCol = new WebComboBox(columnNames);
        WebLabel startLabel = new WebLabel("Start");
        WebTextField start = new WebTextField(4);
        WebLabel stopLabel = new WebLabel("Stop");
        WebTextField stop = new WebTextField(4);
        WebLabel eq = new WebLabel("y = ");
        WebTextField equation = new WebTextField(10);
        equation.setToolTip("Needs to be added explaining how to enter equation, can use column as [A] for example or name ie [name_here] along with x if wanted x represents how many have been done " +
                "while populating");
        WebButton run = new WebButton(Icons.accept);

        populate.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        populate.add(outputColLabel,c);
        c.gridx++;
        populate.add(outputCol,c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        populate.add(startLabel,c);
        c.gridx++;
        populate.add(start,c);
        c.gridx++;
        populate.add(stopLabel,c);
        c.gridx++;
        populate.add(stop,c);
        c.gridy++;
        c.gridx = 0;
        populate.add(eq,c);
        c.gridx++;
        c.gridwidth = 2;
        populate.add(equation,c);
        c.gridx++;
        c.gridwidth=1;

        return populate;
    }
    private WebPanel initRemovePanel() {
        WebPanel remove = new WebPanel();
        //remove.setPreferredSize(new Dimension(250,200));

        WebButton removeNullRows = new WebButton("Remove Null/NA rows");

        WebLabel removeColumn = new WebLabel("Remove Column");
        WebTextField removeCol = new WebTextField(5);
        WebButton runRemoveCol = new WebButton(Icons.accept);

        WebLabel removeRow = new WebLabel("Remove Row");
        WebTextField removeRowTF = new WebTextField(5);
        WebButton runRemoveRow = new WebButton(Icons.accept);

        remove.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 3;
        remove.add(removeNullRows,c);

        c.gridy++;
        c.gridwidth = 1;
        remove.add(removeColumn,c);
        c.gridx++;
        remove.add(removeCol,c);
        c.gridx++;
        remove.add(runRemoveCol,c);
        c.gridx = 0;
        c.gridy++;
        remove.add(removeRow,c);
        c.gridx++;
        remove.add(removeRowTF,c);
        c.gridx++;
        remove.add(runRemoveRow,c);

        return remove;
    }

    private WebPanel initSummaryPanel(){
        WebPanel summary = new WebPanel();
        summary.setLayout(new GridBagLayout());
       // summary.setPreferredSize(new Dimension(400,400));
        GridBagConstraints c = new GridBagConstraints();
        WebButton summaryToNew = new WebButton("ExportSummary");
        summaryToNew.setEnabled(false);
        summaryToNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Summary.createSummary(((CSVPanel)(CenterController.getCenterController().getSelectedDocument().getComponent())).file, FileUtils.getFreeFileName());
            }
        });
        c.gridy = 0;
        c.gridx = 0;
        summary.add(summaryToNew,c);
        return summary;
    }
    private WebPanel initMathPanel(){
        WebPanel math = new WebPanel();
        //AccordionPane math = new AccordionPane();
        math.setLayout(new GridBagLayout());
       // math.setPreferredSize(400,400);
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

    private WebPanel initFindAndReplacePanel(){
        WebPanel findAndReplace = new WebPanel();
//        findAndReplace.setPreferredSize(new Dimension(250,200));
        WebLabel find = new WebLabel("Find");
        WebLabel replace = new WebLabel("Replace");
        WebTextField input = new WebTextField(6);
        WebTextField output = new WebTextField(6);

        WebButton run = new WebButton("Replace");
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVUtils.findAndReplace(((TableModel)((CSVPanel)(CenterController.getCenterController().getSelectedDocument().getComponent())).getTable().getModel()),input.getText(), output.getText());
            }
        });
        findAndReplace.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        findAndReplace.add(find,c);
        c.gridx++;
        findAndReplace.add(replace,c);
        c.gridx = 0;
        c.gridy++;
        findAndReplace.add(input,c);
        c.gridx++;
        findAndReplace.add(output,c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        findAndReplace.add(run,c);
        return findAndReplace;
    }

}
