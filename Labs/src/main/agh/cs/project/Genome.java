package agh.cs.project;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private final int [] genes = new int [32];
    private final int [] quantities = new int [] {0, 0, 0, 0, 0, 0, 0, 0};
    private final static Random random = new Random();

    public Genome() {
        for (int i = 0; i < 32; i++){
            int randInd = (int)(Math.random() * 8);
            quantities[randInd]++;
        }
        this.genomeFromQuantities();
    }
    public Genome (Genome genomeA, Genome genomeB){
        int [] firstParent;
        int [] secondParent;
        int whichParent = (int)(Math.random() * 2);
        if (whichParent == 0){
            firstParent = genomeA.genes;
            secondParent = genomeB.genes;
        }
        else {
            firstParent = genomeB.genes;
            secondParent = genomeA.genes;
        }

        int switchIndexOne = (int)(Math.random() * 30) + 1;
        int switchIndexTwo = (int)(Math.random() * 30) + 1;
        while (switchIndexTwo == switchIndexOne) switchIndexTwo = (int)(Math.random() * 30) + 1;

        int [] curParent = firstParent;
        for (int i = 0; i < 32; i++){
            if (i == switchIndexOne) curParent = secondParent;
            if (i == switchIndexTwo) curParent = firstParent;
            quantities[curParent[i]]++;
        }

        genomeFromQuantities();
    }

    public String toString(){
        return Arrays.toString(this.quantities);
    }

    public String toLongString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 32; i++){
            s.append(this.genes[i]);
        }
        return s.toString();
    }

    private void genomeFromQuantities(){
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
                this.genes[ind] = i;
            }
        }
    }

    public int getRandomGene(){
        return this.genes[random.nextInt(genes.length)];
    }

    public int compare(Genome other){
        for (int i = 0; i < 8; i++){
            if (this.quantities[i] != other.quantities[i]){
                return this.quantities[i] - other.quantities[i];
            }
        }
        return 0;
    }
}
