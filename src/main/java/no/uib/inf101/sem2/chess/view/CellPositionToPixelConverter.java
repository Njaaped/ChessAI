package no.uib.inf101.sem2.chess.view;

import java.awt.geom.Rectangle2D;


/**
 * A utility class for converting cell positions in a grid to their corresponding
 * pixel positions in a rectangle.
 */

public class CellPositionToPixelConverter {
    private Rectangle2D box;
    private double margin;


    public CellPositionToPixelConverter(Rectangle2D box, double margin) {
        this.box = box;
        this.margin = margin;
    }

    public Rectangle2D getBoundsForCell (int x1, int y1) {
        double cellWidth = (box.getWidth() - (8 + 1) * margin) / 8;
        double cellHeight = (box.getHeight() - (8 + 1) * margin) / 8;
        double x = box.getX() + margin + x1 * (margin + cellWidth);
        double y = box.getY() + margin + y1 * (margin + cellHeight);
        return new Rectangle2D.Double(x, y, cellWidth, cellHeight);
    }

}
