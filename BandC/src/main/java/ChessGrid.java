import java.util.ArrayList;
import java.util.List;

public class ChessGrid {


    private int xAxis;
    private int yAxis;
    private ChessSquare[][] gridOfSquares;


    public ChessGrid(){
        this.xAxis = 8;
        this.yAxis = 8;
        gridOfSquares = new ChessSquare[xAxis][yAxis];
        for (int x = 0; x < xAxis; x++){
            for (int y = 0; y < yAxis; y++){
                gridOfSquares[x][y] = new ChessSquare(x,y);
            }
        }
       placePeices();

    }

    public ChessGrid(char[][] grid){//add booleans for king/rook being moved
        this.xAxis = 8;
        this.yAxis = 8;
        gridOfSquares = new ChessSquare[xAxis][yAxis];
        for (int x = 0; x < xAxis; x++){
            for (int y = 0; y < yAxis; y++){
                gridOfSquares[x][y] = new ChessSquare(x,y);
            }
        }
        for (int x = 0; x < xAxis; x++){
            for (int y = 0; y < yAxis; y++){
                gridOfSquares[x][y] = new ChessSquare(x,y);
            }
        }

        for(int y = 0; y<grid[0].length; y++ ){
            for(int x = 0; x<grid.length; x++ ){
                char unitKey = grid[x][y];
                if(unitKey != '0'){
                    createUnitFromChar(unitKey, x , y);
                }
            }
        }
    }

    public boolean checkForCheck(boolean whiteTurn){
        List<ChessSquare> dangerSquares = this.getDangerSpaces(!whiteTurn);
        for(ChessSquare currentSquare : dangerSquares){
            if(currentSquare.getChessUnitHere() != null){
                if(currentSquare.getChessUnitHere().isKing() && currentSquare.getChessUnitHere().colorIsWhite != whiteTurn){
                    return true;

                }
            }
        }
        return false;
    }

    private void createUnitFromChar(char unitKey, int x, int y) {
        if(unitKey == 'P'){
            getSquare(x,y).addChessPiece(new Pawn(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'p'){
            getSquare(x,y).addChessPiece(new Pawn(false, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'R'){
            getSquare(x,y).addChessPiece(new Rook(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'r'){
            getSquare(x,y).addChessPiece(new Rook(false, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'N'){
            getSquare(x,y).addChessPiece(new Knight(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'n'){
            getSquare(x,y).addChessPiece(new Knight(false, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'B'){
            getSquare(x,y).addChessPiece(new Bishop(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'b'){
            getSquare(x,y).addChessPiece(new Bishop(false, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'Q'){
            getSquare(x,y).addChessPiece(new Queen(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'q'){
            getSquare(x,y).addChessPiece(new Queen(false, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'K'){
            getSquare(x,y).addChessPiece(new King(true, getSquare(x,y) ,x,y,this));
        }else if(unitKey == 'k'){
            getSquare(x,y).addChessPiece(new King(false, getSquare(x,y) ,x,y,this));
        }
    }

    private void placePeices(){
        for (int i = 0; i < 8; i++){
            getSquare(i,1).addChessPiece(new Pawn(true, getSquare(i,1), i,1,this));
        }
        for (int i = 0; i < 8; i++){
            getSquare(i,6).addChessPiece(new Pawn(false, getSquare(i,6), i,6,this));
        }
        getSquare(0,0).addChessPiece(new Rook(true, getSquare(0,0), 0,0,this));
        getSquare(7,0).addChessPiece(new Rook(true, getSquare(7,0), 7,0,this));
        getSquare(0,7).addChessPiece(new Rook(false, getSquare(0,7), 0,7,this));
        getSquare(7,7).addChessPiece(new Rook(false, getSquare(7,7), 7,7,this));
        getSquare(1,0).addChessPiece(new Knight(true, getSquare(1,0), 1,0,this));
        getSquare(6,0).addChessPiece(new Knight(true, getSquare(6,0), 6,0,this));
        getSquare(1,7).addChessPiece(new Knight(false, getSquare(1,7), 1,7,this));
        getSquare(6,7).addChessPiece(new Knight(false, getSquare(6,7), 6,7,this));
        getSquare(2,0).addChessPiece(new Bishop(true, getSquare(2,0), 2,0,this));
        getSquare(5,0).addChessPiece(new Bishop(true, getSquare(5,0), 5,0,this));
        getSquare(2,7).addChessPiece(new Bishop(false, getSquare(2,7), 2,7,this));
        getSquare(5,7).addChessPiece(new Bishop(false, getSquare(5,7), 5,7,this));
        getSquare(3,0).addChessPiece(new Queen(true, getSquare(3,0), 3,0,this));
        getSquare(3,7).addChessPiece(new Queen(false, getSquare(3,7), 3,7,this));
        getSquare(4,0).addChessPiece(new King(true, getSquare(4,0), 4,0,this));
        getSquare(4,7).addChessPiece(new King(false, getSquare(4,7), 4,7,this));
    }

    public ChessSquare getSquare(int xAxis, int yAxis) {
        try {
            return gridOfSquares[xAxis][yAxis];
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }
    public ChessPiece getUnit(int xAxis, int yAxis){
        return getSquare(xAxis,yAxis).getChessUnitHere();
    }
    public void moveUnitToNewSpace(int startX, int startY, int endX, int endY){
        ChessPiece moveUnit = getUnit(startX,startY);
        ChessSquare startSquare = getSquare(startX,startY);
        ChessSquare endSquare =getSquare(endX,endY);

        moveUnit.moveToCord(endX, endY);
        startSquare.removePiece();
        endSquare.addChessPiece(moveUnit);

    }

    public List<ChessSquare> getDangerSpaces(boolean isWhite) {
        List<ChessSquare> dangerSquares = new ArrayList<>();
        for(int i = gridOfSquares[0].length -1; i >=0; i--){

            for(int ii = 0; ii < gridOfSquares.length; ii++){

               ChessPiece unit = getUnit(i,ii);
               if(unit != null && unit.colorIsWhite != isWhite){
                   List<ChessSquare> currentSquares = unit.getPossibleSquares();
                   for(ChessSquare currentSquare : currentSquares){
                       if(!dangerSquares.contains(currentSquare)) dangerSquares.add(currentSquare);
                   }
               }
            }

        }
        return dangerSquares;
    }
    public ChessSquare promotePawnsCheck(){
        for(int i = 0; i < 8; i++){
            ChessPiece unit = this.getUnit(i,0);
            if(unit != null) {
                if (unit.getKey() == 'P' || unit.getKey() == 'p') {
                    return getSquare(i, 0);
                }
            }
                unit = this.getUnit(i, 7);
            if(unit != null) {
                if (unit.getKey() == 'P' || unit.getKey() == 'p') {
                    return getSquare(i, 7);
                }
            }
        }

        return null;
    }

    public char[][] drawGrid(boolean cheat, boolean show0s){
        int length = gridOfSquares.length;
        char[][] gridToPrint = new char[8][8];
        for(int i = gridOfSquares[0].length -1; i >=0; i--){

            for(int ii = 0; ii < gridOfSquares.length; ii++){

                    char printer = gridOfSquares[i][ii].getUnitHere();
                    if(printer == '0' && !show0s){
                        printer = ' ';
                    }
                    gridToPrint[i][ii] = printer;
//                    System.out.print(printer + " ");

            }
//            System.out.print((i+1) + "  ");
//            System.out.println();
        }
//        char test = 'A';
//        System.out.print("");
//        for(int i = gridOfSquares.length -1; i >=0; i--){
//
//
//            System.out.print(test + " ");
//            test++;
//        }
        return gridToPrint;
    }

    public int getXAxis() {
        return xAxis;
    }
    public int getYAxis() {//give this to grid
        return yAxis;
    }
}
