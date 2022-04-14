public class WeightedSquaresPlayer extends MinimaxPlayer {

    public WeightedSquaresPlayer(String pName) {
        super(pName);
    }

    public WeightedSquaresPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.PLY = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluationFn(Othello game) {
        return game.weightedSquares(colour) - game.weightedSquares(game.opponent(colour));
    }
}
