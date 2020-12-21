package agh.cs.project.Pages.SimulationPagePanels.Stats;

import agh.cs.project.Pages.SimulationPagePanels.AbstractSimulationPagePanel;
import agh.cs.project.Pages.SimulationPagePanels.Stats.TextStats.TextStatsTracker;
import agh.cs.project.Sources.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatsPanel extends AbstractSimulationPagePanel implements IChangeObserver {

//Fragment okienka obsługujący wyświetlanie statystyk; ich liczenie nie jest trudne, więc robimy je już tutaj

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

    private boolean highlighted;

    public StatsPanel(int statsWidth) {
        setPreferredSize(new Dimension(statsWidth, 100));
        setMinimumSize(new Dimension(statsWidth, 100));
        setBackground(new Color(187, 187, 187));
        add(statsPanel);

        quantitySet = new GenomeQuantitySet();

        Animals = new ArrayList<>();

        highlighted = false;

        textTracker = new TextStatsTracker();
        textTracker.update(days, new double [] {0, 0, 0, 0, 0, 0}, null);

        highlight.addActionListener(e -> {
            Genome dominant = quantitySet.getDominant();
            if (dominant != null){
                if (highlight.getText().equals("Highlight animals with this genome")){
                    for (Animal animal : Animals){
                        if (animal.getGenome().compare(dominant) == 0){
                            animal.highLight();
                        }
                    }
                    highlight.setText("Remove highlight");
                    highlighted = true;
                }
                else {
                    highlight.setText("Highlight animals with this genome");
                    for (Animal animal : Animals){
                        if (animal.getGenome().compare(dominant) == 0){
                            animal.removeHighLight();
                        }
                    }
                    highlighted = false;
                }

            }
        });
    }

    public void allUpdate(GrassField field) {
        double [] update = {0, 0, 0, 0, 0, 0};

        days++;
        day.setText(String.valueOf(days));

        animalNumber.setText(String.valueOf(Animals.size()));
        update[0] = Animals.size();

        jungleGrass.setText(String.valueOf(field.getJungleGrassNumber()));
        update[1] = field.getJungleGrassNumber();

        steppeGrass.setText(String.valueOf(field.grassCount() - field.getJungleGrassNumber()));
        update[2] = field.grassCount() - field.getJungleGrassNumber();

        if (Animals.size() > 0){
            double average = (double) energySum / (double) Animals.size();
            average = Math.ceil(average * 100) / 100;
            avgEnergy.setText(String.valueOf(average));
            update[3] = average;

            average = (double) childrenNumber / (double) Animals.size();
            average = Math.ceil(average * 100) / 100;
            avgChildren.setText(String.valueOf(average));
            update[5] = average;
        }
        else{
            avgEnergy.setText("0");
            avgChildren.setText("0");
        }

        if (deadNumber > 0){
            double average = (double) lifeLengthSum / (double) deadNumber;
            average = Math.ceil(average * 100) / 100;
            avgLife.setText(String.valueOf(average));
            update[4] = average;
        }

        Genome dominantGenome = quantitySet.getDominant();
        if (dominantGenome != null){
            dominant.setText(dominantGenome.toString());
        }
        else {
            dominant.setText("None");
        }

        textTracker.update(days, update, dominantGenome);
    }

    public int getDay(){
        return days;
    }

    public void loadFile(){
        String fileName =  JOptionPane.showInputDialog("Enter desired name of output file.");
        textTracker.writeToFile(fileName);
    }

    public boolean isHighlighted(){
        return highlighted;
    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) { }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            Animals.add(thisAnimal);
            childrenNumber += thisAnimal.getParentNumber();
            energySum += thisAnimal.getEnergy();
            quantitySet.add(((Animal) element).getGenome());
        }
    }

    @Override
    public void removedElement(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            Animal thisAnimal = (Animal) element;
            Animals.remove(thisAnimal);
            lifeLengthSum += thisAnimal.getLifeLength() + 1;
            deadNumber++;
            childrenNumber -= thisAnimal.getParentNumber();
            childrenNumber -= thisAnimal.getChildNumber();
            energySum -= thisAnimal.getEnergy();
            quantitySet.remove(((Animal) element).getGenome());
        }
    }

    @Override
    public void energyChanged(Animal animal, int change){
        energySum += change;
    }


    @Override
    public void enableElements(boolean enable) {
        if (!dominant.getText().equals("None")) highlight.setEnabled(enable);
        else {
            highlight.setEnabled(false);
        }
    }
}
