package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Sources.AbstractWorldMapElement;
import agh.cs.project.Sources.Animal;
import agh.cs.project.Sources.IChangeObserver;
import agh.cs.project.Sources.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrackingPanel extends AbstractSimulationPagePanel implements IChangeObserver {
    private JPanel animalPanel;
    private JPanel statsPanel;
    private JLabel descendants;
    private JLabel genome;
    private JLabel duration;
    private JLabel children;
    private JButton clearSelection;
    private JLabel start;
    private JLabel isAlive;

    private Animal curAnimal;
    private int watchedFor;
    private int curChildren;
    private final ArrayList<Animal> curDesc;

    private final JButton stopButton;

    public TrackingPanel(int statsWidth, JButton stopButton){
        this.setPreferredSize(new Dimension(statsWidth, -1));
        this.setBackground(new Color(187, 187, 187));
        this.setAlignmentY(TOP_ALIGNMENT);

        this.curAnimal = null;
        this.curChildren = 0;
        this.curDesc = new ArrayList<>();

        this.clearSelection.setEnabled(false);

        this.stopButton = stopButton;

        this.add(statsPanel);

        this.clearSelection.addActionListener(e -> {
            String [] options = {"Yes", "No"};
            int x;
            x = JOptionPane.showOptionDialog(this,
                    "Are you sure you want to reset tracking values? Current values will be lost.",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (x == 0){
                if (this.curAnimal != null) this.clearSelection(false);
                this.clearBoard();
                this.clearSelection.setEnabled(false);
            }
        });
    }

    public void clearBoard(){
        this.watchedFor = 0;

        this.isAlive.setText("Now tracking");
        this.start.setText("0");
        this.genome.setText("None");
        this.duration.setText("0");
        this.descendants.setText("0");
        this.children.setText("0");
    }

    public void clearSelection(boolean removing){
        if (!removing){
            this.curAnimal.removeObserver(this);
        }

        this.curAnimal.select(false);
        this.curAnimal = null;

        this.watchedFor = 0;

        this.curChildren = 0;

        for (Animal child : curDesc){
            child.removeObserver(this);
        }
        this.curDesc.clear();
    }

    public void selectedAnimal(Animal animal, int start){
        int x;
        if (this.clearSelection.isEnabled()){
            String [] options = {"Yes", "No"};
            x = JOptionPane.showOptionDialog(this,
                    "Are you sure you want to overwrite current tracking values? Current values will be lost.",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
        }
        else {
            x = 0;
        }
        if (x == 1) return;

        if (this.curAnimal != null) this.clearSelection(false);
        this.clearBoard();

        if (curAnimal != null){
            this.clearSelection(false);
        }

        this.curAnimal = animal;
        this.curAnimal.select(true);
        this.curAnimal.addObserver(this);

        this.clearBoard();

        this.start.setText(String.valueOf(start));
        this.genome.setText(animal.getGenome().toLongString());

        this.clearSelection.setEnabled(true);
    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) {
        if (element.isSelected()) {
            this.watchedFor++;
            this.duration.setText(String.valueOf(this.watchedFor));
        }
    }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        Animal newAnimal = (Animal) element;

        for (Animal parent : newAnimal.getParents()){
            if (parent.isSelected()){
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
        if (animal.isSelected()){
            this.isAlive.setText("Now tracking(Dead)");
            this.watchedFor++;
            this.duration.setText(String.valueOf(this.watchedFor));

            this.clearSelection(true);


            String [] options = {"Yes", "No"};
            int x = JOptionPane.showOptionDialog(this,
                    "The animal you were tracking has died. Do you wish to stop the simulation after this day has ended?",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (x == 0){
                stopButton.doClick();
            }

        }
        else {
            for (Animal parent : animal.getParents()){
                if (parent.isSelected()){
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

    @Override
    public void enableElements(boolean enable) {
        if (!enable) this.clearSelection.setEnabled(false);
        else{
            if (!this.genome.getText().equals("None"))this.clearSelection.setEnabled(true);
        }
    }
}
