package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;
import soup.main.center.panels.FilePanel;
import soup.main.editor.AbstractEditor;
import soup.utils.files.BlindUtils;
import soup.utils.files.FileSwapUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FileEditor extends AbstractEditor {
    private WebPanel blindPanel;
    public FileEditor() {
        super();
    }


    private WebPanel initBlind(){
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
            }
        });



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
        return blindPanel;

    }
    private WebPanel initChangeImageFileType(){
        WebPanel imageSwap = new WebPanel();
        imageSwap.setBorder(BorderFactory.createTitledBorder("Image type swap"));
        String[] types = ImageIO.getReaderFileSuffixes();
        String[] typeWithAll = new String[types.length + 1];
        for(int i = 0; i < types.length; i++){
            typeWithAll[i+1] = types[i];
        }
        typeWithAll[0] = "ALL";
        WebCheckBox onlySelected = new WebCheckBox("Only Selected");
        WebCheckBox duplicate = new WebCheckBox("Save as copy");
        duplicate.setSelected(true);
        duplicate.setEnabled(false);
        WebComboBox old = new WebComboBox(typeWithAll);
        WebLabel arrow = new WebLabel("---->");
        WebComboBox newfile = new WebComboBox(types);
        WebButton go = new WebButton(" Run ");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onlySelected.isSelected()){
                    File panelsFile = ((FilePanel) (CenterController.getCenterController().getSelectedDocument().getComponent())).file;
                    FileSwapUtils.swapImageFilesAsync(panelsFile,((FilePanel) (CenterController.getCenterController().getSelectedDocument().getComponent())).getSelectedFiles(), old.getSelectedItem().toString(), newfile.getSelectedItem().toString(),duplicate.isSelected());
                }else {
                    File panelsFile = ((FilePanel) (CenterController.getCenterController().getSelectedDocument().getComponent())).file;
                    FileSwapUtils.swapImageFilesAsync(panelsFile,panelsFile.listFiles(), old.getSelectedItem().toString(), newfile.getSelectedItem().toString(),duplicate.isSelected());

                }
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        imageSwap.setLayout(new GridBagLayout());
        c.gridy = 0;
        c.gridx = 0;
        imageSwap.add(onlySelected,c);
        c.gridx++;
        imageSwap.add(old, c);
        c.gridx++;
        imageSwap.add(arrow, c);
        c.gridx++;
        imageSwap.add(newfile, c);

        c.gridx = 0;
        c.gridy++;
        imageSwap.add(duplicate,c);
        c.gridx++;
        c.gridx++;
        c.gridx++;
        imageSwap.add(go,c);
        return imageSwap;
    }
    @Override
    protected void init() {
        WebPanel panel = new WebPanel();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        c.gridy = 0;
        c.gridx = 0;
        panel.add(initBlind(),c);
        c.gridy++;
        panel.add(initChangeImageFileType(),c);
        WebScrollPane scrollPane = new WebScrollPane(panel);
        scrollPane.setStyleId(StyleId.scrollpaneHovering);
        add(scrollPane);
    }


}
