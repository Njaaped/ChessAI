package no.uib.inf101.sem2.chess.controller;

import java.awt.geom.Rectangle2D;


import no.uib.inf101.sem2.chess.view.ChessView;

/**
 * 
 * Class for converting the pixel coordinates of the mouse to the cell coordinates.
 * 
 * @author Njal.Pedersen
 *
 */

public class CellPixelToPositionConverter {

    private int bredde = 800;
    private int høyde = 800;

    private int boardsize = 500;

    private int uppermargin = (høyde - boardsize) / 2 - 100;
    private int leftmargin = (bredde - boardsize) / 2 - 100;

    private Rectangle2D box;
    private int margin = 1;
    private int x;
    private int y;
    private boolean isWhite;
    private ChessView view;

    public CellPixelToPositionConverter(int x, int y, boolean isWhite, ChessView view) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.view = view;
    }

    /**
     * 
     * Method for converting the pixel coordinates of the mouse to the cell coordinates.
     * 
     * @return the cell coordinates.
     */

    public int[] getTheCellPosition() {

        box = new Rectangle2D.Double(leftmargin, uppermargin, view.getBREDDE() - 2 * leftmargin, view.getHØYDE() - 2 * uppermargin);
        double cellWidth = (box.getWidth() - (9) * margin) / 8;
        double cellHeight = (box.getHeight() - (9) * margin) / 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double x1 = box.getX() + margin + i * (margin + cellWidth);
                double y1 = box.getY() + margin + j * (margin + cellHeight);
                if (x > x1 && x < x1 + cellWidth && y > y1 && y < y1 + cellHeight) {
                    if (isWhite) {
                        return new int[] {i, 7 - j};
                    } else {
                        return new int[] {i, j};
                    }
                }
            }
        }
        return null;
    }


}
