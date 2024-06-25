package no.uib.inf101.sem2.chess.view;

import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.game.Game;
import no.uib.inf101.sem2.chess.game.GameStatus;
import no.uib.inf101.sem2.chess.ruter.Rute;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.List;


import javax.swing.JPanel;

/**
* ChessView class creates a view of the chess board and the pieces on it.
* @author Njal.Pedersen
*/


public class ChessView extends JPanel {
    private Board board;
    private Game game;
    private PressedRute pressedRute;
    private JButton newGameButton;
    private JButton viewButton;
    private JButton resignButton;
    private JButton newGame;
    private JButton changeSide;
    private JComboBox<String> depthBox;
    private DefaultColorTheme theme;
    private int depth = 4;
    
    

    private final int BREDDE = 800;
    private final int HØYDE = 800;

    private final int BOARDSIZE = 500;

    private final int UPPERMARGIN = (HØYDE - BOARDSIZE) / 2 - 100;
    private final int LEFTMARGIN = (BREDDE - BOARDSIZE) / 2 - 100;

    /**
    * ChessView constructor creates a view of the chess board and the pieces on it.
    */

    public ChessView(Game game) {
        this.game = game;
        this.board = game.getBoard();
        this.pressedRute = game.getPressedRute();
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(BREDDE, HØYDE));
        this.theme = new ColorTheme();
        this.viewButton = new JButton("View");
        this.viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theme.setColorTheme((theme.getColorTheme() + 1) % 5);
                repaint();
            }
        });

        this.resignButton = new JButton("Resign");
        resignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resign();
                repaint();
            }
        });
        resignButton.setBounds(325, 200, 150, 100);

        this.newGameButton = new JButton("PAUSE");
        this.newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getStatus() == GameStatus.ACTIVE) {
                    game.setStatus(GameStatus.SETTINGS);
                } else if (game.getStatus() == GameStatus.SETTINGS) {
                    game.setStatus(GameStatus.ACTIVE);
                    
                }
                repaint();
            }
        });

        this.newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.newGame(depth);
                repaint();
            }
        });
        newGame.setBounds(330, 450, 100, 75);

        this.changeSide = new JButton("Change Side");
        changeSide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.changeSide();
                repaint();
            }
        });
        changeSide.setBounds(330, 550, 100, 75);

        this.depthBox = new JComboBox<String>(new String[] {"0","1","2","3", "4", "5", "6"});
        depthBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                depth = cb.getSelectedIndex();
                repaint();
            }
        });
        depthBox.setBounds(330, 650, 100, 75);
        depthBox.setForeground(Color.BLACK);
        depthBox.setBackground(Color.WHITE);
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard((Graphics2D) g);  
    }

    /**
    * drawBoard method draws the board and the pieces on it.
    * methods drawCells, drawCheckmate and drawSettings are called from here.
    * checks if there is a piece being dragged and draws it on the board.
    * @param g
    */

    private void drawBoard(Graphics2D g) {
        viewButton.setBounds(LEFTMARGIN + 100, UPPERMARGIN - 50, 90, 50);
        this.add(viewButton);
        newGameButton.setBounds(LEFTMARGIN, UPPERMARGIN - 50, 90, 50);
        this.add(newGameButton);
        Rectangle2D box = new Rectangle2D.Double(LEFTMARGIN, UPPERMARGIN, getWidth() - 2 * LEFTMARGIN, getHeight() - 2 * UPPERMARGIN);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box, 1);
        drawCells(g, board, converter, pressedRute, game.getPossibleMoves(), theme, game.isHumanPlayerWhite());
        this.remove(resignButton);
        this.remove(newGame);
        this.remove(changeSide);
        this.remove(depthBox);
        if (game.getStatus() == GameStatus.BLACK_WIN || game.getStatus() == GameStatus.WHITE_WIN) {
            drawCheckmate(g, game.getStatus());
        } else if (game.getStatus() == GameStatus.SETTINGS) {
            this.add(resignButton);
            drawSettings(g);
        }
        if (game.getFollowingBrikke().getBrikke() != null) {
            Image img = Toolkit.getDefaultToolkit().getImage("src/main/java/no/uib/inf101/sem2/chess/view/brikkebilder/" + game.getFollowingBrikke().getBrikke().getName() + ".png");
            g.drawImage(img, (int) game.getFollowingBrikke().getX() - 50, (int) game.getFollowingBrikke().getY() - 100, 90, 90, this);

        }
        
    }

    /**
     * drawCells method draws the chess squares on the board.
     * @param g
     * @param board iterates through the board to find the right piece.
     * @param converter converts the cell position to pixel position.
     * @param pressedRute is a cell position if pressedRute exists, else it is -10 and 10.
     * @param possibleMoves is a list of possible moves for the piece.
     * @param theme is the color theme.
     * @param isWhite is true if the human player is white.
     */

    private void drawCells(Graphics2D g, Board board, CellPositionToPixelConverter converter, PressedRute pressedRute, List<Rute> possibleMoves, DefaultColorTheme theme, boolean isWhite) {
        Color[] colors = theme.theColors();
        Color color1 = colors[0];
        Color color2 = colors[1];
        Color pressedColor = colors[2];
        Color possibleColor = colors[3];
        Rectangle2D cell;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (isWhite) {
                    cell = converter.getBoundsForCell(x,7 - y);
                } else {
                    cell = converter.getBoundsForCell(x,y);
                }
                
                if (pressedRute.getX() == x && pressedRute.getY() == y) {
                    g.setColor(pressedColor);
                } else if ((x + y) % 2 == 0) {
                    g.setColor(color1);
                } else {
                    g.setColor(color2);
                }
                g.fill(cell);

                if (board.getRute(x, y).getBrikke() != null) {
                    Image img = Toolkit.getDefaultToolkit().getImage("src/main/java/no/uib/inf101/sem2/chess/view/brikkebilder/" + board.getRute(x, y).getBrikke().getName() + ".png");
                    g.drawImage(img, (int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight(), this);
                }
            }
        }
        for (Rute rute : possibleMoves) {
            Rectangle2D cell2;
            if (isWhite) {
                cell2 = converter.getBoundsForCell(rute.getX(), 7 - rute.getY());
            } else {
                cell2 = converter.getBoundsForCell(rute.getX(), rute.getY());
            }
            g.setColor(possibleColor);
            g.fill(cell2);
        }
    }

    /**
     * Draws the checkmate screen.
     * @param g
     * @param status
     */

    private void drawCheckmate(Graphics2D g, GameStatus status) {
        Rectangle2D box = new Rectangle2D.Double(LEFTMARGIN, UPPERMARGIN, getWidth() - 2 * LEFTMARGIN, getHeight() - 2 * UPPERMARGIN);
        g.setColor(new Color(128,128,128,128));
        g.fill(box);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        if (status == GameStatus.BLACK_WIN) {
            drawCenteredString(g, "BLACK WINS", box.getBounds2D().getCenterX(), box.getBounds2D().getCenterY());
            this.add(newGame);
            this.add(changeSide);
            this.add(depthBox);
        } else if (status == GameStatus.WHITE_WIN) {
            drawCenteredString(g, "WHITE WINS", box.getBounds2D().getCenterX(), box.getBounds2D().getCenterY());
            this.add(newGame);
            this.add(changeSide);
            this.add(depthBox);
        }
    }

    /**
     * method to draw a centered string around x and y.
     * @param g
     * @param s
     * @param x
     * @param y
     */

    public void drawCenteredString(Graphics2D g, String s, double x, double y) {
        FontMetrics metrics = g.getFontMetrics();
        double txtX = x - ((double) metrics.stringWidth(s))/2;
        double txtY = y - ((double) metrics.getHeight())/2 + metrics.getAscent();
        g.drawString(s, (int) Math.round(txtX), (int) Math.round(txtY));
    }

    /**
     * Draws the settings screen.
     * @param g
     */

    public void drawSettings(Graphics2D g) {
        Rectangle2D box = new Rectangle2D.Double(LEFTMARGIN, UPPERMARGIN, getWidth() - 2 * LEFTMARGIN, getHeight() - 2 * UPPERMARGIN);
        g.setColor(new Color(128,128,128,220));
        g.fill(box);
    
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        drawCenteredString(g, "PAUSE", box.getBounds2D().getCenterX(), 100);
    }

    public int getBREDDE() {
        return this.getWidth();
    }

    public int getHØYDE() {
        return this.getHeight();
    }
}
