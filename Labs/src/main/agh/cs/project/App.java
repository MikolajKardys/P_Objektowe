package agh.cs.project;

import agh.cs.project.Visualisation.*;

import javax.swing.*;
class App {

    public static void main(String [] args){
        JFrame f = new JFrame("CheckerBoard!");
        int fieldSize = 30;
        GrassField field = new GrassField(fieldSize);
        DrawBoard board = new DrawBoard(f, field, fieldSize);

        Animal animal = new Animal(field);
        board.addAnimal(animal);

        f.setSize(750,750); //setting frame size
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

