package soup.center.panels.dumbPanels;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.right.dumbControllers.TypeRaceController;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TypeRace extends AbstractPanel implements KeyListener {

    private JTextPane textArea;
    private TypeRace typeRace;
    private int iterator;
    private Object lastHighlight;
    private TypeRaceController typeRaceController;
    private boolean wordFailed;
    private int success, fail;
    private int totalWords, successfulWords;
    private long time;
    public TypeRace(CenterController centerController, File file, int width, int height) {
        super(centerController, file, "race", width, height);
        typeRace = this;
        success = 0;
        fail = 0;
        totalWords = 0;
        successfulWords = 0;
        wordFailed = false;
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return null;
    }

    @Override
    public String saveFile() {
        return null;
    }
    private void initTextArea(){
        JScrollPane scroller;
        textArea = new JTextPane();
        Font f = new Font(Font.MONOSPACED, 3, 20);
        textArea.setFont(f);
        textArea.setAutoscrolls(true);
        //textArea.setLineWrap(true);
        textArea.setEditable(false);
        scroller = new JScrollPane(textArea);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scroller.setPreferredSize(new Dimension(getWidth() - 20, getHeight()));
            }
        });
        textArea.addKeyListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textArea.requestFocus();
            }
        });
        iterator = 0;
        time = 0;
        textArea.requestFocus();
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
                    text += scan.nextLine() + " \n";
                }
                textArea.setText(textArea.getText() + text);
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            lastHighlight = textArea.getHighlighter().addHighlight(iterator, iterator+1, new DefaultHighlighter.DefaultHighlightPainter(Color.cyan));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setControls(TypeRaceController con){
        typeRaceController = con;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            return;
        }
        System.out.println("key pressed");
        if(time == 0){
            time = System.currentTimeMillis();
        }
        char current = 0;
        try {
            current = textArea.getText(iterator,1).charAt(0);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        if(e.getKeyChar() == current){
            try {
                if(current == ' '){
                    if(wordFailed){
                        wordFailed = false;
                        totalWords++;
                    }else{
                        totalWords++;
                        successfulWords++;
                    }
                }
                textArea.getHighlighter().removeHighlight(lastHighlight);
                textArea.getHighlighter().addHighlight(0, iterator+1, new DefaultHighlighter.DefaultHighlightPainter(Color.green));
                iterator++;
                while (textArea.getText(iterator,1).contains("\n")){
                    iterator++;
                }
                lastHighlight = textArea.getHighlighter().addHighlight(iterator, iterator+1, new DefaultHighlighter.DefaultHighlightPainter(Color.cyan));
                textArea.setCaretPosition(iterator);

            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }else{
            try {
                wordFailed = true;
                if(current == ' ') {
                    if (wordFailed) {
                        wordFailed = false;
                    }
                    totalWords++;
                }
                textArea.getHighlighter().removeHighlight(lastHighlight);

                textArea.getHighlighter().addHighlight(0, iterator+1, new DefaultHighlighter.DefaultHighlightPainter(Color.red));
                iterator++;
                while (textArea.getText(iterator,1).contains("\n")){
                    iterator++;
                }
                lastHighlight = textArea.getHighlighter().addHighlight(iterator, iterator+1, new DefaultHighlighter.DefaultHighlightPainter(Color.cyan));
                textArea.setCaretPosition(iterator);

            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
        typeRaceController.setCorrect(successfulWords);
        typeRaceController.setTotal(totalWords);
        long elapseTime = System.currentTimeMillis() - time;
        int wpm = (int)((successfulWords * 60000)/elapseTime);
        typeRaceController.setWPM(wpm);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
