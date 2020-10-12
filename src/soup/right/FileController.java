package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.FileExplorer;
import soup.utils.files.BlindUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FileController extends AbstractController {

    private FileExplorer panel;

    public FileController(CenterController controller) {
        super(controller);
    }


    private void initBlind(){
        JPanel blindPanel = new JPanel();

        JLabel blind = new JLabel("Blind Files");
        JLabel split = new JLabel("Split folder by substring. use ',' to separate values");
        JTextField folders = new JTextField(24);
        JButton run = new JButton("Run");
        JLabel onlyUseThese = new JLabel("Only use these substrings");
        JCheckBox onlyUseTheseCheck = new JCheckBox();
        folders.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                onlyUseTheseCheck.setSelected(true);
            }
        });
        JLabel useOther = new JLabel("Save to other File format");
        JCheckBox useOtherFileFormat = new JCheckBox();
        JTextField fileFormat = new JTextField(9);
        JCheckBox onlyBlindCheck = new JCheckBox();
        JLabel onlyBlindLabel = new JLabel("Only blind file-type");
        JTextField onlyBlindText = new JTextField(9);
        onlyBlindText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                onlyBlindCheck.setSelected(true);
            }
        });
        fileFormat.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                useOtherFileFormat.setSelected(true);
            }
        });
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                    String inExt = null;
                    if(onlyBlindCheck.isSelected()){
                        if(onlyBlindText.getText().startsWith(".")) {
                            inExt = onlyBlindText.getText();
                        }else{
                            inExt = "." + onlyBlindText.getText();
                        }
                    }
                    String ext = null;
                    if(useOtherFileFormat.isSelected()){
                        if(fileFormat.getText().startsWith(".")){
                            ext = fileFormat.getText();
                        }else{
                            ext = "." + fileFormat.getText();
                        }
                    }
                    BlindUtils.BlindFiles(controller,panel.file,folders.getText(),ext,inExt,onlyUseTheseCheck.isSelected());
                    //BlindUtils.BlindFolder(controller, panel.file, folders.getText(), ext);

            }
        });

        blindPanel.setPreferredSize(new Dimension(280,150));
        blindPanel.setBorder(BorderFactory.createTitledBorder("BlindFiles"));
        blindPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        //blindPanel.add(blind,c);
        //c.gridy++;
        blindPanel.add(split,c);
        c.gridy++;
        blindPanel.add(folders,c);
        c.gridy++;
        c.gridwidth = 1;
        blindPanel.add(onlyUseTheseCheck,c);
        c.gridx++;
        blindPanel.add(onlyUseThese,c);
        c.gridy++;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        blindPanel.add(onlyBlindCheck,c);
        c.gridx++;
        blindPanel.add(onlyBlindLabel,c);
        c.gridx++;
        blindPanel.add(onlyBlindText,c);
        c.gridx = 0;
        c.gridy++;
        blindPanel.add(useOtherFileFormat,c);
        c.gridx++;
        blindPanel.add(useOther,c);
        c.gridx++;
        blindPanel.add(fileFormat,c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        blindPanel.add(run,c);
        add(blindPanel);

    }

    @Override
    protected void init() {
        initBlind();
    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        this.panel = (FileExplorer)panel;
    }
}
