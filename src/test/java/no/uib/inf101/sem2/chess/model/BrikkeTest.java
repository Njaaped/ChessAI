package no.uib.inf101.sem2.chess.model;

import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;

import no.uib.inf101.sem2.chess.model.brikker.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;



public class BrikkeTest {

  Board board = new Board();

  @Test
  public void checkValidMovesPawn() {
    Rute rute = board.getRute(0, 1);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "P");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(0, 3)), true); // 2 squares when starting position
    assertEquals(brikke.isValidMove(board, rute, board.getRute(0, 2)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(0, 4)), false); // 3 squares when starting position

    Rute rute2 = board.getRute(0, 6);
    Brikke brikke2 = rute2.getBrikke();
    assertEquals(brikke2.getFEN(), "p");
    assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 4)), true); // 2 squares when starting position
    assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 5)), true);
    assertEquals(brikke2.isValidMove(board, rute2, board.getRute(0, 3)), false); // 3 squares when starting position

  }

  @Test
  public void checkValidMovesQueen() {
    board.setRute(5, 5, new Rute(5, 5, new Queen(true)));
    Rute rute = board.getRute(5, 5);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "Q");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 4)), true); // straight
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 3)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(7, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 4)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(3, 3)), true);
    // not into other pieces of same color
    assertFalse(brikke.isValidMove(board, rute, board.getRute(1, 1)));
    // not into pieces of different color behind other pieces
    assertFalse(brikke.isValidMove(board, rute, board.getRute(5, 7)));
    // into pieces of different color
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 6)), true);
    board.setRute(5, 5, new Rute(5,5, null));
  }

  @Test
  public void checkValidMovesKing() {
    board.setRute(5, 5, new Rute(5, 5, new King(true)));
    Rute rute = board.getRute(5, 5);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "K");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 4)), true); // straight
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 4)), true); // diagonal
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 4)), true);

    board.setRute(5, 5, new Rute(5,5, null));
  }

  @Test
  public void checkValidMovesKnight() {
    board.setRute(5, 5, new Rute(5, 5, new Knight(true)));
    Rute rute = board.getRute(5, 5);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "N");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 7)), true); // L
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 7)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(3, 6)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(3, 4)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 3)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 3)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(7, 4)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(7, 6)), true);

    board.setRute(5, 5, new Rute(5,5, null));
  }

  @Test
  public void checkValidMovesBishop() {
    board.setRute(5, 5, new Rute(5, 5, new Bishop(true)));
    Rute rute = board.getRute(5, 5);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "B");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 4)), true); // diagonal
    assertEquals(brikke.isValidMove(board, rute, board.getRute(3, 3)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 4)), true);
    // not into other pieces of same color
    assertFalse(brikke.isValidMove(board, rute, board.getRute(1, 1)));
    // not into pieces of different color behind other pieces
    assertFalse(brikke.isValidMove(board, rute, board.getRute(7, 7)));
    // into pieces of different color
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 6)), true);
    board.setRute(5, 5, new Rute(5,5, null));
  }

  @Test
  public void checkValidMovesRook() {
    board.setRute(5, 5, new Rute(5, 5, new Rook(true)));
    Rute rute = board.getRute(5, 5);
    Brikke brikke = rute.getBrikke();
    assertEquals(brikke.getFEN(), "R");
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 4)), true); // straight
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 3)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(6, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(7, 5)), true);
    assertEquals(brikke.isValidMove(board, rute, board.getRute(4, 5)), true);
    // not into other pieces of same color
    assertFalse(brikke.isValidMove(board, rute, board.getRute(5, 1)));
    // not into pieces of different color behind other pieces
    assertFalse(brikke.isValidMove(board, rute, board.getRute(5, 7)));
    // into pieces of different color
    assertEquals(brikke.isValidMove(board, rute, board.getRute(5, 6)), true);
    board.setRute(5, 5, new Rute(5,5, null));
  }

}
