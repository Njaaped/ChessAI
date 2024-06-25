package no.uib.inf101.sem2.chess.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import no.uib.inf101.sem2.chess.game.Game;
import no.uib.inf101.sem2.chess.view.ChessView;
import no.uib.inf101.sem2.chess.model.Brikke;


import java.util.Timer;
import java.util.TimerTask;

import java.awt.MouseInfo;

/**
 * The controller class.
 * 
 * The controller class is the center of the game. It handles the mouse events and
 * the timer for repainting the board.
 * 
 * @author Njal.Pedersen
 *
 */

public class Controller implements MouseListener {

    private Game game;
    private ChessView view;
    private Timer timer;
    private Timer holdingTimer;
    private boolean humanTurn;
    private boolean isHoldingPiece;
    private long timeWait;
    private Brikke currentHoldingBrikke;


    public Controller(ChessView view, Game game) {
        this.view = view;
        this.game = game;
        this.timer = new Timer();
        this.holdingTimer = new Timer();
        this.humanTurn = true;
        this.isHoldingPiece = false;
        this.currentHoldingBrikke = null;

        /**
         * 
         * timer for checking if it is the computer's turn.
         * runs the doComputerMoves method in the game class.
         * repaints the board.
         * 
         */

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!humanTurn) {
                    game.doComputerMoves();
                    view.repaint();
                    humanTurn = true;
                } else {
                    view.repaint(); 
                }
                
            }
        },0, 30);

        

        /**
         * 
         * timer method for holding the piece.
         * gets the mouse position and sets the following brikke to the mouse position.
         * repaints the board.
         * 
         */

        this.holdingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isHoldingPiece) {
                    int xpos = MouseInfo.getPointerInfo().getLocation().x;
                    int ypos = MouseInfo.getPointerInfo().getLocation().y;
                    game.setFollowingBrikkeXAndY(xpos, ypos);
                    game.setFollowingBrikke(currentHoldingBrikke);
                    view.repaint();
                }
            }
        }, 0, 16);

        
        this.view.addMouseListener(this);
        this.view.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.setTimeWait(System.currentTimeMillis());

        int x = e.getX();
        int y = e.getY();

        CellPixelToPositionConverter converter = new CellPixelToPositionConverter(x, y, game.isHumanPlayerWhite(), view);

        int x1 = converter.getTheCellPosition()[0];
        int y1 = converter.getTheCellPosition()[1];


        if (humanTurn) {
            if (game.getPressedRute().getX() == -10) {
                if (game.makeHumanMove(x1, y1)) {
                    humanTurn = false;
                    view.repaint();
                    
                } else {
                    
                    this.isHoldingPiece = true;
                    this.currentHoldingBrikke = game.getBoard().getRute(x1, y1).getBrikke();
                }
            } else {
                game.setPressedRute(x1, y1);
                this.isHoldingPiece = true;
                this.currentHoldingBrikke = game.getBoard().getRute(x1, y1).getBrikke();
                view.repaint();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (System.currentTimeMillis() - timeWait > 200) {

            this.isHoldingPiece = false;
            this.currentHoldingBrikke = null;
            game.setFollowingBrikke(currentHoldingBrikke);
            int x = e.getX();
            int y = e.getY();

            CellPixelToPositionConverter converter = new CellPixelToPositionConverter(x, y, game.isHumanPlayerWhite(), view);

            int x1 = converter.getTheCellPosition()[0];
            int y1 = converter.getTheCellPosition()[1];


            if (humanTurn) {
                if (game.makeHumanMove(x1, y1)) {
                    humanTurn = false;
                    view.repaint();
                    
                } else {
                    view.repaint();
                }
        }

    } else {
        this.isHoldingPiece = false;
        this.currentHoldingBrikke = null;
        game.setFollowingBrikke(currentHoldingBrikke);
    }


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    public void setHumanTurn(boolean humanTurn) {
        this.humanTurn = humanTurn;
    }

    public long getTimeWait() {
        return timeWait;
    }

    public void setTimeWait(long timeWait) {
        this.timeWait = timeWait;
    }

    
}
