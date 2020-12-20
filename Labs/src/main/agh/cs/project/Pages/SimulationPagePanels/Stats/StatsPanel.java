package agh.cs.project.Pages.SimulationPagePanels.Stats;

import agh.cs.project.Pages.SimulationPagePanels.AbstractSimulationPagePanel;
import agh.cs.project.Pages.SimulationPagePanels.Stats.TextStats.TextStatsTracker;
import agh.cs.project.Sources.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatsPanel extends AbstractSimulationPagePanel implements IChangeObserver {

    private JPanel statsPanel;
    private JLabel day;
    private JLabel animalNumber;
    private JLabel jungleGrass;
    private JLabel steppeGrass;
    private JLabel avgEnergy;
    private JLabel avgLife;
    private JLabel avgChildren;
    private JLabel dominant;
    private JButton highlight;

    private int days = 0;

    private final ArrayList<Animal> Animals;
    private int deadNumber = 0;
    private int lifeLengthSum = 0;
    private int energySum;
    private int childrenNumber;

    private final GenomeQuantitySet quantitySet;

    private final TextStatsTracker textTracker;

    public boolean highlighted;

    public StatsPanel(int statsWidth) {
        this.setPreferredSize(new Dimension(statsWidth, 100));
        this.setMinimumSize(new Dimension(statsWidth, 100));
        this.setBackground(new Color(187, 187, 187));
        this.add(statsPanel);

        this.quantitySet = new GenomeQuantitySet();

        Animals = new ArrayList<>();

        this.highlighted = false;

        this.textTracker = new TextStatsTracker();
        this.textTracker.update(days, new double [] {0, 0, 0, 0, 0, 0}, null);

        this.highlight.addActionListener(e -> {
            Genome dominant = quantitySet.getDominant();
            if (dominant != null){
                if (highlight.getText().equals("Highlight animals with this genome")){
                    for (Animal animal : Animals){
                        if (animal.getGenome().compare(dominant) == 0){
                            animal.highLight();
                        }
                    }
                    highlight.setText("Remove highlight");
                    this.highlighted = true;
                }
                else {
                    highlight.setText("Highlight animals with this genome");
                    for (Animal animal : Animals){
                        if (animal.getGenome().compare(dominant) == 0){
                            animal.removeHighLight();
                        }
                    }
                    this.highlighted = false;
                }

            }
        });
    }

    public void allUpdate(GrassField field) {
        double [] update = {0, 0, 0, 0, 0, 0};

        this.days++;
        this.day.setText(String.valueOf(days));

        this.animalNumber.setText(String.valueOf(Animals.size()));
        update[0] = Animals.size();

        this.jungleGrass.setText(String.valueOf(field.jungleGrassNumber));
        update[1] = field.jungleGrassNumber;

        this.steppeGrass.setText(String.valueOf(field.grassCount() - field.jungleGrassNumber));
        update[2] = field.grassCount() - field.jungleGrassNumber;

        if (Animals.size() > 0){
            double average = (double) energySum / (double) Animals.size();
            average = Math.ceil(average * 100) / 100;
            this.avgEnergy.setText(String.valueOf(average));
            update[3] = average;

            average = (double) childrenNumber / (double) Animals.size();
            average = Math.ceil(average * 100) / 100;
            this.avgChildren.setText(String.valueOf(average));
            update[5] = average;
        }
        else{
            this.avgEnergy.setText("0");
            this.avgChildren.setText("0");
        }

        if (deadNumber > 0){
            double average = (double) lifeLengthSum / (double) deadNumber;
            average = Math.ceil(average * 100) / 100;
            this.avgLife.setText(String.valueOf(average));
            update[4] = average;
        }

        Genome dominantGenome = this.quantitySet.getDominant();
        if (dominantGenome != null){
            this.dominant.setText(dominantGenome.toLongString());
        }
        else {
            this.dominant.setText("None");
        }

        this.textTracker.update(days, update, dominantGenome);
    }

    public int getDay(){
        return days;
    }

    public void loadFile(){
        String fileName =  JOptionPane.showInputDialog("Enter desired name of output file.");
        this.textTracker.writeToFile(fileName);
    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) { }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            this.Animals.add(thisAnimal);
            this.childrenNumber += thisAnimal.parents.size();
            this.energySum += thisAnimal.getEnergy();
            this.quantitySet.add(((Animal) element).getGenome());
        }
    }

    @Override
    public void removedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            this.Animals.remove(thisAnimal);
            this.lifeLengthSum += thisAnimal.lifeLength + 1;
            this.deadNumber++;
            this.childrenNumber -= thisAnimal.parents.size();
            this.childrenNumber -= thisAnimal.children.size();
            this.energySum -= thisAnimal.getEnergy();
            this.quantitySet.remove(((Animal) element).getGenome());
        }
    }

    @Override
    public void energyChanged(Animal animal, int change){
        this.energySum += change;
    }


    @Override
    public void enableElements(boolean enable) {
        if (!this.dominant.getText().equals("None")) this.highlight.setEnabled(enable);
        else {
            this.highlight.setEnabled(false);
        }
    }
}
