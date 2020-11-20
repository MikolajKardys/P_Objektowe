package agh.cs.lab2;

abstract class AbstractWorldMapElement {
    protected Vector2d position;
    protected AbstractWorldMapElement(Vector2d position){
        this.position = position;
    }
    public Vector2d getPosition(){
        return this.position;
    }
}
//Zdecydowałem się dodać tą klasę, ale odpuściłem sobie interfejs, bo byłaby w nim jedna metoda, a nie wykluczam, że w
//toku przyszych laboratoriów na tyle uprościmy funkcję toString że też się tu będzie mogła znaleźć