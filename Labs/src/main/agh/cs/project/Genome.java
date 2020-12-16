package agh.cs.project;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private final int [] genes = new int [32];
    private final static Random random = new Random();

    public Genome() {
        int [] genomeTab = new int [] {0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 32; i++){
            int randInd = (int)(Math.random() * 8);
            genomeTab[randInd]++;
        }
        this.genomeFromGenomeTab(genomeTab);
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

        System.out.println(switchIndexOne + " " + switchIndexTwo);

        int [] curParent = firstParent;
        int [] genomeTab = new int [] {0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 32; i++){
            if (i == switchIndexOne) curParent = secondParent;
            if (i == switchIndexTwo) curParent = firstParent;
            genomeTab[curParent[i]]++;
        }

        genomeFromGenomeTab(genomeTab);
    }

    public String toString(){
        return Arrays.toString(this.genes);
    }

    private void genomeFromGenomeTab(int [] genomeTab){
        for (int i = 0; i < 8; i++){
            while (genomeTab[i] == 0){
                int randInd = (int)(Math.random() * 8);
                if (genomeTab[randInd] > 1){
                    genomeTab[randInd]--;
                    genomeTab[i]++;
                }
            }
        }
        int ind = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < genomeTab[i]; j++, ind++){
                this.genes[ind] = i;
            }
        }
    }

    public int getRandomGene(){
        return this.genes[random.nextInt(genes.length)];
    }
}
