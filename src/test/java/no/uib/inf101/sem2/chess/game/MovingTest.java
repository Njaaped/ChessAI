package no.uib.inf101.sem2.chess.game;

import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.model.brikker.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;


public class MovingTest {

    Player human = new HumanPlayer(true);
    Player computer = new ComputerPlayer(false);
    Game game = new Game(human, computer, 4);
    Moving moving = new Moving(game);
    
    
    private Board emptygameBoard() {
        String expected = "4K3/8/8/8/8/8/8/4k3KQkq";
        Board board = game.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (!(rute.getBrikke() instanceof King)) {
                    rute.setBrikke(null);
                } 
            }   
        }
        assertEquals(expected, board.updateFEN(), "actual FEN:" + board.updateFEN() + " your FEN: " + expected);
        return board;
        
    }

    @Test
    public void checkValidMovesPawn() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 1, new Rute(0, 1, new Pawn(true)));
        Rute rute = board.getRute(0, 1);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "P");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 2));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());

        game.changePlayer();
        board.setRute(0, 6, new Rute(0, 6, new Pawn(false)));
       
        Rute rute2 = board.getRute(0, 6);
        Brikke brikke2 = rute2.getBrikke();
        assertEquals(brikke2.getFEN(), "p");
        assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 4)), true); // 2 squares when starting position
        assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 5)), true);
        assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 3)), false); // 3 squares when starting position
        Move theMove2 = new Move(game.getCurrentPlayer(), rute2, board.getRute(0, 4));
        assertEquals(moving.makeMove(theMove2, game.getCurrentPlayer()), true);
        moving.undoMove(theMove2, game.getCurrentPlayer());
        theMove2 = new Move(game.getCurrentPlayer(), rute2, board.getRute(0, 5));
        assertEquals(moving.makeMove(theMove2, game.getCurrentPlayer()), true);
        moving.undoMove(theMove2, game.getCurrentPlayer());
        theMove2 = new Move(game.getCurrentPlayer(), rute2, board.getRute(0, 3));
        assertEquals(moving.makeMove(theMove2, game.getCurrentPlayer()), false);


    }

    @Test
    public void checkValidMovesRook() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        Rute rute = board.getRute(0, 0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "R");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
    
    }

    @Test
    public void checkValidMovesKnight() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Knight(true)));
        Rute rute = board.getRute(0, 0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "N");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(1, 2));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 1));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
    }

    @Test
    public void checkValidMovesBishop() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Bishop(true)));
        Rute rute = board.getRute(0, 0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "B");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
    }

    @Test
    public void checkValidMovesQueen() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Queen(true)));
        Rute rute = board.getRute(0, 0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "Q");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 3));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
    }

    @Test
    public void checkValidCastling() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(5, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(7, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
    }

    @Test 
    public void checkValidCastling2() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        board.setCanCastleBlackKingSide(false);
        board.setCanCastleBlackQueenSide(false);
        board.setCanCastleWhiteKingSide(false);
        board.setCanCastleWhiteQueenSide(false);
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(5, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(7, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
    }

    @Test 
    public void checkValidCastling3() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        board.setCanCastleBlackKingSide(false);
        board.setCanCastleBlackQueenSide(false);
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(5, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(7, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
    }

    @Test
    public void castlingKingMoved() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        board.setCanCastleBlackKingSide(false);
        board.setCanCastleBlackQueenSide(false);
        Move themove = new Move(game.getCurrentPlayer(), rute, board.getRute(3, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        themove = new Move(game.getCurrentPlayer(), board.getRute(3, 0), board.getRute(4, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(5, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), true);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(7, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
        themove = new Move(game.getCurrentPlayer(), rute, board.getRute(0, 0));
        assertEquals(moving.makeMove(themove, game.getCurrentPlayer()), false);
        moving.undoMove(themove, game.getCurrentPlayer());
    }

    @Test
    public void castlingQueenSideRookMoved() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        Move move = new Move(game.getCurrentPlayer(), board.getRute(0, 0), board.getRute(1, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        move = new Move(game.getCurrentPlayer(), board.getRute(1, 0), board.getRute(0, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        move = new Move(game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), false);
        moving.undoMove(move, game.getCurrentPlayer());
        move = new Move (game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        moving.undoMove(move, game.getCurrentPlayer());

    }

    @Test

    public void castlingKingSideRookMoved() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(4,0);
        Brikke brikke = rute.getBrikke();
        assertEquals(brikke.getFEN(), "K");
        assertEquals(board.getRute(7, 0).getBrikke().getFEN(), "R");
        Move move = new Move(game.getCurrentPlayer(), board.getRute(7, 0), board.getRute(6, 0));    
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        move = new Move(game.getCurrentPlayer(), board.getRute(6, 0), board.getRute(7, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        move = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), false);
        moving.undoMove(move, game.getCurrentPlayer());
        move = new Move (game.getCurrentPlayer(), rute, board.getRute(2, 0));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        moving.undoMove(move, game.getCurrentPlayer());

    }

    @Test

    public void checkEnPassant1() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(6, 6, new Rute(6, 6, new Pawn(false)));
        board.setRute(5, 4, new Rute(5, 4, new Pawn(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(6,6);
        Brikke brikke = rute.getBrikke();
        game.changePlayer();
        assertEquals(brikke.getFEN(), "p");
        Move move = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 4));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        game.changePlayer();
        move = new Move(game.getCurrentPlayer(), board.getRute(5, 4), board.getRute(6, 5));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
    }

    @Test

    public void checkEnPassant2() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(6, 6, new Rute(6, 6, new Pawn(false)));
        board.setRute(5, 4, new Rute(5, 4, new Pawn(true)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(6,6);
        Brikke brikke = rute.getBrikke();
        game.changePlayer();
        assertEquals(brikke.getFEN(), "p");
        Move move = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 5));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        move = new Move(game.getCurrentPlayer(), board.getRute(6, 5), board.getRute(6, 4));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        game.changePlayer();
        move = new Move(game.getCurrentPlayer(), board.getRute(5, 4), board.getRute(6, 3));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), false);
    }

    @Test
    public void checkEnPassant3() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(6, 6, new Rute(6, 6, new Pawn(false)));
        board.setRute(5, 4, new Rute(5, 4, new Pawn(true)));
        board.setRute(0, 1, new Rute(0, 1, new Pawn(true)));
        board.setRute(0, 6, new Rute( 0, 6, new Pawn(false)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Rute rute = board.getRute(6,6);
        Brikke brikke = rute.getBrikke();
        game.changePlayer();
        assertEquals(brikke.getFEN(), "p");
        Move move = new Move(game.getCurrentPlayer(), rute, board.getRute(6, 4));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        game.changePlayer();
        move = new Move(game.getCurrentPlayer(), board.getRute(0, 1), board.getRute(0, 3));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        game.changePlayer();
        move = new Move(game.getCurrentPlayer(), board.getRute(0, 6), board.getRute(0, 4));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), true);
        game.changePlayer();
        move = new Move(game.getCurrentPlayer(), board.getRute(5, 4), board.getRute(6, 3));
        assertEquals(moving.makeMove(move, game.getCurrentPlayer()), false);
    }
    
    @Test
    public void testKingInCheckCastling() {
        Board board = game.getBoard();
        board = emptygameBoard();
        board.setRute(7, 0, new Rute(7, 0, new Rook(true)));
        board.setRute(0, 0, new Rute(0, 0, new Rook(true)));
        board.setRute(0, 1, new Rute(4, 6, new Queen(false)));
        assertEquals(4, board.getKingRute(true).getX());
        assertEquals(0, board.getKingRute(true).getY());
        Move move = new Move(game.getCurrentPlayer(), board.getRute(4, 0), board.getRute(6, 0));
        assertEquals(false, moving.makeMove(move, game.getCurrentPlayer()));
    }


    
}
