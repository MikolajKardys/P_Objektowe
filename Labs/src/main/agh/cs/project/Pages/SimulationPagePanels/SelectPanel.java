package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Animal;

import javax.swing.*;
import java.awt.*;

public class SelectPanel extends AbstractSimulationPagePanel {
    private JPanel selectPanel;
    private JLabel genome;
    private JButton track;
    private JButton clear;

    private Animal curAnimal;

    public SelectPanel(int statsWidth, TrackingPanel trackingPanel, StatsPanel stats) {
        this.setMaximumSize(new Dimension(statsWidth, 200));
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setBackground(new Color(187, 187, 187));
        this.add(selectPanel);

        this.track.setEnabled(false);
        this.clear.setEnabled(false);

        this.clear.addActionListener(e -> this.clearSelection());

        this.track.addActionListener(e -> trackingPanel.selectedAnimal(curAnimal, stats.getDay()));
    }

    public void clearSelection() {
        this.curAnimal = null;
        this.genome.setText("None");
        this.track.setEnabled(false);
        this.clear.setEnabled(false);
    }

    public void selectedAnimal(Animal animal) {
        this.curAnimal = animal;
        this.genome.setText(animal.getGenome().toLongString());
        this.track.setEnabled(true);
        this.clear.setEnabled(true);
    }

    @Override
    public void enableElements(boolean enable) {
        if (!enable){
            this.genome.setText("None");
            this.clear.setEnabled(false);
            this.track.setEnabled(false);
        }
    }

}
