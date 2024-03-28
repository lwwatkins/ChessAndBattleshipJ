import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {


    public Pawn(boolean isWhite, ChessSquare startSquare, int xCord, int yCord, ChessGrid grid) {
        super(isWhite, startSquare, xCord, yCord, grid, 'P');
    }

    public List<ChessSquare> getPossibleSquares(){
        List<ChessSquare> moveableSquares = new ArrayList<>();
        if(colorIsWhite) {

            currentSquare = grid.getSquare(xCord, yCord + 1);
            if (currentSquare != null) { // this should always be true
                if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
                if (yCord == 1) {
                    currentSquare = grid.getSquare(xCord, yCord + 2);
                    if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
                }
            }
            currentSquare = grid.getSquare(xCord-1, yCord +1);
            if(currentSquare != null && currentSquare.getChessUnitHere() != null && !currentSquare.getChessUnitHere().isWhite()){
                moveableSquares.add(currentSquare);
            }
            currentSquare = grid.getSquare(xCord+1, yCord +1);
            if(currentSquare != null && currentSquare.getChessUnitHere() != null && !currentSquare.getChessUnitHere().isWhite()){
                moveableSquares.add(currentSquare);
            }
        } else {
            currentSquare = grid.getSquare(xCord, yCord - 1);
            if (currentSquare != null) { // this should always be true
                if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
                if (yCord == 6) {
                    currentSquare = grid.getSquare(xCord, yCord - 2);
                    if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
                }
            }
            currentSquare = grid.getSquare(xCord-1, yCord -1);
            if(currentSquare != null && currentSquare.getChessUnitHere() != null && currentSquare.getChessUnitHere().isWhite()){
                moveableSquares.add(currentSquare);
            }
            currentSquare = grid.getSquare(xCord+1, yCord -1);
            if(currentSquare != null && currentSquare.getChessUnitHere() != null && currentSquare.getChessUnitHere().isWhite()){
                moveableSquares.add(currentSquare);
            }
        }

      return moveableSquares;

    }
}
