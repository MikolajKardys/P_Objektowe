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
    private JEditorPane editorPane1;

    private int days = 0;

    private int allAnimals;
    private int livingNumber;

    public StatsPanel(int statsWidth, int totalHeight){
        this.setPreferredSize(new Dimension(statsWidth, totalHeight));
        this.setBackground(new Color(187,187,187));

        this.add(statsPanel);
    }

    public void allUpdate(GrassField field){
        days++;
        this.day.setText(String.valueOf(days));

        this.animalNumber.setText(String.valueOf(field.animalNumber));

        this.jungleGrass.setText(String.valueOf(field.jungleGrassNumber));

        this.savannaGrass.setText(String.valueOf(field.grassCount() - field.jungleGrassNumber));
    }

}
