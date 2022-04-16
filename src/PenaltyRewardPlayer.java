public class PenaltyRewardPlayer extends AbstractMinimaxPlayer {

    public PenaltyRewardPlayer(String pName) {
        super(pName);
    }

    public PenaltyRewardPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.depth = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluateBoard(Game game) {
        return game.PenaltyReward(colour) - game.PenaltyReward(game.opponent(colour));
    }
}
