package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Sources.GrassField;
import agh.cs.project.Pages.SimulationPage;

import javax.swing.*;
import java.awt.*;

public class PositionPanel extends JPanel implements IMapViz{

//Zastępczy panel, jeśli mapa jest za duża żeby ją wyświetlić

    private JPanel positionPanel;
    private JTextField xField;
    private JButton selectButton;
    private JTextField yField;
    private JTextPane highlighted;

    private String highlightedString;

    private final Color steppeColor = new Color(182, 227, 83);

    public PositionPanel(SimulationPage mainPage, GrassField field, int mapHeight){
        setPreferredSize(new Dimension(-1, mapHeight));
        setBackground(steppeColor);
        add(positionPanel);

        highlightedString = "";
        highlighted.setText(highlightedString);

        selectButton.addActionListener(e -> {
            try{
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                if (x < 0 || y < 0 || x >= field.width || y >= field.height){
                    String permittedRange = "x in (0, " + (field.width - 1) + "), y in (0, " + (field.height - 1) + ")";
                    JOptionPane.showMessageDialog(this, "Coordinates outside of permitted range: " + permittedRange + "!!!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    selectButton.setName(x + " " + y);
                    mainPage.actionPerformed(e);
                }

            }
            catch (NumberFormatException ignored) {
                JOptionPane.showMessageDialog(this, "Both coordinates must be integers!!!",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void highLight(int indX, int indY) {
        highlightedString += "(" + indX + "," + indY + ")   ";
        highlighted.setText(highlightedString);
    }

    @Override
    public void removeHighLight(int indX, int indY) {
        if (!highlightedString.equals("")){
            highlightedString = "";
            highlighted.setText(highlightedString);
        }

    }
}
