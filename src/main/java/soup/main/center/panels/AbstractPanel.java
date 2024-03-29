package soup.main.center.panels;

import com.alee.extended.tab.DocumentData;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;

import javax.swing.*;
import java.io.File;

public abstract class AbstractPanel extends WebPanel {

    public Icon icon;
    public File file;
    public String ext;
    public boolean edited;
    protected DocumentData documentData;
    public AbstractPanel(File file, String ext, Icon icon){
        setStyleId(StyleId.panelTransparent);
        this.file = file;
        this.ext = ext;
        this.icon = icon;
        edited = false;
        loadFile();
    }

    public void setDocumentData(DocumentData documentData) {
        this.documentData = documentData;
    }

    public abstract AbstractPanel newPanel(File file);
    public abstract String saveFile();
    protected void loadFile(){
        loadFile(file);
    }
    public abstract void loadFile(File file);
}
