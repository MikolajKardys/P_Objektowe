package agh.cs.project.Sources;

import java.util.Random;

public class Genome {

//Struktura przechowująca i wykonująca potrzebne operacje na genomach

    private final int [] genes = new int [32];
    private final static Random random = new Random();

    public Genome() {                         //konstruktor losowego genomu
        int [] quantities = new int [] {0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 32; i++){
            int randInd = (int)(Math.random() * 8);
            quantities[randInd]++;
        }
        genomeFromQuantities(quantities);
    }
    public Genome (Genome genomeA, Genome genomeB){ //konstruktor genomu odziedziczonego po rodzicach
        int [] firstParentGenes;
        int [] secondParentGenes;
        int whichParent = (int)(Math.random() * 2); // ma Pan random
        if (whichParent == 0){
            firstParentGenes = genomeA.genes;
            secondParentGenes = genomeB.genes;
        }
        else {
            firstParentGenes = genomeB.genes;
            secondParentGenes = genomeA.genes;
        }

        int switchIndexOne = (int)(Math.random() * 30) + 1;
        int switchIndexTwo = (int)(Math.random() * 30) + 1;
        while (switchIndexTwo == switchIndexOne) switchIndexTwo = (int)(Math.random() * 30) + 1;    // do-while

        int [] quantities = new int [] {0, 0, 0, 0, 0, 0, 0, 0};
        int [] curParentGenes = firstParentGenes;
        for (int i = 0; i < 32; i++){
            if (i == switchIndexOne) curParentGenes = secondParentGenes;
            if (i == switchIndexTwo) curParentGenes = firstParentGenes;
            quantities[curParentGenes[i]]++;
        }
        genomeFromQuantities(quantities);
    }

    //Ustawia odpowiedni genom za pomocą tablicy, w której quantities[i] jest równe ilości wystąpień i w genomie
    private void genomeFromQuantities(int [] quantities){   // sugeruję naprawę genomu trzymać w osobnej metodzie
        for (int i = 0; i < 8; i++){
            while (quantities[i] == 0){
                int randInd = (int)(Math.random() * 8);
                if (quantities[randInd] > 1){
                    quantities[randInd]--;
                    quantities[i]++;
                }
            }
        }
        int ind = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < quantities[i]; j++, ind++){
                genes[ind] = i;
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {                             //zwraca genom jako 32-znakowy string
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 32; i++){
            s.append(genes[i]);
        }
        return s.toString();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getRandomGene(){
        return genes[random.nextInt(genes.length)];
    }

    //prosty komparator, porównujący geny "alfabetycznie"
    public int compare(Genome other){
        for (int i = 0; i < 32; i++){
            if (genes[i] != other.genes[i]){
                return genes[i] - other.genes[i];
            }
        }
        return 0;
    }
}
