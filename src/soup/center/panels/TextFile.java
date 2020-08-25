package soup.center.panels;

import soup.right.TextController;
import soup.utils.FileUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextFile extends AbstractPanel {

    private JTextArea textArea;
    public TextFile(File file,int width,int height){
        super(file, ".txt",width,height);
    }



    @Override
    public AbstractPanel newPanel(File file,int width, int height) {
        return new TextFile(file,width, height);
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
        textArea.setPreferredSize(new Dimension(width - 20, height));
        JPanel p = new JPanel();
        p.add(textArea);
        scroller = new JScrollPane(p);

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
