
package no.uib.inf101.sem2.chess.game.sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class that plays the sound.
 * use playSound(int sound) to play a sound.
 * 
 * @author Njal.Pedersen
 */

public class PlayTheSound {

    private Thread soundThread;

    // 1 = capture
    // 2 = move
    // 3 = check
    // 4 = castling
    // 5 = resign
    // 6 = checkmate
    // 7 = stalemate

    
    public void playSound(int sound) {

        soundThread = new Thread(new Runnable() {
            public void run() {
                try {
                    if (sound == 1 || sound == 2 || sound == 3 || sound == 4 || sound == 5) {
                        File file = playOneSound(sound);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                        // Get the audio clip from the audio input stream
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);

                        // Start playing the sound
                        clip.start();
                    } else if (sound == 6 || sound == 7) {
                        File[] file = playDoubleSound(sound);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file[0]);
                        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file[1]);
                        // Get the audio clip from the audio input stream
                        Clip clip = AudioSystem.getClip();
                        Clip clip2 = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip2.open(audioInputStream2);
                        clip.start();
                        clip2.start();



                    }
                    
                } catch (Exception ex) {
                    System.out.println("Error playing sound: " + ex.getMessage());
                }
            }


        });

        soundThread.start();
    }


    private File playOneSound(int sound) {

        String filePath = "";
        
        if (sound == 1) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/capture.wav";
        } else if (sound == 2) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/move.wav";
        } else if (sound == 3) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/check.wav";
        } else if (sound == 4) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/castling.wav";
        } else if (sound == 5) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/resign.wav";
        }

        File file = new File(filePath);
        return file; 
    }

    private File[] playDoubleSound(int sound) {
        String filePath = "";
        String filePath2 = "";

        if (sound == 6) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/check.wav";
            filePath2 = "src/main/java/no/uib/inf101/sem2/chess/game/sound/resign.wav";
        } else if (sound == 7) {
            filePath = "src/main/java/no/uib/inf101/sem2/chess/game/sound/move.wav";
            filePath2 = "src/main/java/no/uib/inf101/sem2/chess/game/sound/resign.wav";
        }

        File[] file = new File[2];



        file[0] = new File(filePath);
        file[1] = new File(filePath2);

        return file;

    }

    public boolean isRunning() {
        if (this.soundThread == null) {
            return false;
        } else {
            return this.soundThread.isAlive();
        }
        
    }
}

