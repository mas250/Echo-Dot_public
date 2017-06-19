package echodot;

import echodot.gui.UIManager;
import echodot.mode.ModeManager;
import echodot.mode.StateChangedListener;

/**
 * This is the main class which instantiates the UI and Mode Managers.
 * @author 650016706
 */
public class EchoDot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModeManager modeManager = new ModeManager();
        StateChangedListener listener = new StateChangedListener(modeManager);
        UIManager uiManager = UIManager.getInstance(listener);
    }
    
}
