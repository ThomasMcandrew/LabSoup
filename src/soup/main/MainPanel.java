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
    private static WebSplitPane webSplitPane;
    private static WebPanel leftPanelController;
    private static String fileTreeString = "TREE";
    private static String editorString = "Editor";
    private static String currentPanel = fileTreeString;
    private static CardLayout layout;
    public static void setNewFileTree(String file){
        fileTree.swapFile(new File(file));
    }
    private MainPanel(){
        setStyleId(StyleId.panelTransparent);
        webSplitPane = new WebSplitPane(StyleId.splitpane);

        leftPanelController = new WebPanel();
        layout = new CardLayout();
        leftPanelController.setLayout(layout);
        fileTree = loadFileTree();
        if(fileTree == null) {
            fileTree = new FileTree();
        }
        webScrollPane = new WebScrollPane(fileTree);
        webScrollPane.setStyleId(StyleId.scrollpaneTransparentHovering);
        webScrollPane.setPreferredWidth(200);
        WebLabel fileExplorer = new WebLabel("FileExplorer   ");
        fileExplorer.setStyleId(StyleId.labelVerticalCCW);
        fileExplorer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(currentPanel == fileTreeString && webSplitPane.getDividerLocation() != 0){
                    webSplitPane.setDividerLocation(0);
                }else if(currentPanel == fileTreeString && webSplitPane.getDividerLocation() == 0){
                    webSplitPane.setDividerLocation(230);
                }else if(currentPanel != fileTreeString){
                    layout.show(leftPanelController,fileTreeString);
                    currentPanel = fileTreeString;
                    if(webSplitPane.getDividerLocation() == 0){
                        webSplitPane.setDividerLocation(230);
                    }
                }
            }
        });
        WebLabel editorLabel = new WebLabel("Editor    ");
        //editorLabel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        editorLabel.setStyleId(StyleId.labelVerticalCCW);
        editorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(currentPanel == editorString && webSplitPane.getDividerLocation() != 0){
                    webSplitPane.setDividerLocation(0);
                }else if(currentPanel == editorString && webSplitPane.getDividerLocation() == 0){
                    webSplitPane.setDividerLocation(300);
                }else if(currentPanel != editorString){
                    layout.show(leftPanelController,editorString);
                    currentPanel = editorString;
                    if(webSplitPane.getDividerLocation() < 280){
                        webSplitPane.setDividerLocation(280);
                    }
                }
            }
        });

        CenterController centerController = CenterController.getCenterController();

        EditorController editorController = new EditorController();

        WebPanel webPanel = new WebPanel();
        GridBagConstraints c = new GridBagConstraints();
        webPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        webPanel.add(fileExplorer,c);
        c.gridy++;
        webPanel.add(editorLabel,c);
        WebPanel another = new WebPanel();
        another.add(webPanel,BorderLayout.NORTH);
        add(another,BorderLayout.WEST);

        leftPanelController.add(editorController,editorString);
        leftPanelController.add(webScrollPane,fileTreeString);
        layout.show(leftPanelController,fileTreeString);
        webSplitPane.setLeftComponent(leftPanelController);
        webSplitPane.setRightComponent(centerController);
        webSplitPane.setEnabled(true);
        leftPanelController.setMinimumSize(0,getHeight());
        centerController.setMinimumSize((int) (webSplitPane.getWidth()*.8), getHeight());
        webSplitPane.setDividerLocation(230);
        webSplitPane.setStyleId(StyleId.splitpaneUndecorated);
        add(webSplitPane,BorderLayout.CENTER);

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
