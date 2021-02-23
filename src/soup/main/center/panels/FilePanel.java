package soup.main.center.panels;

import com.alee.extended.filechooser.WebFileTable;
import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

public class FilePanel extends AbstractPanel {
    private File lastSelected;

    public FilePanel(File file) {
        super(file, "\\", Icons.folder);
    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new FilePanel(file);
    }

    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {
        WebFileTable webFileTable = new WebFileTable();
        webFileTable.addFiles(Arrays.asList(file.listFiles()));
        webFileTable.setStyleId(StyleId.filetable);
        webFileTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() > 1){
                    CenterController.getCenterController().openFile((File)(webFileTable.getSelectedFile()));
                }
            }
        });

        WebScrollPane scroller = new WebScrollPane(webFileTable);
        add(scroller);
    }
}
