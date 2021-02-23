package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import soup.main.center.CenterController;
import soup.main.center.panels.FilePanel;
import soup.main.editor.AbstractEditor;
import soup.utils.files.BlindUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class FileEditor extends AbstractEditor {
    //private FilePanel panel;
    private WebPanel blindPanel;
    public FileEditor() {
        super();
    }


    private void initBlind(){
        blindPanel = new WebPanel();

        WebLabel blind = new WebLabel("Blind Files");
        WebLabel split = new WebLabel("Split folder by substring. use ',' to separate values");
        WebTextField folders = new WebTextField();
        folders.setPreferredSize(new Dimension(200,20));
        WebButton run = new WebButton("Run");
        WebLabel onlyUseThese = new WebLabel("Only use these substrings");
        WebCheckBox onlyUseTheseCheck = new WebCheckBox();
        folders.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                onlyUseTheseCheck.setSelected(true);
            }
        });
        WebLabel useOther = new WebLabel("Save to other File format");
        WebCheckBox useOtherFileFormat = new WebCheckBox();
        WebTextField fileFormat = new WebTextField();
        fileFormat.setPreferredSize(new Dimension(90,20));

        WebCheckBox onlyBlindCheck = new WebCheckBox();
        WebLabel onlyBlindLabel = new WebLabel("Only blind file-type");
        WebTextField onlyBlindText = new WebTextField();
        onlyBlindText.setPreferredSize(new Dimension(90,20));
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
                BlindUtils.BlindFiles(((FilePanel) (CenterController.getCenterController().getSelectedDocument().getComponent())).file,folders.getText(),ext,inExt,onlyUseTheseCheck.isSelected());
                //BlindUtils.BlindFolder(controller, panel.file, folders.getText(), ext);

            }
        });

        blindPanel.setPreferredSize(new Dimension(280,150));
        blindPanel.setMinimumSize(new Dimension(280,150));
        blindPanel.setMaximumSize(new Dimension(280,150));
        blindPanel.setSize(new Dimension(280,150));

        blindPanel.setBorder(BorderFactory.createTitledBorder("BlindFiles"));
        blindPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
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


}
