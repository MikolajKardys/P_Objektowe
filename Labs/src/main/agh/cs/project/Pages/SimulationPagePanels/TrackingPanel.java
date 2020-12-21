package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Sources.AbstractWorldMapElement;
import agh.cs.project.Sources.Animal;
import agh.cs.project.Sources.IChangeObserver;
import agh.cs.project.Sources.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrackingPanel extends AbstractSimulationPagePanel implements IChangeObserver {

//Część okienka pokazująca wymagane statystyki dla obecnie śledzonego zwierzęcia

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

//Panel przechowujący

    public TrackingPanel(int statsWidth, JButton stopButton){
        setPreferredSize(new Dimension(statsWidth, -1));
        setBackground(new Color(187, 187, 187));
        setAlignmentY(TOP_ALIGNMENT);

        curAnimal = null;
        curChildren = 0;
        curDesc = new ArrayList<>();

        clearSelection.setEnabled(false);

        this.stopButton = stopButton;

        add(statsPanel);

        clearSelection.addActionListener(e -> {
            String [] options = {"Yes", "No"};
            int x;
            x = JOptionPane.showOptionDialog(this,
                    "Are you sure you want to reset tracking values? Current values will be lost.",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (x == 0){
                if (curAnimal != null) clearSelection(false);
                clearBoard();
                clearSelection.setEnabled(false);
            }
        });
    }

    public void clearBoard(){
        watchedFor = 0;

        isAlive.setText("Now tracking");
        start.setText("0");
        genome.setText("None");
        duration.setText("0");
        descendants.setText("0");
        children.setText("0");
    }

    public void clearSelection(boolean removing){
        if (!removing){
            curAnimal.removeObserver(this);
        }

        curAnimal.select(false);
        curAnimal = null;

        watchedFor = 0;

        curChildren = 0;

        for (Animal child : curDesc){
            child.removeObserver(this);
        }
        curDesc.clear();
    }

    public void selectedAnimal(Animal animal, int startDay){
        int x;
        if (clearSelection.isEnabled()){
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

        if (curAnimal != null) clearSelection(false);
        clearBoard();

        if (curAnimal != null){
            clearSelection(false);
        }

        curAnimal = animal;
        curAnimal.select(true);
        curAnimal.addObserver(this);

        clearBoard();

        start.setText(String.valueOf(startDay));
        genome.setText(animal.getGenome().toString());

        clearSelection.setEnabled(true);
    }

    @Override
    public void changedPosition(Animal element, Vector2d oldPosition) {
        if (element.isSelected()) {
            watchedFor++;
            duration.setText(String.valueOf(watchedFor));
        }
    }

    @Override
    public void addedElement(AbstractWorldMapElement element) {
        Animal newAnimal = (Animal) element;

        for (Animal parent : newAnimal.getParents()){
            if (parent.isSelected()){
                curChildren++;
                children.setText(String.valueOf(curChildren));
            }
        }

        curDesc.add(newAnimal);
        descendants.setText(String.valueOf(curDesc.size()));
    }

    @Override
    public void removedElement(AbstractWorldMapElement element) {
        Animal animal = (Animal) element;
        if (animal.isSelected()){
            isAlive.setText("Now tracking(Dead)");
            watchedFor++;
            duration.setText(String.valueOf(watchedFor));

            clearSelection(true);


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
                    curChildren--;
                    children.setText(String.valueOf(curChildren));
                }
            }

            curDesc.remove(element);
            descendants.setText(String.valueOf(curDesc.size()));
        }
    }

    @Override
    public void energyChanged(Animal animal, int change) { }

    @Override
    public void enableElements(boolean enable) {
        if (!enable) clearSelection.setEnabled(false);
        else{
            if (!genome.getText().equals("None"))clearSelection.setEnabled(true);
        }
    }
}
