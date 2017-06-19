package echodot.gui;
        
public class Light {
    LightStatus status;
    
    public Light(){
        status = LightStatus.OFF;
    }
    
    public void switchLights(LightStatus status){
        this.status = status;
    }
}