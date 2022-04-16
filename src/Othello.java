import java.lang.String;
import java.util.ArrayList;

public class Othello {
    private static final int boardSize = 8;
    private BoardField[][] board;
    public static String newline = System.getProperty("line.separator");

    private static final int[] dirR = {-1, 1, 0, 0, 1, -1, 1, -1};
    private static final int[] dirC = {0, 0, 1, -1, 1, -1, -1, 1};

    public Othello() {
        board = new BoardField[boardSize + 2][boardSize + 2];
        initialize();
    }

    public Othello(int n, BoardField[][] workingBoard) {
        if (n != boardSize + 2) {
            System.out.println(" Invalid game board!");
        } else {
            board = workingBoard;
        }
    }

    public Othello(Othello k) {
        board = k.getClone();
    }

    public void initialize() {
        for (int i = 1; i < boardSize + 1; i++) {
            for (int j = 1; j < boardSize + 1; j++) {
                board[i][j] = BoardField.EMPTY;
            }
        }
        for (int i = 0; i < boardSize + 2; i++) {
            board[0][i] = board[i][0] = board[boardSize + 1][i] = board[i][boardSize + 1] = BoardField.BORDER;
        }
        int i = boardSize / 2;
        board[i][i] = board[i + 1][i + 1] = BoardField.WHITE;
        board[i][i + 1] = board[i + 1][i] = BoardField.BLACK;
    }

    public int countBoardFields(BoardField symbol) {
        int count = 0;

        for (int c = 1; c <= boardSize; c++) {
            for (int r = 1; r <= boardSize; r++) {
                if (board[c][r] == symbol) {
                    count++;
                }
            }
        }

        return count;
    }

    public int PenaltyReward(BoardField player) {
        // @formatter:off
        // minus is a penalty
        int[][] weights = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 100, -6, 12, 3, 3, 12, -6, 100, 0},
                {0, -6, -20, -3, -3, -3, -3, -20, -6, 0},
                {0, 12, -3, 6, 1, 1, 6, -3, 12, 0},
                {0, 3, -3, 1, 1, 1, 1, -3, 3, 0},
                {0, 3, -3, 1, 1, 1, 1, -3, 3, 0},
                {0, 12, -3, 6, 1, 1, 6, -3, 12, 0},
                {0, -6, -20, -3, -3, -3, -3, -20, -6, 0},
                {0, 100, -6, 12, 3, 3, 12, -6, 100, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        // @formatter:on

        int sum = 0;
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (board[i][j] == player) {
                    sum += weights[i][j];
                }
            }
        }
        return sum;
    }

    public BoardField opponent(BoardField player) {
        return player == BoardField.BLACK ? BoardField.WHITE : BoardField.BLACK;
    }

    public boolean isMoveFlipping(BoardField player, int r, int c, int dir) {
        int row = r, col = c;
        boolean flag = false;
        for (int i = 0; i < 8; i++) {// in every direction 8 times
            row += dirR[dir];
            col += dirC[dir];
            if (board[row][col] == opponent(player)) {
                flag = true;
            } else if (board[row][col] == player) {
                return flag;
            } else return false;
        }
        return false;
    }

    public void flip(BoardField player, int r, int c, int dir) {
        if (isMoveFlipping(player, r, c, dir)) {
            r += dirR[dir];
            c += dirC[dir];
            while (board[r][c] != player) {
                board[r][c] = player;
                r += dirR[dir];
                c += dirC[dir];
            }
        }
    }

    public boolean isValidMove(BoardField player, int r, int c) {
        if (board[r][c] == BoardField.EMPTY) {
            for (int k = 0; k < 8; k++) {
                if (isMoveFlipping(player, r, c, k)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyMove(BoardField player) {
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (isValidMove(player, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeMove(BoardField player, OthelloMove m) {
        int r = m.getRow();
        int c = m.getCol();
        if (isValidMove(player, r, c)) {
            board[r][c] = player;
            for (int i = 0; i < boardSize; i++) {
                flip(player, r, c, i);
            }
        }
    }

    public String toString(BoardField currentMove) {
        System.out.println(currentMove == BoardField.BLACK ? "Black's move." : "White's move.");
        var possibleMoves = getPossibleMoves(currentMove);

        StringBuilder result;

        result = new StringBuilder("  ");

        for (int i = 1; i <= boardSize; i++) {
            result.append(i).append(" ");
        }
        result.append(newline);
        for (int y = 1; y <= boardSize; y++) {
            result.append(y).append(" ");
            for (int x = 1; x <= boardSize; x++) {
                String sign;
                if (board[y][x] == BoardField.WHITE) {
                    sign = ConsoleColors.WHITE_BRIGHT + "■" + ConsoleColors.RESET;
                } else if (board[y][x] == BoardField.BLACK) {
                    sign = ConsoleColors.BLACK + "■" + ConsoleColors.RESET;
                } else {
                    int finalX = x;
                    int finalY = y;
                    if (possibleMoves.stream().anyMatch(move -> move.getCol() == finalX && move.getRow() == finalY)) {
                        sign = ConsoleColors.WHITE + "▢" + ConsoleColors.RESET;
                    } else {
                        sign = ConsoleColors.WHITE + "·" + ConsoleColors.RESET;
                    }
                }
                result.append(sign).append(" ");
            }
            result.append(newline);
        }

        return result.toString();
    }

    public ArrayList<OthelloMove> getPossibleMoves(BoardField player) {
        ArrayList<OthelloMove> possibleMoves = new ArrayList<>();
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (isValidMove(player, i, j)) {
                    OthelloMove aMove = new OthelloMove(i, j);
                    possibleMoves.add(aMove);
                }
            }
        }
        return possibleMoves;
    }

    public BoardField[][] getClone() {
        BoardField[][] newBoard = new BoardField[boardSize + 2][boardSize + 2];

        for (int i = 0; i < boardSize + 2; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, boardSize);
        }

        return newBoard;
    }

    public BoardField play(Player p1, Player p2, boolean silent) {
        p1.initialize(BoardField.BLACK);
        p2.initialize(BoardField.WHITE);
        BoardField currentMove = BoardField.BLACK;
        int counter = 0;

        while (true) {
            OthelloMove move;

            counter++;

            if(!silent) {
                System.out.println("Turn #" + counter);
                System.out.println(toString(currentMove));
            }

            if (currentMove != BoardField.WHITE) {

                move = p1.makeMove(this);
                if (move.isEmptyMove()) {
                    if (hasAnyMove(p2.colour)) {
                        if(!silent) {
                            System.out.println(p1.name + "'s (Black) move.");
                        }
                        if (move.isGameOver()) {
                            if(!silent) {
                                System.out.println(p1.name + " concedes. Game Over!\n");
                            }
                            return p2.colour;
                        } else {
                            if(!silent) {
                                System.out.println("No valid moves. " + p1.name + " must pass.");
                            }
                            currentMove = BoardField.WHITE;
                        }
                    } else {
                        if(!silent) {
                            System.out.println("Game over!\n");
                        }
                        int difference = countBoardFields(p1.colour) - countBoardFields(p2.colour);
                        if (difference < 0) {
                            if(!silent) {
                                System.out.println(p2.name + " is the winner.");
                            }
                            return p2.colour;
                        } else {
                            if(!silent) {
                                System.out.println(p1.name + " is the winner.");
                            }
                            return p1.colour;
                        }
                    }
                } else {
                    if(!silent) {
                        System.out.println(p1.name + "'s (Black) move.");
                    }
                    makeMove(p1.colour, move);
                    String moveString = "The move is    " + move.getRow() + ", " + move.getCol();
                    if(!silent) {
                        System.out.println(moveString);
                    }

                    currentMove = BoardField.WHITE;
                }
            } else {
                move = p2.makeMove(this);

                if (move.isEmptyMove()) {
                    if (hasAnyMove(p1.colour)) {
                        if(!silent) {
                            System.out.println(p2.name + "'s (White) move.");
                        }
                        if (move.isGameOver()) {
                            if(!silent) {
                                System.out.println(p1.name + " concedes. Game Over!\n");
                            }
                            return p2.colour; // ?
                        } else {
                            if(!silent) {
                                System.out.println("No valid moves." + p2.name + " must pass. ");
                            }
                            currentMove = BoardField.BLACK;
                        }
                    } else {
                        if(!silent) {
                            System.out.println("Game over!\n");
                        }
                        int difference = countBoardFields(p1.colour) - countBoardFields(p2.colour);
                        if (difference < 0) {
                            if(!silent) {
                                System.out.println(p2.name + " is the winner.");
                            }
                            return p2.colour;
                        } else {
                            if(!silent) {
                                System.out.println(p1.name + " is the winner.");
                            }
                            return p1.colour;
                        }
                    }
                } else {
                    if(!silent) {
                        System.out.println(p2.name + "'s (White) move.");
                    }
                    makeMove(p2.colour, move);
                    currentMove = BoardField.BLACK;
                    String moveString = "The move is " + move.getRow() + ", " + move.getCol();
                    if(!silent) {
                        System.out.println(moveString);
                    }
                }
            }

        }

    }

}
