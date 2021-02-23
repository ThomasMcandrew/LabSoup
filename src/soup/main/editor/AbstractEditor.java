package soup.main.editor;


import com.alee.laf.panel.WebPanel;
import soup.main.filetree.FileTree;

import java.awt.*;

public abstract class AbstractEditor extends WebPanel {

    public AbstractEditor(){
        init();
    }
    protected abstract void init();
}
