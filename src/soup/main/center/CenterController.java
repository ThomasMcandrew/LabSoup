package soup.main.center;

import com.alee.extended.tab.*;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.notification.NotificationListener;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.NotificationOption;
import com.alee.managers.notification.WebInnerNotification;
import com.alee.managers.style.StyleId;
import com.alee.utils.swing.ColorUIResource;
import com.alee.utils.swing.Customizer;
import soup.main.center.panels.*;
import soup.utils.FileUtils;

import javax.imageio.ImageIO;
import javax.management.Notification;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CenterController extends WebDocumentPane {

    private static CenterController centerController;
    public static CenterController getCenterController(){
        if(centerController == null){
            centerController = new CenterController();
        }
        return centerController;
    }
    private static AbstractPanel[] panels = new AbstractPanel[]{new TextPanel(null,".txt"),new CSVPanel(null),new LayeredImagePanel(null) };

    private CenterController(){
        setStyleId(StyleId.documentpane);
        setSplitEnabled(true);
        setDragEnabled(true);
        setDragBetweenPanesEnabled(true);
        setTabMenuEnabled(true);
        addImageExtensionsToArray();
        setTabbedPaneCustomizer(new Customizer<WebTabbedPane>(){

            @Override
            public void customize(WebTabbedPane webTabbedPane) {
                webTabbedPane.setTabLayoutPolicy(WebTabbedPane.SCROLL_TAB_LAYOUT);
            }
        });
        onDocumentClose(new DocumentDataRunnable() {
            @Override
            public void run(DocumentData documentData, PaneData paneData, int i) {
                AbstractPanel temp = (AbstractPanel) documentData.getComponent();
                if(temp.file.getAbsolutePath().startsWith(FileUtils.getTempFolder().getAbsolutePath()) && temp.edited == false){
                    temp.file.delete();
                }else {
                    if(!temp.edited){
                        return;
                    }
                    WebInnerNotification win = NotificationManager.showInnerNotification("Save File?\n" + temp.file.getName(), NotificationOption.yes, NotificationOption.no);
                    win.addNotificationListener(new NotificationListener() {
                        @Override
                        public void optionSelected(NotificationOption notificationOption) {
                            if (notificationOption.compareTo(NotificationOption.no) == 0) {
                                if(temp.file.getAbsolutePath().startsWith(FileUtils.getTempFolder().getAbsolutePath())) {
                                    temp.file.delete();
                                }
                            }
                            if(notificationOption.compareTo(NotificationOption.yes) == 0){
                                temp.saveFile();
                            }
                        }

                        @Override
                        public void accepted() {

                        }

                        @Override
                        public void closed() {

                        }
                    });
                }
            }
        });
    }
    private void addImageExtensionsToArray(){
        String[] exts = ImageIO.getReaderFileSuffixes();
        ImagePanel[] Ipanels = new ImagePanel[exts.length];
        for(int i = 0; i < exts.length; i++){
            Ipanels[i] = new ImagePanel(null,exts[i]);
        }
        AbstractPanel[] newPanels = new AbstractPanel[panels.length + Ipanels.length];
        for(int i = 0; i < newPanels.length; i++){
            if(i < panels.length){
                newPanels[i] = panels[i];
            }else{
                newPanels[i] = Ipanels[i-panels.length];
            }
        }
        panels = newPanels;
    }
    public void saveSelectedAsCopy(){
        AbstractPanel panel = (AbstractPanel) getSelectedDocument().getComponent();
        panel.file = null;
        saveSelected();
    }
    public void saveSelectedAs(){
        AbstractPanel panel = (AbstractPanel) getSelectedDocument().getComponent();
        File old = panel.file;
        panel.file = null;
        saveSelected();
        old.delete();
    }
    public void saveSelected(){
        AbstractPanel panel = (AbstractPanel) getSelectedDocument().getComponent();
        String name = panel.saveFile();
        getSelectedDocument().setTitle(name);
    }
    public void newPanel(AbstractPanel panel){
        if(panel.file == null) {
            panel.file = new File(FileUtils.getTempFolder().getAbsolutePath() + File.separator + FileUtils.getFreeFileName() + panel.ext);
            try {
                panel.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        openDocument(new DocumentData(panel.file.getAbsolutePath(), panel.icon, panel.file.getName(), Color.white/*maybe cool to color specify*/, panel));
    }
    public void openFile(File file){
        if(file.isDirectory()){
            newPanel(new FilePanel(file));
        }
        if(fileOpened(file)){
            return;
        }
        boolean set = false;
        String ext = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        for(int i = 0; i < panels.length; i++){
            if(ext.endsWith(panels[i].ext)){
                AbstractPanel temp = panels[i].newPanel(file);
                newPanel(temp);
                set = true;
                break;
            }
        }
        if(!set){
            AbstractPanel temp = panels[0].newPanel(file);
            newPanel(temp);
        }

    }
    private boolean fileOpened(File file){
        if(isDocumentOpened(file.getAbsolutePath())){
            isDocumentOpened(file.getAbsolutePath());
            return true;
        }
        return false;
    }
    public static void saveCurrentConfiguration()  {
        List<DocumentData> list = centerController.getDocuments();
        String saveData = "";
        AbstractPanel p = null;
        for (int i = 0; i < list.size(); i++){
            p = (AbstractPanel) list.get(i).getComponent();
            p.saveFile();
            saveData += p.file.getAbsolutePath() + "\n";
        }
        try {
            FileWriter writer = new FileWriter(new File(FileUtils.getLabSoupFolder() + File.separator + "cc.cfg"));
            writer.write(saveData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadConfiguration(){
        Scanner s = null;
        try {
            s = new Scanner(new File(FileUtils.getLabSoupFolder() + File.separator + "cc.cfg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(s == null){
            return;
        }
        String temp;
        while (s.hasNext()){
            temp = s.nextLine();
            File f = new File(temp);
            if(f.exists()) {
                centerController.openFile(f);
            }else{
                NotificationManager.showNotification("File: " + temp +"\nis unable to be opened");
            }
        }
        s.close();

    }
}
