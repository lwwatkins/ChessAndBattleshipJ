import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Ship {
    private char identifer;
    private boolean isSunk = false;
    private int health;
    private int length;
    private BattleshipGrid battleshipGrid;
    private BattleshipSquare[] location;
    private String name;

    public Ship( String name, int length, char identifer){
        this.battleshipGrid = battleshipGrid;
        this.name = name;
        this.health = length;
        this.length = length;
        this.identifer = identifer;
    }
    public void setGrid(BattleshipGrid battleshipGrid){
        this.battleshipGrid = battleshipGrid;
    }
    public String getSinkMessage(){
        return "You sunk my " + name + "!";
    }


    public int getHealth() {
        return health;
    }
    public int hitShip(){
        health--;
        return health;
    }

    public int getLength() {
        return length;
    }

    public char getIdentifer() {
        return identifer;
    }
    public boolean placeShips(int startX, int endX, int startY, int endY){
        if(startX < 0 || startY < 0 ||  endX < 0 || endY < 0 ){
            return false;
        }
        if(startX > battleshipGrid.getxAxis()-1 || startY > battleshipGrid.getyAxis()-1 || endX > battleshipGrid.getxAxis()-1 || endY > battleshipGrid.getyAxis()-1 ){ //add -1 here but not 100% that its fixing the problem
            return false;
        }
        if(startX != endX && startY != endY){
            return false;
         //   throw new IllegalArgumentException("The ship is not straight ");
        }
        if(abs(startX - endX) != length-1 && abs(startY - endY) != length-1){
            return false;
            //throw new IllegalArgumentException("The ship is too short or too long ");
        }

        List<Integer> xList = new ArrayList<>(); // the must be a better way
        List<Integer> yList = new ArrayList<>();

        location = new BattleshipSquare[length];
        if(startX < endX)
        {
            for (int i = startX; i <= endX; i++) { //loop through all spots
                if (battleshipGrid.getSquare(i, startY).getUnitHere() != '0') { //returns false if not empty

                    return false;
                }
                location[i - startX] = battleshipGrid.getSquare(i, startY); //place in array
                xList.add(i);
                yList.add(startY);
            }
        }
        if(startX > endX)
        {
            for (int i = endX; i <= startX; i++) {

                if (battleshipGrid.getSquare(i, startY).getUnitHere() != '0') {

                    return false;
                }
                location[i - endX] = battleshipGrid.getSquare(i, startY);
                xList.add(i);
                yList.add(startY);
            }
        }
        if(startY <endY )
        {
            for (int i = startY; i <= endY; i++) {
                if (battleshipGrid.getSquare(startX, i).getUnitHere() != '0') {

                    return false;
                }

                location[i - startY] = battleshipGrid.getSquare(startX, i);
                xList.add(startX);
                yList.add(i);
            }
        }
        if(startY > endY)
        {
            for (int i = endY; i <= startY; i++) {

                if (battleshipGrid.getSquare(startX, i).getUnitHere() != '0') {
                    return false;
                }
                location[i - endY] = battleshipGrid.getSquare(startX, i);
                xList.add(startX);
                yList.add(i);
            }
        }
        for(int i = 0; i< location.length ; i++){

            location[i] = battleshipGrid.getSquare(xList.get(i),yList.get(i));
            location[i].placeUnitHere(identifer);
            location[i].placeUnitHere(this);
        }
        return true;
    }

    public String getName() {
        return name;
    }
}
