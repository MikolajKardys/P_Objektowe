package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatsPanel extends JPanel implements IChangeObserver {

    private JPanel statsPanel;
    private JLabel day;
    private JLabel animalNumber;
    private JLabel jungleGrass;
    private JLabel savannaGrass;
    private JLabel avgEnergy;
    private JLabel avgLife;
    private JLabel avgChildren;
    private JLabel dominant;

    private int days = 0;

    private final ArrayList<Animal> Animals;
    private int deadNumber = 0;
    private int lifeLengthSum = 0;
    private int energySum;
    private int childrenNumber;

    private final GenomeQuantitySet quantitySet;

    public StatsPanel(int statsWidth) {
        this.setPreferredSize(new Dimension(statsWidth, -1));
        this.setBackground(new Color(187, 187, 187));
        this.add(statsPanel);

        this.quantitySet = new GenomeQuantitySet();

        Animals = new ArrayList<>();
    }

    public void allUpdate(GrassField field) {
        days++;
        this.day.setText(String.valueOf(days));

        this.animalNumber.setText(String.valueOf(Animals.size()));

        this.jungleGrass.setText(String.valueOf(field.jungleGrassNumber));

        this.savannaGrass.setText(String.valueOf(field.grassCount() - field.jungleGrassNumber));

        if (Animals.size() > 0){
            double average = (double) energySum / (double) Animals.size();          //
            average = Math.ceil(average * 100) / 100;
            this.avgEnergy.setText(String.valueOf(average));

            average = (double) childrenNumber / (double) Animals.size();             //
            average = Math.ceil(average * 100) / 100;
            this.avgChildren.setText(String.valueOf(average));
        }
        else{
            this.avgEnergy.setText("0");
            this.avgChildren.setText("0");
        }

        if (deadNumber > 0){
            double average = (double) lifeLengthSum / (double) deadNumber;
            average = Math.ceil(average * 100) / 100;
            this.avgLife.setText(String.valueOf(average));
        }

        this.dominant.setText(this.quantitySet.getDominant());

    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) {
    }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            Animals.add(thisAnimal);
            childrenNumber += thisAnimal.parents.size();
            energySum += thisAnimal.getEnergy();
            this.quantitySet.add(((Animal) element).getGenome());
        }
    }

    @Override
    public void removedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            Animals.remove(thisAnimal);
            lifeLengthSum += thisAnimal.lifeLength;
            deadNumber++;
            childrenNumber -= thisAnimal.parents.size();
            childrenNumber -= thisAnimal.children.size();
            energySum -= thisAnimal.getEnergy();

        }
    }

    @Override
    public void energyChanged(Animal animal, int change){
        energySum += change;
    }
}
