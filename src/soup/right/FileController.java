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
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BlindUtils.BlindFolder(controller, panel.file,folders.getText(),".jpg");
            }
        });

        setLayout(new FlowLayout());
        add(blind);
        add(split);
        add(folders);
        add(run);
    }

    @Override
    protected void setPanel(AbstractPanel panel) {
        this.panel = (FileExplorer)panel;
    }
}
