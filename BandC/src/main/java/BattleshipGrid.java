
public class BattleshipGrid {

    private int xAxis;
    private int yAxis;
    private BattleshipSquare[][] gridOfSquares;
    private Ship[] fleet;

    public BattleshipGrid(int xAxis, int yAxis, Ship[] fleet){
        this.fleet = fleet;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        gridOfSquares = new BattleshipSquare[xAxis][yAxis];
        for (int x = 0; x < xAxis; x++){
            for (int y = 0; y < yAxis; y++){
                gridOfSquares[x][y] = new BattleshipSquare();
            }
        }
    }
    public boolean checkVictory(){
        for(Ship ship : fleet){
            if(ship.getHealth() > 0){
                return false;
            }
        }
        return true;
    }
    public boolean checkShipStatus(char key){
        for(Ship ship : fleet){


            if(ship.getIdentifer() == key){
                if(ship.getHealth() < 1){
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false; //should never happen
    }
    public void computerPlaceShips(){

        for(Ship ship : fleet){
            while(true){
                double randDir = Math.random();

                int randXStart = (int) (Math.random() * xAxis);
                int randYStart = (int) (Math.random() * yAxis);

                if(randDir < .25){
                    if(ship.placeShips(randXStart, randXStart + (ship.getLength()-1 ), randYStart , randYStart)){
                        break;
                    }
                } else if(randDir < .5){

                    if(ship.placeShips(randXStart, randXStart - (ship.getLength()-1 ), randYStart , randYStart)){
                        break;
                    }
                }else if(randDir < .75){
                    if(ship.placeShips(randXStart, randXStart , randYStart , randYStart - (ship.getLength() -1))){
                        break;
                    }
                }
                else {
                    if(ship.placeShips(randXStart, randXStart , randYStart , randYStart + (ship.getLength() -1))){
                        break;
                    }
                }
            }
        }

    }
    public Ship getShip(char key){ //could make this a map
        for(Ship ship : fleet){


            if(ship.getIdentifer() == key){
               return ship;
            }
        }
        return null;
    }

    public BattleshipSquare getSquare(int xAxis, int yAxis) {
        return gridOfSquares[xAxis][yAxis];
    }

//    public Grid(int dimentions){
//        this(dimentions, dimentions);
//    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public char[][] drawGrid(boolean cheat, boolean show0s){
        int length = gridOfSquares.length;
        char[][] screenToPrint = new char[xAxis][yAxis];
        for(int i = gridOfSquares[0].length -1; i >=0; i--){

            for(int ii = 0; ii < gridOfSquares.length; ii++){
               if(!cheat) {
                   char printer = gridOfSquares[ii][i].getHiddenUnitHere();
                   if(printer == '0' && !show0s ){
                       printer = ' ';
                   }
//                   System.out.print(printer + " ");
                   screenToPrint[ii][i] = printer;
               }
               else {
                   char printer = gridOfSquares[ii][i].getUnitHere();
                   if(printer == '0' && !show0s){
                       printer = ' ';
                   }
//                   System.out.print(printer + " ");
                   screenToPrint[ii][i] = printer;
               }
            }
//            System.out.print((i+1) + "  ");
//            System.out.println();
        }
        char test = 'A';
//        System.out.print("");
        for(int i = gridOfSquares.length -1; i >=0; i--){


//            System.out.print(test + " ");
            test++;
        }
        return screenToPrint;
    }
//    public void drawGrid(boolean cheat){
//        int length = gridOfSquares.length;
//        for(int i = gridOfSquares[0].length -1; i >=0; i--){
//
//            for(int ii = 0; ii < gridOfSquares.length; ii++){
//                System.out.print(gridOfSquares[ii][i].getUnitHere() + " ");
//            }
//            System.out.println();
//        }
//
//
//    }
}
