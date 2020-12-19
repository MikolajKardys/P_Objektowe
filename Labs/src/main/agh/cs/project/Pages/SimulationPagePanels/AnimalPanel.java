package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.AbstractWorldMapElement;
import agh.cs.project.Animal;
import agh.cs.project.IChangeObserver;
import agh.cs.project.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnimalPanel extends JPanel implements IChangeObserver {
    private JPanel animalPanel;
    private JPanel statsPanel;
    private JLabel descendants;
    private JLabel genome;
    private JLabel duration;
    private JLabel children;

    private Animal curAnimal;
    private int watchedFor;
    private int curChildren;
    private ArrayList<Animal> curDesc;

    private final JButton stopButton;

    public AnimalPanel(int statsWidth, JButton stopButton){
        this.setPreferredSize(new Dimension(statsWidth, -1));
        this.setBackground(new Color(187, 187, 187));

        this.curAnimal = null;
        this.curChildren = 0;
        this.curDesc = new ArrayList<>();

        this.stopButton = stopButton;

        this.add(statsPanel);
    }

    public void clearBoard(){
        this.watchedFor = 0;

        this.genome.setText("None");
        this.duration.setText("0");
        this.descendants.setText("0");
        this.children.setText("0");
    }

    public void clearSelection(boolean removing){
        if (!removing){
            this.curAnimal.removeObserver(this);
        }

        this.curAnimal.isSelected = false;
        this.curAnimal = null;

        this.watchedFor = 0;

        this.curChildren = 0;

        for (Animal child : curDesc){
            child.removeObserver(this);
        }
        this.curDesc.clear();
    }

    public void selectedAnimal(Animal animal){
        if (curAnimal != null){
            this.clearSelection(false);
        }

        this.curAnimal = animal;
        this.curAnimal.isSelected = true;
        this.curAnimal.addObserver(this);

        this.clearBoard();

        this.genome.setText(animal.getGenome().toLongString());
    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) {
        if (element.isSelected) {
            this.watchedFor++;
            this.duration.setText(String.valueOf(this.watchedFor));
        }
    }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        Animal newAnimal = (Animal) element;

        for (Animal parent : newAnimal.parents){
            if (parent.isSelected){
                this.curChildren++;
                this.children.setText(String.valueOf(curChildren));
            }
        }

        this.curDesc.add(newAnimal);
        this.descendants.setText(String.valueOf(curDesc.size()));
    }

    @Override
    public void removedElement(AbstractWorldMapElement element) {
        Animal animal = (Animal) element;
        if (animal.isSelected){
            this.stopButton.doClick();

            this.watchedFor++;
            this.duration.setText(String.valueOf(this.watchedFor));

            this.clearSelection(true);

            //JOptionPane.showMessageDialog(this, "Selected animal has died.");
        }
        else {
            for (Animal parent : animal.parents){
                if (parent.isSelected){
                    this.curChildren--;
                    this.children.setText(String.valueOf(curChildren));
                }
            }

            this.curDesc.remove(element);
            this.descendants.setText(String.valueOf(curDesc.size()));
        }
    }

    @Override
    public void energyChanged(Animal animal, int change) { }

}
