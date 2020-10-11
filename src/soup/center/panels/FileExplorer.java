package soup.center.panels;

import soup.center.CenterController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class FileExplorer extends AbstractPanel {

    private File lastSelected;

    public FileExplorer(CenterController centerController, File file, int width, int height) {
        super(centerController, file, "\\", width, height);
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return new FileExplorer(centerController,file,width,height);
    }

    @Override
    public String saveFile() {
        return null;
    }

    @Override
    public void loadFile(File file) {
        JList list = new JList(file.listFiles());
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() > 1){
                    centerController.openFile((File)(list.getSelectedValue()));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JScrollPane scroller = new JScrollPane(list);
        add(scroller);
    }
}
