package soup.main.center.panels;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpeedReaderSplitPane extends WebPanel {
    private String text;
    private String[] words;
    private WebLabel display;
    private boolean reading;
    private int iterator;
    private WebTextArea textArea;
    private int wordsPerMin;
    private Highlighter highlighter;
    private Timer timer;
    public SpeedReaderSplitPane(String input){
        super();
        this.text = input;
        iterator = 0;
        reading = false;
        wordsPerMin = 150;
        words = input.split(" ");
        init();
        createTread();
    }
    private void init(){
        display = new WebLabel("Start");
        display.setFontSize(50);
        WebButton start = new WebButton(Icons.accept);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        start.setPreferredSize(80,80);
        WebButton pause = new WebButton(Icons.cross);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        pause.setPreferredSize(80,80);
        WebTextField wpm = new WebTextField("150");
        wpm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int temp = Integer.parseInt(wpm.getText());
                    if(temp > 0 && temp < 500){
                        wordsPerMin = temp;
                        createTread();
                    }else{
                        wpm.setText(wordsPerMin + "");
                    }
                }catch (NumberFormatException nfe){
                    wpm.setText(wordsPerMin + "");
                }
            }
        });
        wpm.setPreferredSize(360,80);
        WebPanel mainPanel = new WebPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        WebPanel t = new WebPanel();
        t.setLayout(new GridBagLayout());
        WebPanel f = new WebPanel();
        f.add(display,BorderLayout.CENTER);
        mainPanel.add(new WebLabel("This is here to help read faster, start at your regular reading speed and increase when you can. try not to read the words \n" +
                "just understand them by looking at them..."),BorderLayout.NORTH);
        mainPanel.add(f,BorderLayout.CENTER);
        c.gridwidth = 1;
        t.add(pause,c);
        c.gridx++;
        t.add(start,c);
        c.gridx++;
        t.add(wpm,c);
        mainPanel.add(t,BorderLayout.SOUTH);
        textArea = new WebTextArea();
        textArea.setPreferredSize(new Dimension((int)(200),getHeight()));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setStyleId(StyleId.textpaneDecorated);
        textArea.setText(text);
        add(textArea, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

    }
    private void createTread(){
        if(timer != null && timer.isRunning()){
            timer.stop();
        }
        highlighter = textArea.getHighlighter();
        timer = new Timer(60000 / wordsPerMin, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(iterator >= words.length){
                    return;
                }
                display.setText(words[iterator]);
                iterator++;
            }
        });
    }
    private void start(){
        timer.start();
    }
    private void stop(){
        timer.stop();
    }

}
