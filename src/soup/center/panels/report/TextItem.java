package soup.center.panels.report;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TextItem extends AbstractItem {
    private JTextArea textArea;
    public TextItem(int x, int y, int width, int height) {
        super(x, y, width, height);

    }

    @Override
    protected void init() {
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(width,height));
        textArea.setLineWrap(true);
        add(textArea,BorderLayout.CENTER);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }
}
