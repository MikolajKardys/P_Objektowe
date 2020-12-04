package agh.cs.project;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Genome {
    int [] genes;
    public Genome(int [] genes){
        this.genes = genes;
    }
    public Genome (Genome genomeA, Genome genomeB){    //genomeA jest ten główny, z niego idą 2/3

    }

    public int getRandomGene(){
        return this.genes[new Random().nextInt(genes.length)];
    }
}
