package agh.cs.project.Sources;

import java.util.*;

public class GenomeQuantitySet{

//Multizbiór genów; dla każdy gen jest przechowywany wraz z ilością jego wystąpień; posortowany malejąco po ilości wystąpień

////////////////////////////////////////////////////////////////////////////////////////////////////////

//Klasy pomocnicze

    private static class GenomeQuantitySetElement{
        public Genome genome;
        public int muliple;
        public GenomeQuantitySetElement(Genome genome){
            this.genome = genome;
            muliple = 1;
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final SortedSet<GenomeQuantitySetElement> quantitySet;

    public GenomeQuantitySet(){
        quantitySet = new TreeSet<>(new compareGenomes());
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void add(Genome genome) {
        GenomeQuantitySetElement newElement = new GenomeQuantitySetElement(genome);
        for (GenomeQuantitySetElement element : quantitySet) {
            if (element.genome.compare(newElement.genome) == 0) {
                quantitySet.remove(element);
                element.muliple++;
                quantitySet.add(element);
                return;
            }
        }
        quantitySet.add(newElement);
    }

    public void remove(Genome genome) {
        GenomeQuantitySetElement newElement = new GenomeQuantitySetElement(genome);
        for (GenomeQuantitySetElement element : quantitySet) {
            if (element.genome.compare(newElement.genome) == 0) {
                quantitySet.remove(element);
                element.muliple--;
                if (element.muliple > 0) quantitySet.add(element);
                return;
            }
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Genome getDominant(){
        if (quantitySet.size() == 0) return null;
        return quantitySet.first().genome;
    }
}
