package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.GrassField;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel{
    private JPanel statsPanel;
    private JLabel day;
    private JLabel animalNumber;
    private JLabel jungleGrass;
    private JLabel savannaGrass;
    private JLabel avgEnergy;
    private JLabel avgLife;
    private JLabel avgChildren;

    private int days = 1;

    private int allAnimals;
    private int livingNumber;

    public StatsPanel(int statsWidth, int totalHeight){
        this.setPreferredSize(new Dimension(statsWidth, totalHeight));
        this.setBackground(new Color(187,187,187));

        this.add(statsPanel);
    }

    public void addedAnimal(){
        allAnimals++;
        livingNumber++;

        this.animalNumber.setText(String.valueOf(livingNumber));
    }

    public void killedAnimal(){
        livingNumber--;

        this.animalNumber.setText(String.valueOf(livingNumber));
    }

    public void mostUpdate(GrassField field){
        days++;
        this.day.setText(String.valueOf(days));

        this.jungleGrass.setText(String.valueOf(field.jungleGrassNumber));
        this.savannaGrass.setText(String.valueOf(field.GrassMap.size() - field.jungleGrassNumber));
    }

}
