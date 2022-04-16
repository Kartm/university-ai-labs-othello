import java.util.Scanner;
import java.lang.String;

public class HumanPlayer extends Player {

    public HumanPlayer() {
    }

    public HumanPlayer(String pName) {
        super(pName);
    }

    public void initialize(BoardField pColour) {
        colour = pColour;
    }

    public OthelloMove makeMove(Othello game) {

        boolean validInput = false;
        OthelloMove humanMove = null;
        Scanner scanner = new Scanner(System.in);
        int col, row;
        if (game.hasAnyMove(colour) == false) {
            OthelloMove noMove = new OthelloMove(0, 0);
            noMove.makeEmpty();
            System.out.println("Here!! no valid moves!\n");///////////////////////////////////
            return noMove;
        }
        // repeat until the player enters a valid move
        while (!validInput) {
            System.out.println("Enter row  col (or -1 to concede): ");
            try {
                row = scanner.nextInt();
                // If the game is conceded
                if (row == -1) {
                    OthelloMove gameOver = new OthelloMove(0, 0);
                    gameOver.surrender();
                    return gameOver;
                } else {
                    col = scanner.nextInt();
                    // if we reach here the player has entered valid input
                    // we now need to check if this is a valid move
                    if (game.isValidMove(colour, row, col)) {
                        humanMove = new OthelloMove(row, col);
                        validInput = true;
                    } else System.out.println("Invalid Move!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
        ;
        return humanMove;

    }

}