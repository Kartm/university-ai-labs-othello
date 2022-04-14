public class FrontierDisksPlayer extends MinimaxPlayer {

    public FrontierDisksPlayer(String pName) {
        super(pName);
    }

    public FrontierDisksPlayer(String pName, int ply) {
        super(pName);
        PLY = ply;
    }

    public int evaluationFn(Othello game) {
        return game.frontierDisks(colour) - game.frontierDisks(game.opponent(colour));
    }
}
