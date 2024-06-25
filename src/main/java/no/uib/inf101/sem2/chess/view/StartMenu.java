
package no.uib.inf101.sem2.chess.view;


import javax.swing.JFrame;


/**
* StartMenu class
* creates a startMenu for the chess game where you can choose to play as white or black.
* @author Njal.Pedersen
*/



public class StartMenu extends JFrame {


    public static final String WINDOW_TITLE = "Njål's sjakk";

    /**
    * startMenu constructor
    * creates a startMenu for the chess game where you can choose to play as white or black.
    */

    public StartMenu() {
        super("Choose your side");
        
        StartMenuView panel = new StartMenuView();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
    }

    

    
    
}


