package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Pages.SimulationPagePanels.Stats.StatsPanel;
import agh.cs.project.Sources.Animal;

import javax.swing.*;
import java.awt.*;

public class SelectPanel extends AbstractSimulationPagePanel {

//Fragment okienka obsługujący obecnie zaznaczone zwierzę

    private JPanel selectPanel;
    private JLabel genome;
    private JButton track;
    private JButton clear;

    private Animal curAnimal;

    public SelectPanel(int statsWidth, TrackingPanel trackingPanel, StatsPanel stats) {
        setMaximumSize(new Dimension(statsWidth, 200));
        setAlignmentY(TOP_ALIGNMENT);
        setBackground(new Color(187, 187, 187));
        add(selectPanel);

        genome.setOpaque(true);

        track.setEnabled(false);
        clear.setEnabled(false);

        clear.addActionListener(e -> clearSelection());

        track.addActionListener(e -> trackingPanel.selectedAnimal(curAnimal, stats.getDay()));
    }

    public void clearSelection() {
        curAnimal = null;
        genome.setText("None");
        genome.setBackground(new Color(187, 187, 187));
        track.setEnabled(false);
        clear.setEnabled(false);
    }

    public void selectedAnimal(Animal animal) {
        curAnimal = animal;
        genome.setText(animal.getGenome().toString());
        genome.setBackground(animal.getHealthColor());
        track.setEnabled(true);
        clear.setEnabled(true);
    }

    @Override
    public void enableElements(boolean enable) {
        if (!enable){
            clearSelection();
        }
    }

}
