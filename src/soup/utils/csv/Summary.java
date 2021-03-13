package soup.utils.csv;

import com.alee.managers.notification.NotificationManager;
import soup.main.center.CenterController;
import soup.main.center.panels.CSVPanel;
import soup.main.center.panels.TableModel;
import soup.utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Summary {

    public static void createSummary(File current, String outFile){
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                String[] command = { "cmd", "/c","pip install numpy pandas "," & python C:\\Users\\thoma\\IdeaProjects\\LabSoup\\python\\summary.py ",
                        current.getAbsolutePath()};

                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(FileUtils.getSummaryFolder());

                pb.command(command);

                try {
                    Process p = pb.start();

                    try {
                        p.waitFor();
                        File[] files = FileUtils.getSummaryFolder().listFiles();
                        TableModel tableModel = new TableModel();
                        int offset = 0;
                        Scanner scanner;
                        for(int i = 0; i < files.length;i++){
                            scanner = new Scanner(files[i]);
                            String s = "";
                            while (scanner.hasNextLine()){
                                s += scanner.nextLine() + "\n";
                            }
                            String[] c = s.split("\n");
                            int greatest = 0;
                            for(int j = 0; j < c.length; j++){
                                String[] d = c[j].split(",");
                                for(int k = 0; k < d.length; k++){
                                    tableModel.setValueAt(d[k],j,k + offset);
                                    if(k > greatest){
                                        greatest = k;
                                    }
                                }
                            }
                            offset += greatest + 2;
                        }
                        File file = new File(outFile + ".csv");
                        CSVPanel.saveFile(file,tableModel);
                        CenterController.getCenterController().openFile(file);

                        System.out.print(p.exitValue());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
            }
        };
        swingWorker.execute();
    }


}
