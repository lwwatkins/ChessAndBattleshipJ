public abstract class Square {
    private char unitHere = '0';

    public char getUnitHere() {
        return unitHere;
    }
    public char placeUnitHere(char newUnit){
        char temp = unitHere;
        unitHere = newUnit;
        return unitHere;
    }
}
