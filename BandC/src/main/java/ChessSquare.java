public class ChessSquare extends Square{
    private ChessPiece pieceHere = null;
    int xcord;
    int ycord;
    public ChessSquare(int xcord, int ycord){
        this.xcord = xcord;
        this.ycord = ycord;
    }
    public int[] getCords(){
        return new int[] {xcord,ycord};
    }

    public ChessPiece getChessUnitHere() {
        return pieceHere;
    }

    public void addChessPiece(ChessPiece newPiece){
        pieceHere = newPiece;
        placeUnitHere(pieceHere.getKey());
    }

    public void removePiece() {
        pieceHere = null;
        placeUnitHere('0');
    }
}
