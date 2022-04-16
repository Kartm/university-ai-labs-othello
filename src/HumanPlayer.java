import java.util.Scanner;
import java.lang.String;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(String pName) {
        super(pName);
    }

    public void initialize(BoardField pColour) {
        colour = pColour;
    }

    public GameMove makeMove(Game game) {

        boolean validInput = false;
        GameMove humanMove = null;
        Scanner scanner = new Scanner(System.in);
        int col, row;
        if (game.hasAnyMove(colour) == false) {
            GameMove noMove = new GameMove(0, 0);
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
                    GameMove gameOver = new GameMove(0, 0);
                    gameOver.surrender();
                    return gameOver;
                } else {
                    col = scanner.nextInt();
                    // if we reach here the player has entered valid input
                    // we now need to check if this is a valid move
                    if (game.isValidMove(colour, row, col)) {
                        humanMove = new GameMove(row, col);
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