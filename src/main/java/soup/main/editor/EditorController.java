package soup.main.editor;

import com.alee.extended.tab.DocumentAdapter;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.PaneData;
import com.alee.laf.panel.WebPanel;
import soup.main.center.CenterController;
import soup.main.center.panels.AbstractPanel;
import soup.main.editor.editors.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class EditorController extends WebPanel {

    private EditorController editorController;

    private WebPanel nullPanel;
    private TextEditor textEditor;
    private CSVEditor csvEditor;
    private FileEditor fileEditor;
    private ImageEditor imageEditor;
    private LayeredImageEditor layeredImageEditor;

    private String nullString = "NULL";
    private String textString = "TEXT";
    private String csvString = "CSV";
    private String fileString = "FILE";
    private String imageString = "IMAGE";
    private String layeredImageString = "GIF";

    public EditorController(){
        editorController = this;
        CardLayout layout = new CardLayout();
        setLayout(layout);

        nullPanel = new WebPanel();
        textEditor = new TextEditor();
        csvEditor = new CSVEditor();
        fileEditor = new FileEditor();
        imageEditor = new ImageEditor();
        layeredImageEditor = new LayeredImageEditor();



        add(nullPanel,nullString);
        add(textEditor,textString);
        add(csvEditor,csvString);
        add(fileEditor,fileString);
        add(imageEditor,imageString);
        add(layeredImageEditor,layeredImageString);

        CenterController.getCenterController().addDocumentListener(new DocumentAdapter() {
            @Override
            public void selected(DocumentData document, PaneData pane, int index) {
                super.selected(document, pane, index);
                AbstractPanel panel = (AbstractPanel) document.getComponent();
                if(panel == null){
                    return;
                }
                switch (panel.ext){
                    case ".txt":
                        layout.show(editorController,textString);
                        break;
                    case ".csv":
                        layout.show(editorController,csvString);
                        break;
                    case "\\":
                    case "/":
                        layout.show(editorController, fileString);
                        break;
                    case ".gif":
                        layout.show(editorController,layeredImageString);
                        break;
                    case ".jpg":
                    case ".tif":
                    case ".tiff":
                    case ".bmp":
                    //case ".gif":
                    case ".png":
                    case ".jpeg":
                    case ".wbmp":
                        layout.show(editorController,imageString);
                        break;
                    default:
                        layout.show(editorController, nullString);
                        break;
                }
            }
        });





    }

}
