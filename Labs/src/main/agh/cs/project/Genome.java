package agh.cs.project;

import java.util.Random;

public class Genome {
    private int [] genes;
    public Genome() {
        this.genes = new int [] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7};
    }
    public Genome (Genome genomeA, Genome genomeB){    //genomeA jest ten główny, z niego idą 2/3

    }

    public int getRandomGene(){
        return this.genes[new Random().nextInt(genes.length)];
    }
}
