package echodot.mode.listen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that manages the listen mode of the application
 * @author 650023085
 */
public class ListenModeManager {
    private int TIMER = 5;      // seconds
    
    private Microphone microphone;
    
    /**
     * Public constructor that takes no arguments.
     */
    public ListenModeManager() {
        microphone = Microphone.getInstance();
    }
    
    /**
     * Method that records for TIMER seconds and converts it into String.
     * @return 
     */
    public synchronized String listen() {
        final String soundFile = microphone.getSound(TIMER);
        try {
            return SpeechToText.convert(Files.readAllBytes(Paths.get(soundFile)), SpeechToText.getToken());
        } catch (IOException ex) {
            Logger.getLogger(ListenModeManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
