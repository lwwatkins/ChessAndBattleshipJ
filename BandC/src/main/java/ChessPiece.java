import java.util.List;


public abstract class ChessPiece {
    protected ChessGrid grid;
    protected ChessSquare currentSquare; //get rid of this because this doesn't point to the same object (i think)
    protected int xCord;
    protected int yCord;
    protected boolean colorIsWhite; //else black
    protected char key;
    protected boolean hasmoved = false;
    public abstract List<ChessSquare> getPossibleSquares();
    public boolean isWhite(){
        return colorIsWhite;
    }

    protected void setWhite(boolean isWhite){
        colorIsWhite = isWhite;
    }
    public boolean hasMoved(){
        return hasmoved;
    }

    public char getKey() {
        return key;
    }
    public void moveToSquare(ChessSquare newSquare){//this never used remove if you like


        currentSquare.removePiece();
        newSquare.addChessPiece(this);
    }
    public ChessPiece(boolean isWhite,  ChessSquare startSquare, int xCord, int yCord, ChessGrid grid , char key){
        this.setWhite(isWhite);
        currentSquare = startSquare;
        this.xCord =xCord;
        this.yCord = yCord;
        this.grid = grid;

        if(isWhite){
            this.key = key;
        }
        else {
            this.key = Character.toLowerCase(key);
        }

    }

    public void moveToCord(int xCord, int yCord) {
        this.xCord =xCord;
        this.yCord = yCord;
        hasmoved =true; // this can fail if a moved is reversed

    }

    public boolean isKing() {
        return false;
    }

    public int getxCord() {
        return xCord;
    }
    public int getyCord(){
        return yCord;
    }

    public void undoMove() {
        hasmoved = false;
    }
}
