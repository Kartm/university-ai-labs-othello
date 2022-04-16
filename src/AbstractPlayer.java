import java.lang.String;

public abstract class AbstractPlayer {
    public String name;
    public BoardField colour;

    public AbstractPlayer() {
        name = "Player";
        colour = BoardField.EMPTY;
    }

    public AbstractPlayer(String pName) {
        name = pName;
        colour = BoardField.EMPTY;
    }

    public abstract void initialize(BoardField pColour);

    public abstract GameMove makeMove(Game game);

}
