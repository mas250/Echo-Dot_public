package echodot.gui;

import echodot.mode.StateChangedListener;

/**
 *
 * @author 650016706, 650046219
 */
public class UIManager {
    private MainWindow mainWindow;
    private static UIManager instance;
    
    private UIManager(StateChangedListener stateChangedListener){
        mainWindow = new MainWindow(stateChangedListener);
        mainWindow.setupUi();
    }
    
    public static UIManager getInstance(StateChangedListener stateChangedListener){
        if(instance == null){
            instance = new UIManager(stateChangedListener);
        }
        
        return instance;
    }
}
