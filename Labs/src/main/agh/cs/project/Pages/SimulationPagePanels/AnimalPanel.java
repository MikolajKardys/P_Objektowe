package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Animal;

import javax.swing.*;
import java.awt.*;

public class AnimalPanel extends JPanel{
    private JPanel animalPanel;
    private JPanel statsPanel;
    private JLabel coordinates;

    public AnimalPanel(int statsWidth){
        this.setPreferredSize(new Dimension(statsWidth, -1));
        this.setBackground(new Color(187, 187, 187));

        this.add(animalPanel);
    }


    public void selectedAnimal(Animal animal){
        this.coordinates.setText(animal.toString());
    }

}
