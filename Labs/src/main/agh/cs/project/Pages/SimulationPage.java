package agh.cs.project.Pages;

import agh.cs.project.*;
import agh.cs.project.Pages.SimulationPagePanels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPage {
    private final MapPanel map;
    private final StatsPanel stats;

    private final Color borderColor = new Color(131, 125, 74);

    public SimulationPage(GrassField field, ProjectEngine engine) {
        int minMapHeight = 400;
        int minMapWidth = 400;
        int statsWidth = 400;    //trzeba ustawić w formularzu

        int fieldSize = Math.min(600 / field.width, 1200 / field.height);
        fieldSize = Math.min(120, fieldSize);
        fieldSize = Math.max(fieldSize, 15);
        System.out.println(field.height + " " + field.width + " " + 50);

        int mapHeight = fieldSize * field.width;
        int mapWidth = (int) (mapHeight * ((float) field.height / (float) field.width));

        int totalHeight = Math.max(mapHeight, minMapHeight) + 100;
        int totalWidth = Math.max(mapWidth, minMapWidth) + statsWidth;

        JFrame f = new JFrame("Simulation Map");
        f.setSize(totalWidth, totalHeight);
        f.setFocusableWindowState(true);
        f.setResizable(false);

        JPanel contentPane = new JPanel();
        f.setContentPane(contentPane);
        contentPane.setBackground(borderColor);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

//Początek lewej strony

        JPanel left = new JPanel();
        left.setBackground(borderColor);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        left.setMaximumSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        contentPane.add(left);

        this.map = new MapPanel(field, mapWidth, mapHeight, fieldSize);    //mapa
        left.add(this.map);

        DelayPanel delayPanel = new DelayPanel(mapWidth, mapHeight, totalHeight);     //panel opóźnienia
        delayPanel.setBackground(borderColor);
        left.add(delayPanel);

        ButtonPanel buttonPanel = new ButtonPanel(mapWidth);
        buttonPanel.setBackground(borderColor);
        left.add(buttonPanel);

        buttonPanel.stopButton.addActionListener(e -> {
            if (buttonPanel.stopButton.getText().equals("Stop Simulation")) {
                buttonPanel.stopButton.setText("Resume Simulation");
                delayPanel.delayText.setEnabled(true);
                engine.pause();
            } else {
                try {
                    int delay = Integer.parseInt(delayPanel.delayText.getText());
                    if (delay >= 0) {
                        buttonPanel.stopButton.setText("Stop Simulation");
                        engine.unpause(delay);
                        delayPanel.delayText.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(f, "Delay value must be equal or above 0");
                    }
                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(f, "Invalid delay value!!!");
                }
            }

        });

//Koniec lewej strony, początek prawej

        this.stats = new StatsPanel(statsWidth, totalHeight);
        contentPane.add(stats);

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.interrupt();
            }
        });

    }

    public MapPanel getMap (){
        return this.map;
    }

    public StatsPanel getStats (){
        return this.stats;
    }
}





