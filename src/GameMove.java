public class GameMove {
    private int toRow, toCol;
    private boolean emptyMove = false;
    private boolean gameOver = false;

    public GameMove(int r, int c) {
        toRow = r;
        toCol = c;
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