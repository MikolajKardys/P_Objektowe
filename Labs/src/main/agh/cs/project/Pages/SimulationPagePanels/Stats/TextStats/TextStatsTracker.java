package agh.cs.project.Pages.SimulationPagePanels.Stats.TextStats;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextStatsTracker extends JComponent{

    private long days;
    private double animalNumberTotal;
    private double jungleGrassTotal;
    private double steppeGrassTotal;
    private double avgEnergyTotal;
    private double avgLifeTotal;
    private double avgChildrenTotal;
    private String dominant;

    public TextStatsTracker() {
        days = 0;
        animalNumberTotal = 0;
        jungleGrassTotal = 0;
        steppeGrassTotal = 0;
        avgEnergyTotal = 0;
        avgLifeTotal = 0;
        avgChildrenTotal = 0;
        dominant = "None";
    }

    public void update(int days, double [] updateNumbers){
        this.days = days;
        animalNumberTotal += updateNumbers[0];
        jungleGrassTotal += updateNumbers[1];
        steppeGrassTotal += updateNumbers[2];
        avgEnergyTotal += updateNumbers[3];
        avgLifeTotal += updateNumbers[4];
        avgChildrenTotal += updateNumbers[5];
    }


    public void writeToFile(String fileName){
        if (fileName == null){
            return;
        }
        try {
            fileName = fileName.replace(" ","");
            if (fileName.isEmpty()){
                throw new IOException();
            }

            String outputPath = System.getProperty("user.dir") + "/src/main/agh/cs/project/StatOutputs/" + fileName + ".txt";

            FileWriter fileWriter = new FileWriter(outputPath);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            float animalNumberAverage = (float)animalNumberTotal / days;
            float jungleGrassAverage = (float)jungleGrassTotal / days;
            float steppeGrassAverage = (float)steppeGrassTotal / days;
            float avgEnergyAverage = (float)avgEnergyTotal / days;
            float avgLifeAverage = (float)avgLifeTotal / days;
            float avgChildrenAverage = (float)avgChildrenTotal / days;

            printWriter.print("-------------------------Average Statistics-------------------------\n");
            printWriter.print("Day:                                                            " + days + "\n");
            printWriter.print("Average of animal number:                                       " + animalNumberAverage + "\n");
            printWriter.print("Average of grass number in jungle:                              " + jungleGrassAverage + "\n");
            printWriter.print("Average of grass number on steppe:                              " + steppeGrassAverage + "\n");
            printWriter.print("Average of average energy for living animal:                    " + avgEnergyAverage + "\n");
            printWriter.print("Average of average life length for dead animal:                 " + avgLifeAverage + "\n");
            printWriter.print("Average of average number of children per living animal:        " + avgChildrenAverage + "\n");

            printWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Invalid file name!!!","Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
