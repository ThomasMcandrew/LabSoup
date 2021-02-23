package soup.main;

import com.alee.extended.split.MultiSplitState;
import com.alee.extended.split.MultiSplitViewState;
import com.alee.extended.split.WebMultiSplitPane;
import com.alee.extended.split.WebMultiSplitPaneDivider;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.managers.style.StyleId;
import soup.main.center.CenterController;
import soup.main.editor.EditorController;
import soup.main.filetree.FileTree;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainPanel extends WebPanel {

    private static MainPanel mainPanel;
    public static MainPanel getMainPanel(){
        if(mainPanel == null){
            mainPanel = new MainPanel();
        }
        return mainPanel;
    }
    private static FileTree fileTree;
    public static FileTree getFileTree(){
        return fileTree;
    }
    private static WebScrollPane webScrollPane;
    private static WebMultiSplitPane splitPane;
    private static MultiSplitPaneSettings multiSplitPaneSettings;
    private int fileExplorerDividerLocation;
    private int EditorDividerLocation;
    public static void setNewFileTree(String file){
        fileTree = new FileTree(file);
        webScrollPane = new WebScrollPane(fileTree);
        splitPane.remove(0);
        splitPane.add(webScrollPane,0);
    }
    private MainPanel(){
        setStyleId(StyleId.panelTransparent);
        splitPane = new WebMultiSplitPane(StyleId.splitpaneTransparent);

        splitPane.setOneTouchExpandable(true);
        fileTree = loadFileTree();
        if(fileTree == null) {
            fileTree = new FileTree();
        }
        webScrollPane = new WebScrollPane(fileTree);
        webScrollPane.setStyleId(StyleId.scrollpaneHovering);
        webScrollPane.setPreferredWidth(200);
        WebLabel fileExplorer = new WebLabel("FileExplorer   ");
        fileExplorer.setStyleId(StyleId.labelVerticalCCW);
        fileExplorer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(splitPane.getDividers().get(0).getLocation().x > 0){
                    //splitPane.getViewComponent(0).setEnabled(false);

                    //splitPane.getDividers().get(0).setLocation(0,0);
                }else{
                    //splitPane.getDividers().get(0).setLocation(200,0);
                }
            }
        });

        splitPane.add(webScrollPane,0);
        splitPane.setDividerSize(1);
        CenterController centerController = CenterController.getCenterController();
        splitPane.add(centerController,1);

        EditorController editorController = new EditorController();
        splitPane.add(editorController,2);

        WebPanel webPanel = new WebPanel();
        webPanel.add(fileExplorer,BorderLayout.NORTH);
        add(webPanel,BorderLayout.WEST);


        multiSplitPaneSettings = new MultiSplitPaneSettings();

        splitPane.setMultiSplitState(multiSplitPaneSettings);
        add(splitPane,BorderLayout.CENTER);

    }
    public void saveFileTree(){
        String path = fileTree.getRootNode().getFile().getAbsolutePath();
        boolean filter = fileTree.filterOn();
        File save = new File(FileUtils.getLabSoupFolder() + File.separator + "lsft.cfg");
        try {
            FileWriter writer = new FileWriter(save);
            writer.write(path + "\n" + filter);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private FileTree loadFileTree(){
        File save = new File(FileUtils.getLabSoupFolder() + File.separator + "lsft.cfg");
        try {
            Scanner scan = new Scanner(save);
            String path = scan.nextLine();
            boolean on = scan.nextBoolean();
            FileTree tree = new FileTree(path);
            if(on){
                if(!tree.filterOn()){
                    tree.toggleHiddenFiles();
                }
            }else{
                if(tree.filterOn()){
                    tree.toggleHiddenFiles();
                }
            }
            return tree;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
