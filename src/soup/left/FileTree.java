package soup.left;

import soup.center.CenterController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class FileTree extends JPanel implements MouseListener {
    private JTree tree;
    private DefaultMutableTreeNode root;
    private CenterController controller;
    public FileTree(CenterController controller){
        File file = new File(System.getProperty("user.home"));
        setBackground(Color.WHITE);
        makeFileTreeRecursive(file);
        this.controller = controller;
        tree.addMouseListener(this);
        setBorder(null);
        tree.setBorder(null);
    }



    private void makeFileTreeRecursive(File rootFile){
        root = new DefaultMutableTreeNode(rootFile.getName());
        helper(rootFile,root);
        tree = new JTree(root);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

           @Override
           public void valueChanged(TreeSelectionEvent e) {
               helper((FileNode)(((DefaultMutableTreeNode) (tree.getLastSelectedPathComponent())).getUserObject()), (DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
           }
        });
        add(tree);
        repaint();
    }

    private void helper(FileNode fileNode, DefaultMutableTreeNode node){
         File file = fileNode.file;
         helper(file,node);
    }
    private void helper(File file, DefaultMutableTreeNode node){
         File[] search = file.listFiles();
         if(search == null || search.length < 1) {
             return;
         }
         for(int i = 0; i < search.length;i++){
             if(!search[i].getName().startsWith(".")) {
                 DefaultMutableTreeNode temp = new DefaultMutableTreeNode(new FileNode(search[i]));
                    node.add(temp);
                    if (search[i].isDirectory()) {
                        temp.add(new DefaultMutableTreeNode(null));
                    }
                }
            }
    }

    public JTree getTree() {
        return tree;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){
            JPopupMenu pop = new JPopupMenu();
            JMenuItem openFolder = new JMenuItem("Open Folder");
            openFolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.openDirectory((FileNode)(((DefaultMutableTreeNode) (tree.getLastSelectedPathComponent())).getUserObject()));
                }
            });
            pop.add(openFolder);
            JMenuItem openAsText = new JMenuItem("Open as Text file");
            openAsText.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.openFileAsText((FileNode)(((DefaultMutableTreeNode) (tree.getLastSelectedPathComponent())).getUserObject()));
                }
            });
            pop.add(openAsText);
            pop.show(e.getComponent(),e.getX(),e.getY());
        }
        if(e.getClickCount() >= 2){
            controller.openFile((FileNode)(((DefaultMutableTreeNode) (tree.getLastSelectedPathComponent())).getUserObject()));
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
