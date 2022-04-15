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
        int opMoves = game.generateMoves(game.opponent(colour)).size();

        if(myMoves == 0) {
            return -opMoves;
        }

        return (-1) * (myMoves - opMoves);
    }

}
