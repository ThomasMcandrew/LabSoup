package soup.center.panels;

import soup.center.CenterController;
import soup.right.TextController;
import soup.utils.FileUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextFile extends AbstractPanel {

    private JTextArea textArea;

    public TextFile(CenterController centerController, File file,String ext, int width, int height){
        super(centerController, file, ext,width,height);
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        if(file == null){
            return new TextFile(centerController, null,".txt",width, height);
        }
        return new TextFile(centerController, file,file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")),width, height);
    }

    @Override
    public String saveFile() {
        if(file == null){
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
        return file.getName();
    }

    private void initTextArea(){


        JScrollPane scroller;
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        scroller = new JScrollPane(textArea);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scroller.setPreferredSize(new Dimension(getWidth() - 20, getHeight()));
            }
        });

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
                textArea.append(text);
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
