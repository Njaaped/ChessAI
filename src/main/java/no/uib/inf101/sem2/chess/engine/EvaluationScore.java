package no.uib.inf101.sem2.chess.engine;


import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.game.Player;
import no.uib.inf101.sem2.chess.model.brikker.Pawn;
import no.uib.inf101.sem2.chess.model.brikker.Knight;
import no.uib.inf101.sem2.chess.model.brikker.Bishop;
import no.uib.inf101.sem2.chess.model.brikker.Rook;
import no.uib.inf101.sem2.chess.model.brikker.Queen;
import no.uib.inf101.sem2.chess.model.brikker.King;

/**
 * 
 * a Class which evaluates the score of the board
 * 
 * methods:
 * evaluate() - evaluates the score of the board
 * getPieceValue() - returns the value of a piece
 * 
 * 
 * 
 * @author njal.pedersen
 */


public class EvaluationScore {

    private Board board;
    private int whiteScore;
    private int blackScore;

    public EvaluationScore(Board board, Player currentPlayer) {
        this.board = board;
        this.whiteScore = 0;
        this.blackScore = 0;
    }

    /**
     * returns the value of a piece
     * @param piece
     * @return the value of a piece
     */

    private int getPieceValue(Brikke piece) {
        if (piece instanceof Pawn) {
            return 100;
        } else if (piece instanceof Knight) {
            return 280;
        } else if (piece instanceof Bishop) {
            return 320;
        } else if (piece instanceof Rook) {
            return 500;
        } else if (piece instanceof Queen) {
            return 900;
        } else if (piece instanceof King) {
            return 6000;
        }
        return 0;

    }

    /**
     * evaluates the score of the board
     * @return the score of the board
     */

    public int evaluate() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (rute.getBrikke() != null) {
                    if (rute.getBrikke().isWhite()) {
                        whiteScore += getPieceValue(rute.getBrikke());
                    } else {
                        blackScore += getPieceValue(rute.getBrikke());
                    }
                }
            }
        }
        
        // position transposition tables for each piece // their best positions on the board has higher values

        int[][] pawn = {{0, 0, 0, 0, 0, 0, 0, 0},
                        {78, 83, 86, 73, 102, 82, 85, 90},
                        {7, 29, 21, 44, 40, 31, 44, 7},
                        {-17, 16, -2, 15, 14, 0, 15, -13},
                        {-26, 3, 10, 9, 6, 1, 0, -23},
                        {-22, 9, 5, -11, -10, -2, 3, -19},
                        {-31, 8, -7, -40, -39, -31, -30, -28},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
        
        int[][] knight = {{-66, -53, -75, -75, -10, -55, -58, -70},
                        {-3, -6, 100, -36, 4, 62, -4, -14},
                        {10,  67,   1,  74,  73,  27,  62,  -2},
                     {24,  24,  45,  37,  33,  41,  25,  17},
                     {-1,   5,  31,  21,  22,  35,   2,   0},
                    {-18,  10,  13,  22,  18,  15,  11, -14},
                    {-23, -15,   2,   0,   2,   0, -23, -20},
                    {-74, -23, -26, -24, -19, -35, -22, -69}};
        
        int[][] bishop = {{-59, -78, -82, -76, -23,-107, -37, -50},
                       {-11,  20,  35, -42, -39,  31,   2, -22},
                        {-9,  39, -32,  41,  52, -10,  28, -14},
                        {25,  17,  20,  34,  26,  25,  15,  10},
                        {13,  10,  17,  23,  17,  16,   0,   7},
                        {14,  25,  24,  15,   8,  25,  20,  15},
                        {19,  20,  11,   6,   7,   6,  20,  16},
                        {-7,   2, -15, -12, -14, -15, -10, -10}};
        
        int[][] rook = { {35,  29,  33,   4,  37,  33,  56,  50},
                        {55,  29,  56,  67,  55,  62,  34,  60},
                        {19,  35,  28,  33,  45,  27,  25,  15},
                        {0,   5,  16,  13,  18,  -4,  -9,  -6},
                        {-28, -35, -16, -21, -13, -29, -46, -30},
                        {-42, -28, -42, -25, -25, -35, -26, -46},
                        {-53, -38, -31, -26, -29, -43, -44, -53},
                        {-30, -24, -18,   5,  -2, -18, -31, -32}};

        
        int[][] queen = {{6,   1,  -8,-104,  69,  24,  88,  26},
                        {14,  32,  60, -10,  20,  76,  57,  24},
                        {-2,  43,  32,  60,  72,  63,  43,   2},
                        {1, -16,  22,  17,  25,  20, -13,  -6},
                        {-14, -15,  -2,  -5,  -1, -10, -20, -22},
                        {-30,  -6, -13, -11, -16, -11, -16, -27},
                        {-36, -18,   0, -19, -15, -15, -21, -38},
                        {-39, -30, -31, -13, -31, -36, -34, -42}};

        int[][] king = {{4,  54,  47, -99, -99,  60,  83, -62},
                        {-32,  10,  55,  56,  56,  55,  10,   3},
                        {-62,  12, -57,  44, -67,  28,  37, -31},
                        {-55,  50,  11,  -4, -19,  13,   0, -49},
                        {-55, -43, -52, -28, -51, -47,  -8, -50},
                        {-47, -42, -43, -79, -64, -32, -29, -32},
                        {-4,   3, -14, -50, -57, -18,  13,   4},
                        {17,  30,  -3, -14,   6,  -1,  40,  18}};


        // loop through the board and count the points
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (rute.getBrikke() != null) {
                    if (rute.getBrikke() instanceof King && !rute.getBrikke().isWhite()) {
                        blackScore += king[j][i];   
                    } else if (rute.getBrikke() instanceof Queen && !rute.getBrikke().isWhite()) {
                        blackScore += queen[j][i];
                    } else if (rute.getBrikke() instanceof Rook && !rute.getBrikke().isWhite()) {
                        blackScore += rook[j][i];
                    } else if (rute.getBrikke() instanceof Bishop && !rute.getBrikke().isWhite()) {
                        blackScore += bishop[j][i];
                    } else if (rute.getBrikke() instanceof Knight && !rute.getBrikke().isWhite()) {
                        blackScore += knight[j][i];
                    } else if (rute.getBrikke() instanceof Pawn && !rute.getBrikke().isWhite()) {
                        blackScore += pawn[j][i];
                    } else if (rute.getBrikke() instanceof King && rute.getBrikke().isWhite()) {
                        whiteScore += king[7 - j][7 - i];
                    } else if (rute.getBrikke() instanceof Queen && rute.getBrikke().isWhite()) {
                        whiteScore += queen[7 - j][7 - i];
                    } else if (rute.getBrikke() instanceof Rook && rute.getBrikke().isWhite()) {
                        whiteScore += rook[7 - j][7 - i];
                    } else if (rute.getBrikke() instanceof Bishop && rute.getBrikke().isWhite()) {
                        whiteScore += bishop[7 - j][7 - i];
                    } else if (rute.getBrikke() instanceof Knight && rute.getBrikke().isWhite()) {
                        whiteScore += knight[7 - j][7 - i];
                    } else if (rute.getBrikke() instanceof Pawn && rute.getBrikke().isWhite()) {
                        whiteScore += pawn[7 - j][7 - i];
                    }
                }
            }
        }

        int score = whiteScore - blackScore;

        return score;
    }
    
}
