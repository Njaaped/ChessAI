package no.uib.inf101.sem2.chess.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import no.uib.inf101.sem2.chess.controller.Controller;
import no.uib.inf101.sem2.chess.game.ComputerPlayer;
import no.uib.inf101.sem2.chess.game.Game;
import no.uib.inf101.sem2.chess.game.HumanPlayer;
import no.uib.inf101.sem2.chess.game.Player;


public class StartMenuView extends JPanel implements ActionListener {

    private JButton whiteButton, blackButton, zero, one, two, three, four, five, six;
    private int depth;
    private int margin, yMargin, buttonHeight, buttonWidth;

    public static final String WINDOW_TITLE = "Njål's sjakk";

    /**
     * startMenu constructor
     * creates a startMenu for the chess game where you can choose to play as white or black.
     */

     public StartMenuView() {
        this.depth = 4;
        this.setPreferredSize(new Dimension(800, 800));
        whiteButton = new JButton("White Player");
        blackButton = new JButton("Black Player");
    
        zero = new JButton("Ultra Ultra Noob");
        one = new JButton("Ultra Noob");
        two = new JButton("Noob");
        three = new JButton("Aight");
        four = new JButton("Intermediate");
        five = new JButton("Insane");
        six = new JButton("Impossible");
    
        try {
            Image img = Toolkit.getDefaultToolkit().getImage("src/main/java/no/uib/inf101/sem2/chess/view/brikkebilder/whitequeen.png");
            Image img2 = Toolkit.getDefaultToolkit().getImage("src/main/java/no/uib/inf101/sem2/chess/view/brikkebilder/blackqueen.png");
            whiteButton.setIcon(new ImageIcon(img));
            blackButton.setIcon(new ImageIcon(img2));
        } catch (Exception e) {
            System.out.println("Error loading image");
        }
    
        whiteButton.addActionListener(this);
        blackButton.addActionListener(this);
        zero.addActionListener(this);
        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);

        this.setLayout(null);
    
        buttonWidth = 200;
        buttonHeight = 75;
        yMargin = 75;
        margin = (800 - buttonWidth) / 2;
        whiteButton.setBounds(margin, yMargin, buttonWidth, buttonHeight);
        blackButton.setBounds(margin, 75 + yMargin, buttonWidth, buttonHeight);
        zero.setBounds(margin, 200 + yMargin, buttonWidth, buttonHeight);
        one.setBounds(margin, 275 + yMargin, buttonWidth, buttonHeight);
        two.setBounds(margin, 350 + yMargin, buttonWidth, buttonHeight);
        three.setBounds(margin, 425 + yMargin, buttonWidth, buttonHeight);
        four.setBounds(margin, 500 + yMargin, buttonWidth, buttonHeight);
        five.setBounds(margin, 575 + yMargin, buttonWidth, buttonHeight);
        six.setBounds(margin, 650 + yMargin, buttonWidth, buttonHeight);

    
        add(whiteButton);
        add(blackButton);
        add(zero);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == whiteButton) {
            startGame(true);
            setVisible(false);
        } else if (e.getSource() == blackButton) {
            startGame(false);
            setVisible(false);
        } else if (e.getSource() == zero) {
            depth = 0;
        } else if (e.getSource() == one) {
            depth = 1;
        } else if (e.getSource() == two) {
            depth = 2;
        } else if (e.getSource() == three) {
            depth = 3;
        } else if (e.getSource() == four) {
            depth = 4;
        } else if (e.getSource() == five) {
            depth = 5;
        } else if (e.getSource() == six) {
            depth = 6;
      
        }
        repaint();
    }
    /**
     * Starts a new game.
     * @param isWhite true if the human player is white, false otherwise
     */

    private void startGame(boolean isWhite) {
        Player human = new HumanPlayer(isWhite);
        Player computer = new ComputerPlayer(!isWhite);
        Game game = new Game(human, computer, depth);
        ChessView view = new ChessView(game);
        Controller controller = new Controller(view, game);
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(new ImageIcon("src/main/java/no/uib/inf101/sem2/chess/view/background.jpeg").getImage(), 0, 0, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (800 - fm.stringWidth("Choose your side")) / 2;
        g2d.drawString("Choose your side", x, 50);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        fm = g2d.getFontMetrics();
        x = (800 - fm.stringWidth("Choose your difficulty")) / 2;
        g2d.drawString("Choose your difficulty", x, 260);
        g2d.setColor(Color.GREEN.brighter());
        Rectangle2D rect = new Rectangle2D.Double(margin, 200 + yMargin + 75 * depth, buttonWidth, buttonHeight);
        int thickness = 2;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(rect);
        g2d.setStroke(oldStroke);
        

    }


    
    
    
}
