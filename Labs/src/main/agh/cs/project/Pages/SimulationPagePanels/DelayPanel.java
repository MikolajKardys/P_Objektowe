package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;
import java.awt.*;

public class DelayPanel extends AbstractSimulationPagePanel {

//Fragment okienka obsługujący ustawianie wybranego przez użytkownika opóźnienia

    private final JTextField delayText;

    public DelayPanel(int minMapWidth, int mapHeight, int totalHeight){
        setMinimumSize(new Dimension(minMapWidth, -1));
        setMaximumSize(new Dimension(minMapWidth, totalHeight - mapHeight - 50));


        JLabel delayLabel = new JLabel("Current delay (in milliseconds): ");
        delayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        delayLabel.setBackground(Color.WHITE);
        delayLabel.setOpaque(true);
        delayLabel.setFont(new Font(delayLabel.getFont().getName(), Font.BOLD, 16));
        add(delayLabel);

        delayText = new JTextField("200");
        delayText.setAlignmentX(Component.CENTER_ALIGNMENT);
        delayText.setPreferredSize(new Dimension(60, 30));
        delayText.setHorizontalAlignment(0);
        delayText.setEnabled(true);
        add(delayText);
    }

    public JTextField getDelayText(){
        return delayText;
    }

    @Override
    public void enableElements(boolean enable) {
        delayText.setEnabled(enable);
    }
}
