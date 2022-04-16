public class OthelloMove {
    private int toRow, toCol;
    private boolean emptyMove = false;
    private boolean gameOver = false;

    public OthelloMove() {
    }

    public OthelloMove(int r, int c) {
        toRow = r;
        toCol = c;
    }

    public OthelloMove(OthelloMove c) {
        toRow = c.getRow();
        toCol = c.getCol();
    }

    public int getRow() {
        return toRow;
    }

    public int getCol() {
        return toCol;
    }

    public void surrender() {
        gameOver = true;
        emptyMove = true;
    }

    public void makeEmpty() {
        emptyMove = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isEmptyMove() {
        return emptyMove;
    }

}