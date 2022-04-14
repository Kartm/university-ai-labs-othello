public class WeightedSquaresPlayer extends MinimaxPlayer {

    public WeightedSquaresPlayer(String pName) {
        super(pName);
    }

    public WeightedSquaresPlayer(String pName, int ply) {
        super(pName);
        PLY = ply;
    }

    public int evaluationFn(Othello game) {
        return game.weightedSquares(colour) - game.weightedSquares(game.opponent(colour));
    }
}
