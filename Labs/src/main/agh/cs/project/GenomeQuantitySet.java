package agh.cs.project;

import java.util.*;

public class GenomeQuantitySet{

    private static class GenomeQuantitySetElement{
        public Genome genome;
        public int muliple;
        public GenomeQuantitySetElement(Genome genome){
            this.genome = genome;
            this.muliple = 1;
        }
    }

    private static class compareGenomes implements Comparator<GenomeQuantitySetElement>{
        @Override
        public int compare (GenomeQuantitySetElement first, GenomeQuantitySetElement second){
            if (first.muliple != second.muliple){
                return second.muliple - first.muliple;
            }
            return first.genome.compare(second.genome);
        }
    }

    private final SortedSet<GenomeQuantitySetElement> quantitySet;

    public GenomeQuantitySet(){
        this.quantitySet = new TreeSet<>(new compareGenomes());
    }

    public void add(Genome genome) {
        GenomeQuantitySetElement newElement = new GenomeQuantitySetElement(genome);
        for (GenomeQuantitySetElement element : quantitySet) {
            if (element.genome.compare(newElement.genome) == 0) {
                this.quantitySet.remove(element);
                element.muliple++;
                this.quantitySet.add(element);
                return;
            }
        }
        this.quantitySet.add(newElement);
    }

    public void remove(Genome genome) {
        GenomeQuantitySetElement newElement = new GenomeQuantitySetElement(genome);
        for (GenomeQuantitySetElement element : quantitySet) {
            if (element.genome.compare(newElement.genome) == 0) {
                this.quantitySet.remove(element);
                element.muliple--;
                if (element.muliple > 0) this.quantitySet.add(element);
                return;
            }
        }
    }

    public String getDominant(){
        if (this.quantitySet.size() == 0) return "None";
        return this.quantitySet.first().genome.toLongString();
    }
}
