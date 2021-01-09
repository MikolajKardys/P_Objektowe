package agh.cs.project.Sources; // skoro ten pakiet zawiera źródła, to co zawierają pozostałe?

abstract public class AbstractWorldMapElement {
    protected Vector2d position;

    public AbstractWorldMapElement(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }
}
//Zdecydowałem się dodać tą klasę, ale odpuściłem sobie interfejs, bo byłaby w nim jedna metoda, a nie wykluczam, że w
//toku przyszych laboratoriów na tyle uprościmy funkcję toString że też się tu będzie mogła znaleźć