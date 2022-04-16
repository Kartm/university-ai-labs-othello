public class LeastMovesPlayer extends MinimaxPlayer {

    public LeastMovesPlayer(String pName) {
        super(pName);
    }

    public LeastMovesPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.depth = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluateBoard(Othello game) {
        int myMoves = game.generateMoves(colour).size();
        int opponentMoves = game.generateMoves(game.opponent(colour)).size();

        if(myMoves == 0) {
            return -1 * opponentMoves * opponentMoves;
        }

        int sign = myMoves - opponentMoves > 0 ? -1:1;

        return sign * (myMoves - opponentMoves) * (myMoves - opponentMoves);
    }

}
