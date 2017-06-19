package echodot.mode;

import echodot.mode.answer.AnswerModeManager;
import echodot.mode.listen.ListenModeManager;

/**
 * Class that manages the states of the application.
 * @author 650023085
 */
public class ModeManager{
    private volatile Mode mode;
    private ListenModeManager listenModeManager;
    private AnswerModeManager answerModeManager;
    
    public ModeManager() {
        mode = Mode.OFF;
        listenModeManager = new ListenModeManager();
        answerModeManager = new AnswerModeManager();
    }
    /**
     * Method to start the Thread.
     */
    public void switchON() {
        mode = Mode.ON;
        
        answerModeManager.answer(listenModeManager.listen());
    }
    
    /**
     * Method to turn off the ModeManager by setting up a flag.
     */
    public void switchOFF() {
        mode = Mode.OFF;
    }
    
    public boolean isOn() {
        return mode.equals(Mode.ON);
    }
}
