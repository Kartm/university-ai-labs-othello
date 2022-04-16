import java.util.ArrayList;

public abstract class MinimaxPlayer extends Player {
    protected int depth = 4;
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
        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(colour);

        if (possibleMoves.size() == 0) {
            OthelloMove emptyMove = new OthelloMove(0, 0);
            emptyMove.makeEmpty();
            possibleMoves.add(emptyMove);
        }

        int currentMaxIndex = 0;
        int currentMax = Integer.MIN_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMoves.get(i));
            int tmp = abMinNoAlphaBeta(newGame, depth);
            if (tmp > currentMax) {
                currentMax = tmp;
                currentMaxIndex = i;
            }
        }
        return possibleMoves.get(currentMaxIndex);
    }

    private int abMaxNoAlphaBeta(Othello game, int currentDepth) {
        if (currentDepth == 0) {
            return evaluateBoard(game);
        }

        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(colour);
        int currentMax = Integer.MIN_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMove);
            int result = abMinNoAlphaBeta(newGame, currentDepth - 1);
            currentMax = Math.max(currentMax, result);
        }

        return currentMax;
    }

    private int abMinNoAlphaBeta(Othello game, int currentDepth) {
        if (currentDepth == 0) {
            return evaluateBoard(game);
        }

        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(game.opponent(colour));
        int currentMin = Integer.MAX_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(game.opponent(colour), possibleMove);
            int result = abMaxNoAlphaBeta(newGame, currentDepth - 1);
            currentMin = Math.min(currentMin, result);
        }

        return currentMin;
    }

    private OthelloMove makeMoveAlphaBeta(Othello game) {
        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(colour);
        if (possibleMoves.size() == 0) {
            OthelloMove noMoves = new OthelloMove(0, 0);
            noMoves.makeEmpty();
            possibleMoves.add(noMoves);
        }

        int indexOfCurrentMax = 0;
        int currentMax = Integer.MIN_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMoves.get(i));
            int result = abMinAlphaBeta(newGame, currentMax, Integer.MAX_VALUE, depth);
            if (result > currentMax) {
                currentMax = result;
                indexOfCurrentMax = i;
            }
        }

        return possibleMoves.get(indexOfCurrentMax);
    }

    private int abMaxAlphaBeta(Othello game, int alpha, int beta, int currentDepth) {
        if (currentDepth == 0) {
            return evaluateBoard(game);
        }

        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(colour);
        int currentMax = Integer.MIN_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(colour, possibleMove);
            int result = abMinAlphaBeta(newGame, alpha, beta, currentDepth - 1);
            currentMax = Math.max(currentMax, result);
            if (currentMax >= beta) {
                return currentMax;
            }
            alpha = currentMax;
        }

        return currentMax;
    }

    private int abMinAlphaBeta(Othello game, int alpha, int beta, int currentDepth) {
        if (currentDepth == 0) {
            return evaluateBoard(game);
        }

        ArrayList<OthelloMove> possibleMoves = game.getPossibleMoves(game.opponent(colour));
        int currentMin = Integer.MAX_VALUE;
        for (OthelloMove possibleMove : possibleMoves) {
            Othello newGame = new Othello(game);
            newGame.makeMove(game.opponent(colour), possibleMove);
            int result = abMaxAlphaBeta(newGame, alpha, beta, currentDepth - 1);
            currentMin = Math.min(currentMin, result);
            if (currentMin <= alpha) {
                return currentMin;
            }
            beta = currentMin;
        }

        return currentMin;
    }

    public abstract int evaluateBoard(Othello game);
}
