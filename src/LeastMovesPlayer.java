public class LeastMovesPlayer extends AbstractMinimaxPlayer {

    public LeastMovesPlayer(String pName) {
        super(pName);
    }

    public LeastMovesPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.depth = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluateBoard(Game game) {
        int myMoves = game.getPossibleMoves(colour).size();
        int opponentMoves = game.getPossibleMoves(game.opponent(colour)).size();

        if(myMoves == 0) {
            return -1 * opponentMoves * opponentMoves;
        }

        int sign = myMoves - opponentMoves > 0 ? -1:1;

        return sign * (myMoves - opponentMoves) * (myMoves - opponentMoves);
    }

}
