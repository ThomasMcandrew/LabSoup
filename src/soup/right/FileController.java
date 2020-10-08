package soup.right;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.FileExplorer;
import soup.utils.files.BlindUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileController extends AbstractController {

    private FileExplorer panel;

    public FileController(CenterController controller) {
        super(controller);
    }


    @Override
    protected void init() {
        JLabel blind = new JLabel("Blind Files");
        JLabel split = new JLabel("Split folder by substring. use ',' to separate values");
        JTextField folders = new JTextField(12);
        JButton run = new JButton("Run");
        JLabel useOther = new JLabel("Use other File format");
        JCheckBox useOtherFileFormat = new JCheckBox();
        JTextField fileFormat = new JTextField(5);
        fileFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useOtherFileFormat.setSelected(true);
            }
        });
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(useOtherFileFormat.isSelected()){
                    String ext;
                    if(fileFormat.getText().startsWith(".")){
                        ext = fileFormat.getText();
                    }else{
                        ext = "." + fileFormat.getText();
                    }
                    BlindUtils.BlindFolder(controller, panel.file, folders.getText(), ext);
                }else {
                    BlindUtils.BlindFolder(controller, panel.file, folders.getText(), ".jpg");
                }
            }
        });

        setLayout(new FlowLayout());
        add(blind);
        add(split);
        add(folders);
        add(useOther);
        add(useOtherFileFormat);
        add(fileFormat);
        add(run);
    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        this.panel = (FileExplorer)panel;
    }
}
