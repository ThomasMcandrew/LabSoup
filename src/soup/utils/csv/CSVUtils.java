package soup.utils.csv;

import soup.main.center.panels.TableModel;

public class CSVUtils {

    public static void findAndReplace(TableModel model, String input, String output){
        int width = model.getColumnCount();
        int height = model.getRowCount();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(model.getValueAt(y,x) != null && model.getValueAt(y,x).toString().compareTo(input) == 0){
                    model.setValueAt(output, y, x);
                }
            }
        }
    }
    public enum RowOrCol{
        ROW,COL
    }
    public static void removeNull(RowOrCol roc,String value){

    }

}
