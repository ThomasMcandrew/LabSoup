package soup.utils.files;

import soup.center.CenterController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class BlindUtils {

    public static void BlindFolder(CenterController controller, File file, String separateFolder, String ext){
        File out = new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator)) + File.separator +"output");
        out.mkdir();
        String[] sub =  separateFolder.split(",");
        File[] subFolder = new File[sub.length + 1];
        for(int i = 0; i < sub.length; i++){
            sub[i] = sub[i].trim();
            subFolder[i] = new File(out.getAbsolutePath() + File.separator + sub[i]);
            subFolder[i].mkdir();
        }
        subFolder[subFolder.length-1] = new File(out.getAbsolutePath() + File.separator + "Other");
        subFolder[subFolder.length-1].mkdir();
        File[] files = file.listFiles();

        String[][] data = new String[files.length][5];
        for(int i = 0; i < files.length; i++){
            data[i][0] = files[i].getName();
            for(int j = 0; j < sub.length; j++){
                if(data[i][0].contains(sub[j]) && data[i][1] == null){
                    data[i][1] = sub[j];
                    data[i][2] = data[i][0].replaceAll(data[i][1],"");
                    continue;
                }
            }
        }
        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if(o1[2] == null || o2[2] == null){
                    return 0;
                }
                return o1[2].compareTo(o2[2]);
            }
        });
        int iterator = -1;
        String last = "";
        for(int i = 0; i < files.length;i++){
            if(data[i][2] != null){
                if(data[i][2].compareTo(last) != 0){
                    iterator++;
                    last = data[i][2];
                }
                data[i][3] = iterator+"";
            }
        }
        ArrayList<Integer> available = new ArrayList<>();
        for(int i = 0; i < iterator + 1; i++){
            available.add(i);
        }
        int[][] blinds = new int[iterator+1][2];
        Random rand = new Random();
        for(int i = 0; i < iterator+1; i++){
            blinds[i][0] = i;
            blinds[i][1] = available.remove(rand.nextInt(available.size()));
        }
        for(int i = 0; i < files.length;i++){
            if(data[i][3] != null) {
                data[i][4] = blinds[Integer.parseInt(data[i][3])][1] + "";
            }
        }
        for(int i = 0; i < files.length; i++){
            for(int j = 0; j < files.length; j++){
                if(files[i].getName().compareTo(data[j][0]) == 0){
                    for(int k = 0; k < sub.length; k++){
                        if(data[j][1] != null) {
                            if (data[j][1].compareTo(sub[k]) == 0) {
                                try {
                                    String fileExt = files[i].getAbsolutePath().substring(files[i].getAbsolutePath().lastIndexOf('.'));
                                    Files.copy(files[i].toPath(),
                                            new File(subFolder[k].getAbsolutePath() + File.separator + data[j][4] + fileExt).toPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        try {
            File f = new File(out.getAbsolutePath() + File.separator + "Output.csv");
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write("OldName,Blinded Name,Blinded Folder,Treatment Name,\n");
            for(int y = 1; y <= files.length; y++) {

                    fileWriter.write(data[y-1][0] + ",");
                    fileWriter.write(data[y-1][4] + ",");
                    fileWriter.write(data[y-1][1] + ",");
                    fileWriter.write(data[y-1][2] + ",");

                    fileWriter.write("\n");
            }
            fileWriter.close();
            controller.openFile(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File fo = new File(out.getAbsolutePath() + File.separator + "Output_Only_treatment_and_number.csv");
            FileWriter fileWriter = new FileWriter(fo);
            fileWriter.write("Treatment,Blinded Name,\n");
            int old = -1;
            for(int y = 1; y <= files.length; y++) {
                try {
                    int t = Integer.parseInt(data[y - 1][4]);
                    if(old != t) {
                        old = t;
                        fileWriter.write(data[y - 1][2] + ",");
                        fileWriter.write(data[y - 1][4] + ",");
                        fileWriter.write("\n");
                    }
                }catch (NumberFormatException er){

                }

            }
            fileWriter.close();
            controller.openFile(fo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
