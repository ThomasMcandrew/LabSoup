package soup.utils.csv.math;

import soup.center.panels.CSVFile;
import soup.center.panels.TableModel;

public class CSVMathUtil {

    public static void addCol(CSVFile panel, int colToAdd, int colFrom, int startRow,int outCol1,int outCol2,  String op){
        TableModel model = (TableModel) panel.getTable().getModel();
        DoubleList list = new DoubleList();

        for(int i = startRow; i < model.getRowCount();i++){
            try{
                int temp = Integer.parseInt(model.getValueAt(i,colFrom).toString());
                if(!list.containsKey(temp)){
                    list.put(temp,0);
                }
            } catch (NumberFormatException e  ){

            } catch (NullPointerException np){

            }
        }
        for(int i = startRow; i < model.getRowCount(); i++){
            try{
                double temp = Double.parseDouble(model.getValueAt(i,colToAdd).toString());
                int templabel = Integer.parseInt(model.getValueAt(i,colFrom).toString());
                list.add(templabel,temp);
            }catch (NumberFormatException e){

            }catch (NullPointerException np){

            }
        }
        model.setValueAt("Output",0,outCol1);
        double[] t;
        for(int i = 0; i < list.length();i++){
            t = list.getKeyAndVal(i);
            model.setValueAt((int) t[0],i+1,outCol1);
            model.setValueAt(t[1],i+1,outCol2);
        }
    }

}

