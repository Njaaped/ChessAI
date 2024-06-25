package no.uib.inf101.sem2.chess.game;

import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.view.PressedRute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.game.sound.PlayTheSound;
import no.uib.inf101.sem2.chess.view.FollowingBrikke;
import no.uib.inf101.sem2.chess.engine.BestMoveProducer;
import no.uib.inf101.sem2.chess.engine.GetTheMoves;
import no.uib.inf101.sem2.chess.board.Board;

import java.util.List;
import java.util.ArrayList;

/**
 * The game class.
 * 
 * The main class of the chess game. It's the center of the game
 * 
 * @author Njal.Pedersen
 *
 */

public class Game {
    private Player[] players;
    private Board board;
    private Player currentPlayer;
    private PressedRute pressedRute;
    private GameStatus gameStatus;
    private List<Rute> possibleMoves;
    private FollowingBrikke followingBrikke;
    private PlayTheSound playTheSound;
    private BestMoveProducer bestMoveProducer;
    private Moving moving;
    private GetTheMoves getTheMoves;


    public Game(Player p1, Player p2, int depth) {
        this.initialize(p1, p2, depth);
    }

    /**
     * Initializes the game.
     * @param p1
     * @param p2
     * @param depth
     */

    private void initialize(Player p1, Player p2, int depth) {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;
        this.pressedRute = new PressedRute(-10, -10);
        this.followingBrikke = new FollowingBrikke(-100, -100, null);
        this.possibleMoves = new ArrayList<Rute>();
        this.gameStatus = GameStatus.ACTIVE;
        this.playTheSound = new PlayTheSound();
        this.moving = new Moving(this);
        this.getTheMoves = new GetTheMoves(this);
        this.bestMoveProducer = new BestMoveProducer(depth, this, moving);
      

        if (p1.isWhiteSide()) {
            this.currentPlayer = players[0];
        } else {
            this.currentPlayer = players[1];
            this.doComputerMoves();
        }
    }

    /**
     * 
     * @return the initialized moving class object
     */

    public Moving getMoving() {
        return this.moving;
    }

    /**
     * 
     * @return the initialized GetTheMoves class object
     */

    public GetTheMoves getGetTheMoves() {
        return this.getTheMoves;
    }

    /**
     * 
     * @return the game status
     */

    public GameStatus getStatus() {
        return this.gameStatus;
    }

    /**
     * sets the pressed Rute
     * @param x
     * @param y
     */

    public void setPressedRute(int x, int y) {
        this.pressedRute.setX(x);
        this.pressedRute.setY(y);
        this.possibleMoves = this.getTheMoves.getLegalMoves(this.currentPlayer, this.board.getRute(x, y));
    }

    /**
     * sets the status of the game
     * @param status
     */

    public void setStatus(GameStatus status) {
        this.gameStatus = status;
    }

    /**
     * 
     * @return true if the human player is white
     */

    public boolean isHumanPlayerWhite() {
        return this.players[0].isWhiteSide();
    }

    /**
     * sets the brikke that is dragged by the player
     * @param brikke
     */

    public void setFollowingBrikke(Brikke brikke) {
        this.followingBrikke.setBrikke(brikke);
    }

    /**
     * sets the x and y coordinates of the brikke that is dragged by the player
     * @param x
     * @param y
     */

    public void setFollowingBrikkeXAndY(int x, int y) {
        this.followingBrikke.setX(x);
        this.followingBrikke.setY(y);
    }

    /**
     * 
     * @return the brikke that is dragged by the player and it's position
     */

    public FollowingBrikke getFollowingBrikke() {
        return this.followingBrikke;
    }

    /**
     * Does the move for the computer
     */

    public void doComputerMoves() {
        Move theBestAssMove;
        if (this.currentPlayer.isWhiteSide()) {
            theBestAssMove = bestMoveProducer.findBestMove(false);
        } else {
            theBestAssMove = bestMoveProducer.findBestMove(true);
        }
        
        if (moving.makeMove(theBestAssMove, currentPlayer)) {
            changePlayer();
            if (this.isKingInCheck(currentPlayer)) {
                playTheSound.playSound(3);
                if (this.isKingInCheckMate(currentPlayer)) {
                    checkMated();
                }
            } else {
                if (theBestAssMove.getPieceKilled() != null) {
                    playTheSound.playSound(1);
                } else if (theBestAssMove.isCastlingMove()) {
                    playTheSound.playSound(4);
                } else {
                    playTheSound.playSound(2);
                }
            }
            
        } 
    }

    /**
     * does the move for the human player
     * @param x
     * @param y
     * @return true if the move was valid
     */

    public boolean makeHumanMove(int x, int y) {
        if (pressedRute.getX() == -10 && pressedRute.getY() == -10) {
            pressedRute.setX(x);
            pressedRute.setY(y);
            possibleMoves = getTheMoves.getLegalMoves(currentPlayer, board.getRute(x, y));
            return false;
        } else {
            Move currentPlayerMove = new Move(currentPlayer, board.getRute(pressedRute.getX(), pressedRute.getY()), board.getRute(x, y));
            if (moving.makeMove(currentPlayerMove, currentPlayer)){
                changePlayer();
                if (this.isKingInCheck(currentPlayer)) {
                    playTheSound.playSound(3);
                    if (this.isKingInCheckMate(currentPlayer)) {
                        checkMated();
                    }
                } else {
                    if (currentPlayerMove.getPieceKilled() != null) {
                        playTheSound.playSound(1);
                    } else if (currentPlayerMove.isCastlingMove()) {
                        playTheSound.playSound(4);
                    } else {
                        playTheSound.playSound(2);
                    }
                }

                possibleMoves.clear();
                pressedRute.setX(-10);
                pressedRute.setY(-10);

                return true;
            } else {
                possibleMoves.clear();
                pressedRute.setX(-10);
                pressedRute.setY(-10);
                return false;
            }

        }
    }

    /**
     * call this method if you want a new game
     */

    public void newGame(int d) {
        this.board.resetBoard();
        this.gameStatus = GameStatus.ACTIVE;
        this.currentPlayer = this.players[0].isWhiteSide() ? this.players[0] : this.players[1];
        if (this.currentPlayer != this.players[0]) {
            this.doComputerMoves();
        }
        this.possibleMoves.clear();
        this.pressedRute.setX(-10);
        this.pressedRute.setY(-10);
        this.followingBrikke.setBrikke(null);
        this.followingBrikke.setX(-10);
        this.followingBrikke.setY(-10);
        this.bestMoveProducer = new BestMoveProducer(d, this, moving);

    }

    /**
     * call this method to change the players playing side
     */

     public void changeSide() {
        if (this.players[0].isWhiteSide()) {
            this.players[0].setWhiteSide(false);
            this.players[1].setWhiteSide(true);
            
        } else {
            this.players[0].setWhiteSide(true);
            this.players[1].setWhiteSide(false);
        }
     }

    /**
     * call this method if the human player resigns
     */

     public void resign() {
        if (this.players[0].isWhiteSide()) {
            this.gameStatus = GameStatus.BLACK_WIN;
            System.out.println("Black wins!");
        } else {
            this.gameStatus = GameStatus.WHITE_WIN;
            System.out.println("White wins!");
        }
     }


    /**
     * the checkmate method
     */

    public void checkMated() {
        playTheSound.playSound(6);
        if (this.currentPlayer.isWhiteSide()) {
            this.gameStatus = GameStatus.BLACK_WIN;
            System.out.println("Black wins!");
        } else {
            this.gameStatus = GameStatus.WHITE_WIN;
            System.out.println("White wins!");
        }
    }

    /**
     * changes the player turn
     */

    public void changePlayer() {
        // set the current turn to the other player
        if (this.currentPlayer == players[0]) {
            this.currentPlayer = players[1];
        }
        else {
            this.currentPlayer = players[0];
        }
    }

    /**
     * 
     * @param player
     * @return the opponent of the player
     */

    public Player getOpponent(Player player) {
        if (player == players[0]) {
            return players[1];
        }
        else {
            return players[0];
        }
    }

    /**
     * 
     * @return a List of all the possible moves
     */

    public List<Rute> getPossibleMoves() {
        return this.possibleMoves;
    }

    /**
     * 
     * @return the pressed rute
     */

    public PressedRute getPressedRute() {
        return this.pressedRute;
    }

    /**
     * 
     * @return the board
     */

    public Board getBoard() {
        return this.board;
    }

    /**
     * sets the board
     * @param board
     */

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * 
     * @return the current player
     */

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * iterates through the board and checks if the rute is targeted by the opponent
     * @param rute
     * @param player
     * @return true if the rute is targeted
     */

    public boolean isRuteTargeted(Rute rute, Player player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute r = board.getRute(i, j);
                Brikke piece = r.getBrikke();
                if (piece != null && (piece.isWhite() != player.isWhiteSide())) {
                    if (piece.isValidMove(board, r, rute)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if the king is in check
     * @param player
     * @return true if the king is in check
     */

    public boolean isKingInCheck(Player player) {
        Rute kingRute = board.getKingRute(player.isWhiteSide());
        if (kingRute == null) {
            return false;
        }
        if (isRuteTargeted(kingRute, player)) {
            return true;
        }
        return false;
    }

    /**
     * checks if the king is in checkmate
     * @param player
     * @return true if the king is in checkmate
     */

    public boolean isKingInCheckMate(Player player) {
        if (!isKingInCheck(player)) {
            return false;
        }
        if (getTheMoves.getAllMoves(currentPlayer).size() > 0) {
            return false;
        }
        return true;
    }

}

