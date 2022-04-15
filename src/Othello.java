import java.lang.String;
import java.util.ArrayList;

public class Othello {
    private static final int size = 8;
    private BoardField[][] cBoard;
    public static String newline = System.getProperty("line.separator");

    private static final int[] dirR = {-1, 1, 0, 0, 1, -1, 1, -1};
    private static final int[] dirC = {0, 0, 1, -1, 1, -1, -1, 1};

    public Othello() {
        cBoard = new BoardField[size + 2][size + 2];
        reset();
    }

    public Othello(int n, BoardField[][] workingBoard) {
        if (n != size + 2) {
            System.out.println(" Invalid game board!");
        } else cBoard = workingBoard;
    } // end of constructor

    public Othello(Othello k) {
        cBoard = k.getBoardCopy();
    }

    public void reset() {

        // Define two counters and set them to zero
        int i, j;
        for (i = 1; i < size + 1; i++)
            for (j = 1; j < size + 1; j++)
                cBoard[i][j] = BoardField.EMPTY;
        for (i = 0; i < size + 2; i++)
            cBoard[0][i] = cBoard[i][0] = cBoard[size + 1][i] = cBoard[i][size + 1] = BoardField.BORDER;
        i = size / 2;
        cBoard[i][i] = cBoard[i + 1][i + 1] = BoardField.WHITE;
        cBoard[i][i + 1] = cBoard[i + 1][i] = BoardField.BLACK;
    } // End of reset

    public int countSymbol(BoardField symbol) {
        int count = 0;
        for (int c = 1; c <= size; c++) {
            for (int r = 1; r <= size; r++) {
                if (cBoard[c][r] == symbol) {
                    count++;
                }
            }
        }
        return count;
    }

    public int weightedSquares(BoardField player) {
        // @formatter:off
        // minus is a penalty
        int[][] weights = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 240, -40, 60, 20, 20, 60, -40, 240, 0},
                {0, -40, -80, -20, -20, -20, -20, -80, -40, 0},
                {0, 60, -20, 40, 5, 5, 40, -20, 60, 0},
                {0, 20, -20, 5, 5, 5, 5, -20, 20, 0},
                {0, 20, -20, 5, 5, 5, 5, -20, 20, 0},
                {0, 60, -20, 40, 5, 5, 40, -20, 60, 0},
                {0, -40, -80, -20, -20, -20, -20, -80, -40, 0},
                {0, 240, -40, 60, 20, 20, 60, -40, 240, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        // @formatter:on

        int sum = 0;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (cBoard[i][j] == player) {
                    sum += weights[i][j];
                }
            }
        }
        return sum;
    }

    public BoardField opponent(BoardField player) {
        if (player == BoardField.BLACK) return BoardField.WHITE;
        else return BoardField.BLACK;
    }

    public boolean wouldFlip(BoardField player, int r, int c, int dir) {
        int row = r, col = c;
        boolean flag = false;
        for (int i = 0; i < 8; i++) {// in every direction 8 times
            // row is -1
            row += dirR[dir];
            col += dirC[dir];
            if (cBoard[row][col] == opponent(player)) {
                flag = true;
            } else if (cBoard[row][col] == player) {
                return flag;
            } else return false;
        }
        return false;
    }

    public void makeFlip(BoardField player, int r, int c, int dir) {
        if (wouldFlip(player, r, c, dir)) {
            r += dirR[dir];
            c += dirC[dir];
            while (cBoard[r][c] != player) {
                cBoard[r][c] = player;
                r += dirR[dir];
                c += dirC[dir];
            }
        }
    }

    public boolean validMove(BoardField player, int r, int c) {
        if (cBoard[r][c] == BoardField.EMPTY) {
            for (int k = 0; k < 8; k++)
                if (wouldFlip(player, r, c, k)) {
                    return true;
                }
        }
        return false;
    }

    public boolean anyLegalMove(BoardField player) {
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++)
                if (validMove(player, i, j)) return true;
        return false;
    }

    public void makeMove(BoardField player, OthelloMove m) {
        int r = m.getRow();
        int c = m.getCol();
        if (validMove(player, r, c)) {
            cBoard[r][c] = player;
            for (int i = 0; i < size; i++) {
                makeFlip(player, r, c, i);
            }
        }
    }

    public String boardToString(BoardField currentMove) {
        System.out.println(currentMove == BoardField.BLACK ? "Black's move." : "White's move.");
        var possibleMoves = generateMoves(currentMove);

        StringBuilder result;

        result = new StringBuilder("  ");

        for (int i = 1; i <= size; i++) {
            result.append(i).append(" ");
        }
        result.append(newline);
        for (int y = 1; y <= size; y++) {
            result.append(y).append(" ");
            for (int x = 1; x <= size; x++) {
                String sign;
                if (cBoard[y][x] == BoardField.WHITE) {
                    sign = ConsoleColors.WHITE_BRIGHT + "■" + ConsoleColors.RESET;
                } else if (cBoard[y][x] == BoardField.BLACK) {
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

    public ArrayList<OthelloMove> generateMoves(BoardField player) {
        ArrayList<OthelloMove> possibleMoves = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (validMove(player, i, j)) {
                    OthelloMove aMove = new OthelloMove(i, j);
                    possibleMoves.add(aMove);
                }
            }
        }
        return possibleMoves;
    }

    public BoardField[][] getBoardCopy() {
        BoardField[][] newBoard = new BoardField[size + 2][size + 2];

        for (int i = 0; i < size + 2; i++)
            System.arraycopy(cBoard[i], 0, newBoard[i], 0, size);

        return newBoard;
    }

    public void play(Player p1, Player p2) {
        p1.initialize(BoardField.BLACK);
        p2.initialize(BoardField.WHITE);
        BoardField currentMove = BoardField.BLACK;

        while (true) {
            OthelloMove move;

            System.out.println(boardToString(currentMove));

            if (currentMove != BoardField.WHITE) {

                move = p1.makeMove(this);
                if (move.noMoves()) {
                    if (anyLegalMove(p2.colour)) {
                        System.out.println(p1.name + "'s (Black) move.");
                        if (move.gameOver()) {
                            System.out.println(p1.name + " concedes. Game Over!\n");
                            break;
                        } else {
                            System.out.println("No valid moves. " + p1.name + " must pass.");
                            currentMove = BoardField.WHITE;
                        }
                    } else {
                        System.out.println("Game over!\n");
                        int difference = countSymbol(p1.colour) - countSymbol(p2.colour);
                        if (difference < 0) System.out.println(p2.name + " is the winner.");
                        else System.out.println(p1.name + " is the winner.");
                        break;
                    }
                } else {
                    System.out.println(p1.name + "'s (Black) move.");
                    makeMove(p1.colour, move);
                    String moveString = "The move is    " + move.getRow() + ", " + move.getCol();
                    System.out.println(moveString);

                    currentMove = BoardField.WHITE;
                }
            } else {
                move = p2.makeMove(this);

                if (move.noMoves()) {
                    if (anyLegalMove(p1.colour)) {
                        System.out.println(p2.name + "'s (White) move.");
                        if (move.gameOver()) {
                            System.out.println(p1.name + " concedes. Game Over!\n");
                            break;
                        } else {
                            System.out.println("No valid moves." + p2.name + " must pass. ");
                            currentMove = BoardField.BLACK;
                        }
                    } else {
                        System.out.println("Game over!\n");
                        int difference = countSymbol(p1.colour) - countSymbol(p2.colour);
                        if (difference < 0) System.out.println(p2.name + " is the winner.");
                        else System.out.println(p1.name + " is the winner.");
                        break;
                    }
                } else {
                    System.out.println(p2.name + "'s (White) move.");
                    makeMove(p2.colour, move);
                    currentMove = BoardField.BLACK;
                    String moveString = "The move is " + move.getRow() + ", " + move.getCol();
                    System.out.println(moveString);
                }
            }

        }

    }

}
