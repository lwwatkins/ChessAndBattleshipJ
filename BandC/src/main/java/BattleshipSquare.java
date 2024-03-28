public class BattleshipSquare extends Square{
    private Ship localShip;
    public char getHiddenUnitHere(){
        if(this.getUnitHere() == 'X'){
            return 'X';
        }
        if(this.getUnitHere() == '@'){
            return '@';
        }
        return '0';

    }

    public boolean PlaceShip(Ship toPlace){
        char unitHere = this.getUnitHere();
        if (unitHere != '0'){

            return false;
        }
        unitHere = toPlace.getIdentifer();
        return true;
    }
    public char Fire(){
        char unitHere = this.getUnitHere();
        if(unitHere == '0'){
            placeUnitHere('@');
            return '0';
        }
        if(unitHere == '@'){
            throw new IllegalArgumentException();
        }
        if(unitHere == 'X'){
            throw new IllegalArgumentException();
        }
        placeUnitHere('X');
        localShip.hitShip();
        return unitHere;

    }

    public void placeUnitHere( Ship ship) {
        localShip = ship;
    }

    public BattleshipSquare(){

    }
}
