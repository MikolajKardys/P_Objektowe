package agh.cs.project.Pages;

import agh.cs.project.InputFile.Parameters;
import agh.cs.project.Sources.ProjectEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage implements ActionListener {

//Okienko menu głownego

    private final JFrame frame;

    private JButton runButton;
    private JPanel menuPanel;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField animalNumberField;
    private JTextField startEnergyField;
    private JTextField moveEnergyField;
    private JTextField plantEnergyField;
    private JTextField jungeRatioField;
    private JButton loadButton;

    private final Parameters parameters;

    public MenuPage(){
        frame = new JFrame();

        frame.setTitle("Main Menu");
        frame.setLocationRelativeTo(null);
        frame.setSize(420, 420);
        frame.setResizable(false);

        frame.setContentPane(menuPanel);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        runButton.addActionListener(this);
        loadButton.addActionListener(this);

        parameters = new Parameters();
    }

    private boolean areArgumentsCorrect(){
        for (Component comp : menuPanel.getComponents()){
            try {
                if (comp instanceof JTextField) {
                    String text = ((JTextField) comp).getText();

                    if (comp.equals(jungeRatioField)) {
                        Float.parseFloat(text);
                    } else {
                        Integer.parseInt(text);
                    }
                }
            }
            catch (NumberFormatException ignored) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton){

            if (areArgumentsCorrect()){
                float jungeRatio = Float.parseFloat(jungeRatioField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int animalNumber = Integer.parseInt(animalNumberField.getText());
                if (jungeRatio < 0 || jungeRatio > 1){
                    JOptionPane.showMessageDialog(frame, "Jungle ratio must be between 0 and 1!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else if (width <= 0 || height <= 0){
                    JOptionPane.showMessageDialog(frame, "Please select positive width and height!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else if(animalNumber > width * height){
                    JOptionPane.showMessageDialog(frame, "Too many animals to fit on the map!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String [] options = {"Yes", "No"};
                    int x;
                    if (width > 100 || height > 60){
                        x = JOptionPane.showOptionDialog(frame,
                                "Selected size is over display bounds (width <= 100, height <= 60) and map will not be displayed. Do you wish to continue?",
                                "Over display bounds",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                    }
                    else {
                        x = 0;
                    }
                    if (x == 0){
                        int startEnergy = Integer.parseInt(startEnergyField.getText());
                        int moveEnergy = Integer.parseInt(moveEnergyField.getText());
                        int plantEnergy = Integer.parseInt(plantEnergyField.getText());
                        new ProjectEngine(width, height, animalNumber, startEnergy, moveEnergy, plantEnergy, jungeRatio);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(frame, "Incorrect simulation parameters!!!","Error!", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == loadButton){
            String [] params = parameters.getParameters();
            widthField.setText(params[0]);
            heightField.setText(params[1]);
            animalNumberField.setText(params[2]);
            startEnergyField.setText(params[3]);
            moveEnergyField.setText(params[4]);
            plantEnergyField.setText(params[5]);
            jungeRatioField.setText(params[6]);
        }
    }

}
