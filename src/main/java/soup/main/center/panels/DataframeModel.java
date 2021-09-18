package soup.main.center.panels;

import com.alee.extended.split.OneTouchButton;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataframeModel extends AbstractTableModel {
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    private ArrayList<ArrayList<Object>> showData = new ArrayList<>();
    private String query = null;
    private int MIN_WIDTH = 50, MIN_HEIGHT = 100;
    private int Width;
    private int Height;
    private int HeaderLine;
    
    public void setHeaderLine(int val){
        HeaderLine = val;
    }
    public int getHeaderLine(){
        return HeaderLine;
    }
    
    public void query(String query) throws Exception {
        this.query = query;
        List<String> queryLang = Arrays.asList(new String[]{"Select","Where"});
        ArrayList<String> headers = new ArrayList<>();
        for (int i = 0 ; i < data.size(); i++){
            headers.add(data.get(i).get(HeaderLine).toString().trim());
        }
        List<String> input = Arrays.asList(query.split(" "));
        input.forEach(x -> x.trim());
        for (String s :
                input) {
            if(!(queryLang.stream().anyMatch((x) -> x.compareTo(s) == 0) || queryLang.stream().anyMatch((x) -> x.compareTo(s) == 0))){
                throw new Exception("Error on input" + s);
            }
        }


    }
    public void addColumn(){
        for (ArrayList a :
                data) {
            a.add(null);
        }
    }
    public void addColumn(int loc){
        for (ArrayList a :
                data) {
            a.add(loc,null);
        }
    }
    public void addRow(){
        ArrayList<Object> temp = new ArrayList<>();
        for (int i = 0; i < Width; i++) {
            temp.add(null);
        }
        data.add(temp);
    }
    public void addRow(int loc){
        ArrayList<Object> temp = new ArrayList<>();
        for (int i = 0; i < Width; i++) {
            temp.add(null);
        }
        data.add(loc,temp);
    }
    public void setUneditable(){

    }
    public void setEditable(){

    }
    public void setValueAt(Object value, int row, int col) {
        if(value == null)
            value = "";
        data.get(row).set(col,value);
        fireTableCellUpdated(row, col);
    }
    public void loadNewEmpty(int width, int height){
        if(width < MIN_WIDTH){
            width = MIN_WIDTH;
        }
        if(height < MIN_HEIGHT){
            height = MIN_HEIGHT;
        }
        for(int i = 0; i < Height; i++){
            ArrayList<Object> temp = new ArrayList<>();
            for (int j = 0; j < Width; j++) {
                temp.add(null);
            }
            data.add(temp);
        }
        Width = width;
        Height = height;
    }
    public void loadNewEmpty(){
        Width = MIN_WIDTH;
        Height = MIN_HEIGHT;
        for(int i = 0; i < Height; i++){
            ArrayList<Object> temp = new ArrayList<>();
            for (int j = 0; j < Width; j++) {
                temp.add(null);
            }
            data.add(temp);
        }
    }

    public void loadData(File file) {
        data = new ArrayList<>();
        ArrayList<String[]> tempData = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tempData.add(scanner.nextLine().split(","));
            }
            AtomicInteger maxWidth = new AtomicInteger();
            tempData.stream().max((x,y) -> x.length - y.length).ifPresent(x-> maxWidth.set(x.length));
            if(maxWidth.get() < MIN_WIDTH){
                maxWidth.set(MIN_WIDTH);
            }
            Width = maxWidth.get();
            int maxHeight = tempData.size();
            if(maxHeight < MIN_HEIGHT){
                maxHeight = MIN_HEIGHT;
            }
            Height = maxHeight;
            for (int i = 0; i < tempData.size(); i++) {
                ArrayList<Object> temp = new ArrayList<Object>(Width);
                for (int j = 0; j < tempData.get(i).length; j++) {
                        temp.add(tempData.get(i)[j]);
                }
                for (int j = tempData.get(i).length; j < Width; j++) {
                    temp.add(null);
                }
                data.add(temp);
            }
            while (data.size() < maxHeight)
                data.add(new ArrayList<>(maxWidth.get()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void saveData(File file) {
        String str = "";
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                if(data.get(i).get(j) == null) str += ",";
                else str += data.get(i).get(j) + ",";
            }
            str += "\n";
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    public boolean isCellEditable(int row, int col) {
        return true;
    }
    @Override
    public int getRowCount() {
        return Height;
    }

    @Override
    public int getColumnCount() {
        return Width;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(query == null) {
            return data.get(rowIndex).get(columnIndex);
        }else {
            return showData.get(rowIndex).get(columnIndex);
        }
    }
}
