import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece{

    public Rook(boolean isWhite, ChessSquare startSquare, int xCord, int yCord, ChessGrid grid) {
        super(isWhite, startSquare, xCord, yCord, grid, 'R');
    }

    @Override
    public List<ChessSquare> getPossibleSquares() {
        List<ChessSquare> moveableSquares = new ArrayList<>();
        int adder = 1;
        while (true) {
            currentSquare = grid.getSquare(xCord, yCord + adder);

            if (currentSquare == null)break;
                if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
                else {
                    if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                        moveableSquares.add(currentSquare);
                    }
                    break;
                }
            adder++;
        }
        adder = 1;
        while (true) {
            currentSquare = grid.getSquare(xCord, yCord - adder);

            if (currentSquare == null)break;
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                    moveableSquares.add(currentSquare);
                }
                break;
            }
            adder++;
        }
        adder = 1;
        while (true) {
            currentSquare = grid.getSquare(xCord + adder, yCord );

            if (currentSquare == null)break;
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                    moveableSquares.add(currentSquare);
                }
                break;
            }
            adder++;
        }
        adder = 1;
        while (true) {
            currentSquare = grid.getSquare(xCord -adder, yCord );

            if (currentSquare == null)break;
            if (currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
            else {
                if (currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                    moveableSquares.add(currentSquare);
                }
                break;
            }
            adder++;
        }
        return moveableSquares;
    }

}
