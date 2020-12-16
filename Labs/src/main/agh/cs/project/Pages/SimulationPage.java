package agh.cs.project.Pages;

import agh.cs.project.*;
import agh.cs.project.Pages.SimulationPagePanels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPage implements ActionListener {
    private final MapPanel map;
    private final StatsPanel stats;

    private final GrassField field;

    private final Color borderColor = new Color(131, 125, 74);
    private SimulationPage x;

    public SimulationPage(GrassField field, ProjectEngine engine){
        int minMapHeight = 400;
        int minMapWidth = 400;
        int statsWidth = 400;    //trzeba ustawić w formularzu

        this.field = field;
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

        this.stats = new StatsPanel(statsWidth, totalHeight);
        contentPane.add(stats);

//Początek prawej strony

        JPanel left = new JPanel();
        left.setBackground(borderColor);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        left.setMaximumSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        contentPane.add(left);

        this.map = new MapPanel(this, field, mapWidth, mapHeight, fieldSize);    //mapa
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

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.interrupt();
            }
        });

    }

//Metody

    public MapPanel getMap (){
        return this.map;
    }

    public StatsPanel getStats (){
        return this.stats;
    }

    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        Vector2d position = Vector2d.fromString(source.getName());
        System.out.println(field.objectAt(position));
    }
}





