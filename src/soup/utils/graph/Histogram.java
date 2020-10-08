package soup.utils.graph;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Histogram {


    public static BufferedImage getGraph(String title, String xLabel, String yLabel, String[] labels, double[] values,String[] config){
        double max = 0;
        for(int i = 0; i < values.length; i++){
            if(values[i] > max){
                max = values[i];
            }
        }
        int colwidth = 10;
//        int width = (labels.length * colwidth) + (labels.length * (colwidth/3));
//        int height = (int) Math.ceil(max);
        //Graphics2D
        int width = 1200;
        int height = 630;
        
        return null;
    }
}
