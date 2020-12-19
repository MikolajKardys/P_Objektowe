package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;
import java.awt.*;

public class DelayPanel extends JPanel {
    public final JTextField delayText;

    public DelayPanel(int minMapWidth, int mapHeight, int totalHeight){
        this.setMinimumSize(new Dimension(minMapWidth, -1));
        this.setMaximumSize(new Dimension(minMapWidth, totalHeight - mapHeight - 50));


        JLabel delayLabel = new JLabel("Current delay (in milliseconds): ");
        delayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        delayLabel.setBackground(Color.WHITE);
        delayLabel.setOpaque(true);
        delayLabel.setFont(new Font(delayLabel.getFont().getName(), Font.BOLD, 16));
        this.add(delayLabel);

        this.delayText = new JTextField("200");
        this.delayText.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.delayText.setPreferredSize(new Dimension(60, 30));
        this.delayText.setHorizontalAlignment(0);
        this.delayText.setEnabled(true);
        this.add(this.delayText);
    }
}
