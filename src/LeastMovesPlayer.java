public class LeastMovesPlayer extends MinimaxPlayer {

    public LeastMovesPlayer(String pName) {
        super(pName);
    }

    public LeastMovesPlayer(String pName, int ply, boolean abEnabled) {
        super(pName);
        this.PLY = ply;
        this.abEnabled = abEnabled;
    }

    public int evaluationFn(Othello game) {
        int myMoves = game.generateMoves(colour).size();
        int opponentMoves = game.generateMoves(game.opponent(colour)).size();

        if(myMoves == 0) {
            return -1 * (int)Math.round(Math.pow((opponentMoves), 2));
        }

        int sign = myMoves - opponentMoves > 0 ? -1:1;

        return sign * (int)Math.round(Math.pow((myMoves - opponentMoves), 2));
    }

}
