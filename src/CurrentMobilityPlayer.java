public class CurrentMobilityPlayer extends MinimaxPlayer {

    public CurrentMobilityPlayer(String pName) {
        super(pName);
    }

    public CurrentMobilityPlayer(String pName, int ply) {
        super(pName);
        PLY = ply;
    }

    public int evaluationFn(Othello game) {
        int myMoves = game.generateMoves(colour).size();
        int opMoves = game.generateMoves(game.opponent(colour)).size();
        return 100 * ((myMoves - opMoves) / (myMoves + opMoves + 2));
    }

}
