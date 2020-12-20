package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;
import java.awt.*;

public class StartButtonPanel extends JPanel {
    private final JButton stopButton;
    private final JButton statsButton;

    public StartButtonPanel(){         //guzik start/stop
        this.stopButton = new JButton("Resume Simulation");
        stopButton.setPreferredSize(new Dimension(200, 30));
        stopButton.setMinimumSize(new Dimension(200, 30));
        this.add(stopButton);

        this.statsButton = new JButton("Load statistics to file");
        statsButton.setPreferredSize(new Dimension(200, 30));
        statsButton.setMinimumSize(new Dimension(200, 30));
        this.add(statsButton);
    }

    public JButton getStopButton(){
        return stopButton;
    }

    public JButton getStatsButton(){
        return statsButton;
    }
}
