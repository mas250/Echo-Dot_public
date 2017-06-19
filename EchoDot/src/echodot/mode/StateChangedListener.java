
package echodot.mode;

import echodot.gui.EchoTurnedOnEvent;

/**
 *
 * @author 650016706
 */
public class StateChangedListener {
    ModeManager modeManager;
    
    public StateChangedListener(ModeManager modeManager) {
        this.modeManager = modeManager;
    }
    
    public void onStateChanged(EchoTurnedOnEvent e){
        if(modeManager.isOn()) {
            modeManager.switchOFF();
        } else {
            modeManager.switchON();
        }
    }
}
