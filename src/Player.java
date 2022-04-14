import java.lang.String;

public abstract class Player {
    public String name;
    public BoardField colour;

    public Player() {
        name = "Player";
        colour = BoardField.EMPTY;
    }

    public Player(String pName) {
        name = pName;
        colour = BoardField.EMPTY;
    }

    public abstract void initialize(BoardField pColour);

    public abstract OthelloMove makeMove(Othello game);

}
