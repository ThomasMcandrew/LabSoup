package soup.main.filetree;

import com.alee.extended.tree.FileTreeNode;
import com.alee.extended.tree.WebFileTree;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.tree.TreeNodeEventRunnable;
import com.alee.managers.style.StyleId;
import com.alee.utils.compare.Filter;
import com.alee.utils.filefilter.FilesFilter;
import soup.main.center.CenterController;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;

public class FileTree extends WebFileTree implements MouseListener {
    public static Filter<File> filter;
    private Filter<File> on = new Filter<File>() {
        @Override
        public boolean accept(File file) {
            if(file.getName().startsWith(".")){
                return false;
            }
            return true;
        }
    };
    public boolean filterOn(){
        return filter == on;
    }
    public FileTree(){
        super(System.getProperty("user.home"));
        setStyleId(StyleId.filetreeTransparent);
        setFileFilter(filter);
        addMouseListener(this);
    }
    public FileTree(String rootPath){
        super(rootPath);
        setStyleId(StyleId.filetreeTransparent);
        setFileFilter(filter);
        addMouseListener(this);
    }
    public void toggleHiddenFiles(){
        if(getFileFilter() == null){
            setFileFilter(on);
            filter = on;
        }else{
            setFileFilter(null);
            filter = null;
        }
        }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2){
            if(getSelectedFile().isDirectory() && e.getClickCount() >= 3){
                CenterController.getCenterController().openFile(getSelectedFile());
            }
            if(!getSelectedFile().isDirectory()) {
                CenterController.getCenterController().openFile(getSelectedFile());
            }
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            WebPopupMenu menu = new WebPopupMenu();
            WebMenuItem openFolder = new WebMenuItem("Open Folder");
            openFolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    CenterController.getCenterController().openFile(getClosestNodeForLocation(e.getX(),e.getY()).getFile());

                }
            });
            menu.add(openFolder);
            menu.show(e.getComponent(),e.getX(),e.getY());
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
}
