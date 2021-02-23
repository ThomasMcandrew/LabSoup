package soup.main.editor.editors;

import com.alee.laf.button.WebButton;
import soup.main.editor.AbstractEditor;

public class TextEditor extends AbstractEditor {

    public TextEditor(){
        add(new WebButton("Foo"));
    }

    @Override
    protected void init() {

    }

}
