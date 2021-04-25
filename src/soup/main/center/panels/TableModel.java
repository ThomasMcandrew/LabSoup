package soup.main.center.panels;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

    private String[] columnNames = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L", "M", "N","O","P","Q","R","S","T","U", "V","W","X", "Y","Z","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L", "M", "N","O","P","Q","R","S","T","U", "V","W","X", "Y","Z","Looks like you need more Columns :/"};
    private Object[][] data;

    public TableModel(){
        data = new Object[100][columnNames.length];

        for(int y = 0; y < getRowCount(); y++){
            for(int x = 0; x < columnNames.length; x++){
                data[y][x] = "";
            }
        }
    }
    public void removeRows(int[] rows){
        for(int i = 0; i < rows.length; i++){
            for(int j = 0; j <)
        }
    }
    public void setValueAt(Object value, int row, int col) {
        checkSize(row,col);
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    private void checkSize(int row,int col){
        if(row >= getRowCount()){
            Object[][] temp = new Object[getRowCount()*2][getColumnCount()];
            for(int y = 0; y < getRowCount(); y++){
                for(int x = 0; x < getColumnCount(); x++){
                    temp[y][x] = data[y][x];
                }
            }
            data = temp;
        }
        if(col >= getColumnCount()){
            Object[][] temp = new Object[getRowCount()][getColumnCount()*2];
            for(int y = 0; y < getRowCount(); y++){
                for(int x = 0; x < getColumnCount(); x++){
                    temp[y][x] = data[y][x];
                }
            }
            data = temp;
        }

    }
    public boolean isCellEditable(int row, int col) {
        return true;
    }
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
