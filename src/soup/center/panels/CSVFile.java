package soup.center.panels;

import soup.center.CenterController;
import soup.right.CSVController;
import soup.utils.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVFile extends AbstractPanel {

    private JTable table;
    public CSVFile(CenterController centerController, File file, int width, int height) {
        super(centerController, file, ".csv", width, height);
    }


    public JTable getTable(){
        return table;
    }

    @Override
    public AbstractPanel newPanel(CenterController centerController, File file, int width, int height) {
        return new CSVFile(centerController,file,width,height);
    }

    @Override
    public String saveFile() {
        if(file == null){
            file = FileUtils.saveCSVFile();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            TableModel model = (TableModel) table.getModel();
            for(int y = 0; y < model.getRowCount(); y++){
                for(int x = 0; x < model.getColumnCount(); x++){
                    fileWriter.write(model.getValueAt(y,x) + ",");
                }
                fileWriter.write("\n");
            }

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

    private void initTable(){
        table = new JTable(new TableModel());
        for(int i = 0; i < table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(65);
        }
        setPreferredSize(new Dimension(65*26,table.getModel().getRowCount()*10+10));
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(table, BorderLayout.CENTER);
        JScrollPane pane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(pane);
    }


    @Override
    public void loadFile(File file) {
        initTable();
        if(file != null){
            try {
                Scanner scan = new Scanner(file);
                String s = "";
                TableModel model = (TableModel) table.getModel();
                while (scan.hasNextLine()){
                    s += scan.nextLine() + "\n";
                }
                String[] c = s.split("\n");

                for(int i = 0; i < c.length; i++){
                    String[] d = c[i].split(",");
                    for(int j = 0; j < d.length; j++){
                        model.setValueAt(d[j],i,j);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
