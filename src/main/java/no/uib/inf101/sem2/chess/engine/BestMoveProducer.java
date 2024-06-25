package no.uib.inf101.sem2.chess.engine;

import no.uib.inf101.sem2.chess.game.Game;
import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.game.Move;
import no.uib.inf101.sem2.chess.game.Player;
import no.uib.inf101.sem2.chess.game.Moving;

import java.util.List;

/**
 * The class that gets the best move.
 * 
 * @author Njal.Pedersen
 *
 */


public class BestMoveProducer {

    private int depth;
    private Game game;
    private Moving moving;
    private GetTheMoves getTheMoves;
    private Board board;

    public BestMoveProducer(int depth, Game game, Moving moving) {
        this.game = game;
        this.depth = depth;
        this.moving = moving;
        this.getTheMoves = game.getGetTheMoves();
    }

    /**
     * search all the moves in a giving depth using the minimax algorithm with alpha-beta pruning.
     * @param depth
     * @param alpha
     * @param beta
     * @param isMaximizingPlayer
     * @return the best score for the player
     */
     
    public int searchAllMoves(int depth, int alpha, int beta, boolean isMaximizingPlayer) {

        board = game.getBoard();
        
        EvaluationScore evaluationScore = new EvaluationScore(board, p());

        if (depth == 0) {
            return -evaluationScore.evaluate();
        }

        List<Move> allOfTheNewMoves = getTheMoves.getAllMoves(p());

        CaptureScoreSort captureScoreSort = new CaptureScoreSort(allOfTheNewMoves, board);
        allOfTheNewMoves = captureScoreSort.sortMoves();

        if (allOfTheNewMoves.size() == 0) {
            if (game.isKingInCheck(p())) {
                return isMaximizingPlayer ?  -1000000 : 1000000;
            }
            return 0;
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < allOfTheNewMoves.size(); i++) {
                moving.makeMove(allOfTheNewMoves.get(i), p());
                game.changePlayer();
                bestScore = Math.max(bestScore, searchAllMoves(depth - 1, alpha, beta, !isMaximizingPlayer));
                game.changePlayer();
                moving.undoMove(allOfTheNewMoves.get(i), p());
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    return bestScore;
                }
            }
            return bestScore;

        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < allOfTheNewMoves.size(); i++) {
                moving.makeMove(allOfTheNewMoves.get(i), p());
                game.changePlayer();
                bestScore = Math.min(bestScore, searchAllMoves(depth - 1, alpha, beta, !isMaximizingPlayer));
                game.changePlayer();
                moving.undoMove(allOfTheNewMoves.get(i), p());
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    return bestScore;
                }
            }
            return bestScore;
        }
    }

    /**
     * find the best move for the player
     * uses the method searchAllMoves to find the best score, so it can find the best move.
     * @param isMaximizingPlayer
     * @return the best move for the player
     */
    
    
     public Move findBestMove(boolean isMaximizingPlayer) {
        if (depth == 0) {
            return findRandomMove();
        }
        board = game.getBoard();
        List<Move> allOfTheMoves = getTheMoves.getAllMoves(p());
        int currentScore = isMaximizingPlayer ? -100000000 : 100000000;
        Move bestMove = null;
        CaptureScoreSort captureScoreSort = new CaptureScoreSort(allOfTheMoves, board);
        allOfTheMoves = captureScoreSort.sortMoves();
        for (int j = 0; j < allOfTheMoves.size(); j++) {
            moving.makeMove(allOfTheMoves.get(j), p());
            game.changePlayer();
            int score = searchAllMoves(depth - 1, -1000000, 1000000, !isMaximizingPlayer);
            game.changePlayer();
            moving.undoMove(allOfTheMoves.get(j), p());
            if (isMaximizingPlayer && score > currentScore) {
                currentScore = score;
                bestMove = allOfTheMoves.get(j);
            } else if (!isMaximizingPlayer && score < currentScore) {
                currentScore = score;
                bestMove = allOfTheMoves.get(j);
            }
        }
        return bestMove;
    }
    
    

    /**
     * shortcut method for getting the current player
     * @return the current player
     */

    private Player p() {
        return game.getCurrentPlayer();
    }
    
    /**
     * finding a random move to implement the ultra ultra noob mode
     */

    public Move findRandomMove() {
        List<Move> allOfTheMoves = getTheMoves.getAllMoves(p());
        int random = (int) (Math.random() * allOfTheMoves.size());
        return allOfTheMoves.get(random);
    }
}
