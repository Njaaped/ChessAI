
package no.uib.inf101.sem2.chess.game;

import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.model.brikker.Pawn;
import no.uib.inf101.sem2.chess.model.brikker.Rook;
import no.uib.inf101.sem2.chess.model.brikker.King;
import no.uib.inf101.sem2.chess.model.brikker.Queen;


/**
 * Class for moving pieces on the board.
 * 
 * makeMove() for making a move
 * undoMove() for undoing a move
 * 
 * @author Njal.Pederen
 */

public class Moving {

    private Game game;

    private Board board;

    public Moving(Game game) {
        this.game = game;
    }

    /**
     * undoMove() for undoing a move
     * @param move the move to be undone
     * @param player the player who made the move
     */


    public void undoMove(Move move, Player player) {

        board = game.getBoard();

        Rute startRute = move.getStart();
        Rute endRute = move.getEnd();
        Brikke startBrikke = move.getPieceMoved();
        Brikke dreptBrikke = move.getPieceKilled();

        startRute.setBrikke(startBrikke);

        if (!move.isEnPassantMove() && dreptBrikke != null && !move.isCastlingMove()) {
            dreptBrikke.setKilled(false);
            endRute.setBrikke(dreptBrikke);
        } else if (dreptBrikke == null) {
            endRute.setBrikke(null);
        }
        
        if (move.isCastlingMove()) {
            Brikke rook = move.getRookCastled();
            Rute rookStart, rookEnd;
            if (endRute.getX() == 2) {
                // queenside castling
                rookStart = board.getRute(0, startRute.getY());
                rookEnd = board.getRute(3, startRute.getY());
                
            } else {
                // kingside castling
                rookStart = board.getRute(7, startRute.getY());
                rookEnd = board.getRute(5, startRute.getY());
                
            }


            rookStart.setBrikke(rook);
            rookEnd.setBrikke(null);
            startRute.setBrikke(startBrikke);
            endRute.setBrikke(null);
            

        }

        if (player.isWhiteSide()) {
            if (move.isBothCastleFalse()) {
                board.setCanCastleWhiteKingSide(true);
                board.setCanCastleWhiteQueenSide(true);
            } else if (move.isCastleKingSide()) {
                board.setCanCastleWhiteKingSide(true);
            } else if (move.isCastleQueenSide()) {
                board.setCanCastleWhiteQueenSide(true);
            }
        } else {
            if (move.isBothCastleFalse()) {
                board.setCanCastleBlackKingSide(true);
                board.setCanCastleBlackQueenSide(true);
            } else if (move.isCastleKingSide()) {
                board.setCanCastleBlackKingSide(true);
            } else if (move.isCastleQueenSide()){
                board.setCanCastleBlackQueenSide(true);
            }
        }

        if (move.isEnPassantMove()) {
            Rute enPassantRute;
            if (player.isWhiteSide()) {
                enPassantRute = board.getRute(endRute.getX(), endRute.getY() - 1);
            } else {
                enPassantRute = board.getRute(endRute.getX(), endRute.getY() + 1);
            }
            enPassantRute.setBrikke(dreptBrikke);
            endRute.setBrikke(null);
            board.setEnPassantRute(new Rute(endRute.getX(), endRute.getY(), null));
        }
        if (move.isFirstPawnMove()) {
            board.setEnPassantRute(null);
        }
    }

    /**
     * makeMove() for making a move
     * @param move the move to be made
     * @param player the player who makes the move
     * @return true if the move is valid, false if the move is invalid
     */
    
    public boolean makeMove(Move move, Player player) {

        board = game.getBoard();

        Brikke sourcePiece = move.getStart().getBrikke();
        if (sourcePiece == null) {
            return false;
        }
  
        //valid player
        if (player != game.getCurrentPlayer()) {
            return false;
        }
  
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }
        
        // valid move?
        if (!sourcePiece.isValidMove(board, move.getStart(), move.getEnd())) {
            return false;
        }

        // kill?
        Brikke destPiece = move.getEnd().getBrikke();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }

        if (sourcePiece instanceof Pawn) {
            int deltaX = Math.abs(move.getStart().getX() - move.getEnd().getX());
            int deltaY = Math.abs(move.getStart().getY() - move.getEnd().getY());
            if (deltaY == 2) {
                if (player.isWhiteSide()) {
                    board.setEnPassantRute(new Rute(move.getStart().getX(), move.getEnd().getY() - 1, null));
                } else {
                    board.setEnPassantRute(new Rute(move.getStart().getX(), move.getEnd().getY() + 1, null));
                }
                move.setFirstPawnMove(true);
            }
            if (deltaX == 1 && deltaY == 1 && destPiece == null) {
                Rute theEnPassantRute;
                if (sourcePiece.isWhite()) {
                    theEnPassantRute = board.getRute(move.getEnd().getX(), move.getEnd().getY() - 1);
                } else {
                    theEnPassantRute = board.getRute(move.getEnd().getX(), move.getEnd().getY() + 1);
                }
                Brikke enPassantPiece = theEnPassantRute.getBrikke();

                if (enPassantPiece != null && enPassantPiece instanceof Pawn && enPassantPiece.isWhite() != sourcePiece.isWhite()) {
                    enPassantPiece.setKilled(true);
                    move.setPieceKilled(destPiece);
                    theEnPassantRute.setBrikke(null);
                    move.setEnPassantMove(true);
                    board.setEnPassantRute(null);
                }
                
            }
        }


        boolean nocastling = false;
        if (game.isKingInCheck(player)) {
            nocastling = true;
        }

        Rute startRute = move.getStart();
        Rute endRute = move.getEnd();
        Brikke startBrikke = startRute.getBrikke();
        endRute.setBrikke(startBrikke);
        startRute.setBrikke(null);


        if (game.isKingInCheck(player)) {
            startRute.setBrikke(startBrikke);
            endRute.setBrikke(null);
            if (destPiece != null) {
                destPiece.setKilled(false);
                endRute.setBrikke(destPiece);
            }
            return false;
        }

        // castling?
        if (sourcePiece instanceof King && Math.abs(move.getStart().getX() - move.getEnd().getX()) == 2) {
            // determine which rook to move based on the direction of the castling move
            if (nocastling) {
                return false;
            }
            int rookStartX, rookEndX;
            if (move.getEnd().getX() > move.getStart().getX()) {
                // castling kingside
                rookStartX = 7;
                rookEndX = 5;
                
            } else {
                // castling queenside
                rookStartX = 0;
                rookEndX = 3;
                
            }
            Rute rookStart = board.getRute(rookStartX, move.getStart().getY());
            Rute rookEnd = board.getRute(rookEndX, move.getStart().getY());
            Brikke rook = rookStart.getBrikke();
            if (rook == null && !(rook instanceof Rook)) {
                // rook is not in the correct position for castling
                return false;
            }

            int deltaXRook = - (Math.abs(rookStart.getX() - rookEnd.getX())/ (rookStart.getX() - rookEnd.getX()));
            // check if the castling is actually in check
            for (int i = rookStartX + 1; i <= rookEndX + 1; i += deltaXRook) {
                Rute rute = board.getRute(i, rookStart.getY());
                if (game.isRuteTargeted(rute, player)) {
                    return false;
                }
            }

            if (rookStartX == 7) {
                if (player.isWhiteSide()) {
                    if (board.isCanCastleWhiteQueenSide()) {
                        board.setCanCastleWhiteQueenSide(false);
                        board.setCanCastleWhiteKingSide(false);
                        move.setBothCastleFalse(true);
                    } else {
                        board.setCanCastleWhiteKingSide(false);
                        move.setCastleKingSide(true);
                    }
                } else {
                    if (board.isCanCastleBlackQueenSide()) {
                        board.setCanCastleBlackQueenSide(false);
                        board.setCanCastleBlackKingSide(false);
                        move.setBothCastleFalse(true);
                    } else {
                        board.setCanCastleBlackKingSide(false);
                        move.setCastleKingSide(true);
                    }
                }
            } else {
                if (player.isWhiteSide()) {
                    if (board.isCanCastleWhiteKingSide()) {
                        board.setCanCastleWhiteQueenSide(false);
                        board.setCanCastleWhiteKingSide(false);
                        move.setBothCastleFalse(true);
                    } else {
                        board.setCanCastleWhiteQueenSide(false);
                        move.setCastleQueenSide(true);
                    }
                } else {
                    if (board.isCanCastleBlackKingSide()) {
                        board.setCanCastleBlackQueenSide(false);
                        board.setCanCastleBlackKingSide(false);
                        move.setBothCastleFalse(true);
                    } else {
                        board.setCanCastleBlackQueenSide(false);
                        move.setCastleQueenSide(true);
                    }
                }
            }

            // move the rook
            rookEnd.setBrikke(rookStart.getBrikke());
            rookStart.setBrikke(null);
            move.setCastlingMove(true);
            move.setRookCastled(rook);
            
        }

        // promotion?
        if (sourcePiece instanceof Pawn) {
            if ((!sourcePiece.isWhite() && endRute.getY() == 0) || (sourcePiece.isWhite() && endRute.getY() == 7)) {
                Brikke queen = new Queen(sourcePiece.isWhite());
                endRute.setBrikke(null);
                endRute.setBrikke(queen);
                move.setPawnPromotion(true);
            }

        } else if (sourcePiece instanceof King) {
            if (sourcePiece.isWhite()) {
                if (board.isCanCastleWhiteQueenSide() && board.isCanCastleWhiteKingSide()) {
                    board.setCanCastleWhiteQueenSide(false);
                    board.setCanCastleWhiteKingSide(false);
                    move.setBothCastleFalse(true);
                } else if (board.isCanCastleWhiteQueenSide()) {
                    board.setCanCastleWhiteQueenSide(false);
                    move.setCastleQueenSide(true);
                } else if (board.isCanCastleWhiteKingSide()) {
                    board.setCanCastleWhiteKingSide(false);
                    move.setCastleKingSide(true);
                }
            } else {
                if (board.isCanCastleBlackQueenSide() && board.isCanCastleBlackKingSide()) {
                    board.setCanCastleBlackQueenSide(false);
                    board.setCanCastleBlackKingSide(false);
                    move.setBothCastleFalse(true);
                } else if (board.isCanCastleBlackQueenSide()) {
                    board.setCanCastleBlackQueenSide(false);
                    move.setCastleQueenSide(true);
                } else if (board.isCanCastleBlackKingSide()) {
                    board.setCanCastleBlackKingSide(false);
                    move.setCastleKingSide(true);
                }
            }
        } else if (sourcePiece instanceof Rook) {
          
            if (move.getStart().getX() == 0) {
                if (board.isCanCastleWhiteQueenSide() && sourcePiece.isWhite()) {
                    board.setCanCastleWhiteQueenSide(false);
                    move.setCastleQueenSide(true);
                }
            } else if (move.getStart().getX() == 7 && sourcePiece.isWhite()) {
                if (board.isCanCastleWhiteKingSide()) {
                    board.setCanCastleWhiteKingSide(false);
                    move.setCastleKingSide(true);
                }
            } else if (move.getStart().getX() == 0 && !sourcePiece.isWhite()) {
                if (board.isCanCastleBlackQueenSide()) {
                    board.setCanCastleBlackQueenSide(false);
                    move.setCastleQueenSide(true);
                }
            } else if (move.getStart().getX() == 7 && !sourcePiece.isWhite()) {
                if (board.isCanCastleBlackKingSide()) {
                    board.setCanCastleBlackKingSide(false);
                    move.setCastleKingSide(true);
                }
            }
        }
        


        return true;
    }

    
}
