public class FrontierDisksPlayer extends MinimaxPlayer {

    public FrontierDisksPlayer(String pName) {
        super(pName);
    }

    public FrontierDisksPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.PLY = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluationFn(Othello game) {
        return game.frontierDisks(colour) - game.frontierDisks(game.opponent(colour));
    }
}
