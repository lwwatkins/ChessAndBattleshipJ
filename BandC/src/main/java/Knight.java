import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece{
    public Knight(boolean isWhite, ChessSquare startSquare, int xCord, int yCord, ChessGrid grid) {
        super(isWhite, startSquare, xCord, yCord, grid, 'N');
    }

    @Override
    public List<ChessSquare> getPossibleSquares() {


        List<ChessSquare> moveableSquares = findspaces(true);
        List<ChessSquare> temp = findspaces(false);
        for(ChessSquare square : temp){
            moveableSquares.add(square);
        }






        return moveableSquares;
    }

    private List<ChessSquare>  findspaces(boolean direction){
        int adder = direction ? 1 : -1;
        List<ChessSquare> moveableSquares = new ArrayList<>();
        currentSquare = grid.getSquare(xCord + 2, yCord + adder);
        if (currentSquare != null) {
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
           else {
              if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite){
                  moveableSquares.add(currentSquare);
              }
            }
        }
        currentSquare = grid.getSquare(xCord - 2, yCord + adder);
        if (currentSquare != null) {
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite){
                    moveableSquares.add(currentSquare);
                }
            }
        }
        currentSquare = grid.getSquare(xCord + adder, yCord + 2);
        if (currentSquare != null) {
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite){
                    moveableSquares.add(currentSquare);
                }
            }
        }
        currentSquare = grid.getSquare(xCord - adder, yCord - 2);
        if (currentSquare != null) {
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite){
                    moveableSquares.add(currentSquare);
                }
            }
        }



        return moveableSquares;
    }


}

