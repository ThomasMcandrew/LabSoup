package soup.main.center.panels;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.managers.icon.Icons;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.style.StyleId;
import soup.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVPanel extends AbstractPanel {
    private WebTable table;
    public WebTable getTable(){
        return table;
    }
    public CSVPanel(File file) {
        super(file, ".csv", Icons.tiles);
    }

    @Override
    public AbstractPanel newPanel(File file) {
        return new CSVPanel(file);
    }

    @Override
    public String saveFile() {
        if(file == null){
            file = FileUtils.saveCSVFile();
        }
        ((DataframeModel) table.getModel()).saveData(file);
        return file.getName();
    }
    public static String saveFile(File file, WebTable t) {
        if(file == null){
            file = FileUtils.saveCSVFile();
        }
        ((DataframeModel) t.getModel()).saveData(file);
        return file.getName();
    }
    private void initTable(DataframeModel dataframeModel){
        table = new WebTable(dataframeModel);
        for(int i = 0; i < table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(65);
        }
        setPreferredSize(new Dimension(65*26,table.getModel().getRowCount()*10+10));
        WebPanel p = new WebPanel();
        p.setLayout(new BorderLayout());
        p.add(table, BorderLayout.CENTER);
        WebScrollPane pane = new WebScrollPane(table);
        pane.setStyleId(StyleId.scrollpaneTransparentHovering);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(pane);
    }

    @Override
    public void loadFile(File file) {
        DataframeModel dataframeModel = new DataframeModel();
        if(file == null){
            dataframeModel.loadNewEmpty();
        }else{
            dataframeModel.loadData(file);
        }
        initTable(dataframeModel);
    }
}
