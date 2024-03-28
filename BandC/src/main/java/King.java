import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(boolean isWhite, ChessSquare startSquare, int xCord, int yCord, ChessGrid grid) {
        super(isWhite, startSquare, xCord, yCord, grid, 'K');
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public List<ChessSquare> getPossibleSquares() {
        List<ChessSquare> moveableSquares = new ArrayList<>();


        currentSquare = grid.getSquare(xCord, yCord + 1);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }
        }

        currentSquare = grid.getSquare(xCord, yCord - 1);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }

        }

        currentSquare = grid.getSquare(xCord + 1, yCord);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }

        }

        currentSquare = grid.getSquare(xCord - 1, yCord);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }

        }

        currentSquare = grid.getSquare(xCord + 1, yCord + 1);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }

        }


        currentSquare = grid.getSquare(xCord + 1, yCord - 1);

        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }
        }

        currentSquare = grid.getSquare(xCord - 1, yCord + 1);


        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }

        }

        currentSquare = grid.getSquare(xCord - 1, yCord - 1);

        if (currentSquare != null && currentSquare.getChessUnitHere() == null) moveableSquares.add(currentSquare);
        else {
            if (currentSquare != null && currentSquare.getChessUnitHere().colorIsWhite != this.colorIsWhite) {
                moveableSquares.add(currentSquare);
            }
        }


        List<ChessSquare> castleSquares = checkForCastle();
        moveableSquares.addAll(castleSquares);

        return moveableSquares;
    }

    public List<ChessSquare> checkForCastle() {

        List<ChessSquare> castleSquares = new ArrayList<>();
        if (!hasmoved) {
            boolean failThis = false;
            for (int i = xCord - 1; i >= 1; i--) {
                currentSquare = grid.getSquare(i, yCord);
                if (currentSquare != null && currentSquare.getChessUnitHere() != null) failThis = true;

            }
            currentSquare = grid.getSquare(0, yCord);
            if (!failThis && !currentSquare.getChessUnitHere().hasmoved) {
                castleSquares.add(grid.getSquare(xCord - 2, yCord));

            }
            failThis = false;
            for (int i = xCord + 1; i < 7; i++) {
                currentSquare = grid.getSquare(i, yCord);
                if (currentSquare != null && currentSquare.getChessUnitHere() != null) failThis = true;

            }
            currentSquare = grid.getSquare(7, yCord);
            if (!failThis && !currentSquare.getChessUnitHere().hasmoved) {
                castleSquares.add(grid.getSquare(xCord + 2, yCord));


            }
        }
        return castleSquares;

    }
}
