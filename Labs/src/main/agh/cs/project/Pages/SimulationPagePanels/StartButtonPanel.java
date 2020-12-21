package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;
import java.awt.*;

public class StartButtonPanel extends JPanel {

//Panel zawierający guzik start/stop symulacji ORAZ guzik wczytujący statystyki do pliku

    private final JButton stopButton;
    private final JButton statsButton;

    public StartButtonPanel(){         //guzik start/stop
        stopButton = new JButton("Resume Simulation");
        stopButton.setPreferredSize(new Dimension(200, 30));
        stopButton.setMinimumSize(new Dimension(200, 30));
        add(stopButton);

        statsButton = new JButton("Load statistics to file");
        statsButton.setPreferredSize(new Dimension(200, 30));
        statsButton.setMinimumSize(new Dimension(200, 30));
        add(statsButton);
    }

    public JButton getStopButton(){
        return stopButton;
    }

    public JButton getStatsButton(){
        return statsButton;
    }
}
