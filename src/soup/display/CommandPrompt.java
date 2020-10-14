package soup.display;

import soup.center.panels.dumbPanels.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class CommandPrompt extends JTextField {


    private MainPanel mainPanel;
    private SwingWorker worker;
    private String command;
    public CommandPrompt(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        setBorder(BorderFactory.createLineBorder(Color.black));
        setText(">>>  ");
        addListeners();
        makeWorker();
    }
    private void makeWorker(){
        worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                if(command == null){
                    return null;
                }
                String[] commandArgs = command.split(" ");
                String cmd = commandArgs[0];
                String[] args = new String[commandArgs.length-1];
                for(int i = 0; i < commandArgs.length-1;i++){
                    args[i] = commandArgs[i+1];
                }
                switch (cmd){
                    case "snake":
                        //Snake s = new Snake(mainPanel.centerController,0,0);
                        mainPanel.centerController.newPanel("Snake",new Snake(mainPanel.centerController,0,0));
                        break;
                }
                return null;
            }
        };
    }
    private void run(){
        if(command == null){
            return;
        }
        String[] commandArgs = command.split(" ");
        String cmd = commandArgs[0];
        String[] args = new String[commandArgs.length-1];
        for(int i = 0; i < commandArgs.length-1;i++){
            args[i] = commandArgs[i+1];
        }
        switch (cmd){
            case "snake":
                //Snake s = new Snake(mainPanel.centerController,0,0);
                mainPanel.centerController.newPanel("Snake",new Snake(mainPanel.centerController,0,0));
                break;
        }
    }
    private void addListeners(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(getCaretPosition() <= 4){
                    setCaretPosition(5);
                }
            }
        });
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command = e.getActionCommand();
                setText(">>>  ");
                command = command.substring(5).trim();
                run();
            }
        });
    }





}
