package soup.center.panels;

import javax.swing.*;
import java.io.File;

public class FileExplorer extends AbstractPanel {



    public FileExplorer(File file, int width, int height) {
        super(file, "\\", width, height);
    }

    @Override
    public AbstractPanel newPanel(File file, int width, int height) {
        return new FileExplorer(file,width,height);
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

        JScrollPane scroller = new JScrollPane(list);
        add(scroller);
    }
}
