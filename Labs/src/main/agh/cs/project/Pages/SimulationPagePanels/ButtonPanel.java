package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    public final JButton stopButton;
    public ButtonPanel(int mapWidth){         //guzik start/stop
        this.setMaximumSize(new Dimension(mapWidth, 50));
        this.stopButton = new JButton("Resume Simulation");
        stopButton.setPreferredSize(new Dimension(200, 30));
        this.add(stopButton);

    }
}
