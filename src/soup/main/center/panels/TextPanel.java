package soup.main.center.panels;


import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextPane;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextPanel extends AbstractPanel{

        private WebTextPane textArea;

        public TextPanel(File file, String ext) {
            super(file, ext, Icons.tiles);
        }

        @Override
        public AbstractPanel newPanel(File file) {
            if (file == null) {
                return new TextPanel( null, ".txt");
            }
            return new TextPanel(file, file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")));
        }

        @Override
        public String saveFile() {
            if (file == null) {
                file = FileUtils.saveTextFile();
            }
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            edited = false;
            return file.getName();
        }

        private void initTextArea() {
            WebScrollPane scroller;
            textArea = new WebTextPane();
            textArea.setStyleId(StyleId.textpaneTransparent);
            textArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    edited = true;
                }
            });
            scroller = new WebScrollPane(textArea);
            scroller.setStyleId(StyleId.scrollpaneTransparentHovering);
            add(scroller);
        }


        @Override
        public void loadFile(File file) {
            initTextArea();

            if (file != null) {

                Scanner scan = null;
                try {
                    scan = new Scanner(file);
                    String text = "";
                    while (scan.hasNext()) {
                        text += scan.nextLine() + "\n";
                    }
                    textArea.setText(text);
                    scan.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

