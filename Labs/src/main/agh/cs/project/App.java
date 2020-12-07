package agh.cs.project;

import javax.swing.*;

class App {

    public static void main(String [] args) throws InterruptedException {
        JFrame f = new JFrame("Map");

        int fieldSize = 25;
        int width = 20;
        int height = 30;

        f.setSize(fieldSize * width,fieldSize * height); //setting frame size

        ProjectEngine engine = new ProjectEngine(f, fieldSize, height, width, 22, 100, 10);
        engine.run();

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}

