import java.util.ArrayList;

public abstract class MinimaxPlayer extends Player {
    protected int depth = 4; //the depth of the minimax
    protected boolean abEnabled = false;
    public long startTime;
    public long endTime;
    public long timeSoFar = 0;


    public MinimaxPlayer(String pName) {
        name = pName;
        colour = BoardField.EMPTY;
    }

    public int getDepth() {
        return depth;
    }

    public void initialize(BoardField pColour) {
        colour = pColour;
    }

    public OthelloMove makeMove(Othello game) {

        startTime = System.nanoTime();

        var move = abEnabled ? makeMoveAlphaBeta(game):makeMoveNoAlphaBeta(game);

        endTime = System.nanoTime();

        timeSoFar += endTime - startTime;

        return move;
    }

    private OthelloMove makeMoveNoAlphaBeta(Othello game) {
        ArrayList<OthelloMove> possibleMoves = game.generateMoves(colour);

        if (possibleMoves.size() == 0) {
            OthelloMove noMoves = new OthelloMove(0, 0);
            noMoves.notAmove();
            possibleMoves.add(noMoves);
        }

        int best = 0;
        int utility = Integer.MIN_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMoves.get(i));
            int tmp = abMinNoAlphaBeta(newGame, depth);
            if (tmp > utility) {
                utility = tmp;
                best = i;
            }
        }

        return possibleMoves.get(best);
    }

    private int abMaxNoAlphaBeta(Othello game, int cutOff) {
        if (--cutOff == 0) return evaluationFn(game);
        ArrayList<OthelloMove> possibleMoves = game.generateMoves(colour);
        int utility = Integer.MIN_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMove);
            int tmp = abMinNoAlphaBeta(newGame, cutOff);
            if (utility < tmp) {
                utility = tmp;
            }
        }
        return utility;
    }

    private int abMinNoAlphaBeta(Othello game, int cutOff) {
        if (--cutOff == 0) return evaluationFn(game);
        ArrayList<OthelloMove> possibleMoves = game.generateMoves(game.opponent(colour));
        int utility = Integer.MAX_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(game.opponent(colour), possibleMove);
            int tmp = abMaxNoAlphaBeta(newGame, cutOff);
            if (utility > tmp) {
                utility = tmp;
            }
        }
        return utility;
    }

    private OthelloMove makeMoveAlphaBeta(Othello game) {

        ArrayList<OthelloMove> possibleMoves = game.generateMoves(colour);
        if (possibleMoves.size() == 0) {
            OthelloMove noMoves = new OthelloMove(0, 0);
            noMoves.notAmove();
            possibleMoves.add(noMoves);
        }

        int best = 0;
        int utility = Integer.MIN_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMoves.get(i));
            int tmp = abMinAlphaBeta(newGame, utility, Integer.MAX_VALUE, depth);
            if (tmp > utility) {
                utility = tmp;
                best = i;
            }
        }



        return possibleMoves.get(best);
    }

    private int abMaxAlphaBeta(Othello game, int a, int b, int cutOff) {
        if (--cutOff == 0) return evaluationFn(game);
        ArrayList<OthelloMove> possibleMoves = game.generateMoves(colour);
        int utility = Integer.MIN_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMove);
            int tmp = abMinAlphaBeta(newGame, a, b, cutOff);
            if (utility < tmp) {
                utility = tmp;
            }
            if (utility >= b) return utility;
            a = utility;
        }
        return utility;
    }

    private int abMinAlphaBeta(Othello game, int a, int b, int cutOff) {
        if (--cutOff == 0) return evaluationFn(game);
        ArrayList<OthelloMove> possibleMoves = game.generateMoves(game.opponent(colour));
        int utility = Integer.MAX_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(game.opponent(colour), possibleMove);
            int tmp = abMaxAlphaBeta(newGame, a, b, cutOff);
            if (utility > tmp) utility = tmp;
            if (utility <= a) return utility;
            b = utility;
        }
        return utility;
    }

    public abstract int evaluationFn(Othello game);
}
