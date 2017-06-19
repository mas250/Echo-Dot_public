
package echodot.gui;

import echodot.mode.StateChangedListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author 65046219
 */
public class MainWindow extends JPanel implements ActionListener{
    private static final String APP_NAME = "Echo Dot";
    private static final Color  OFF_COLOR = Color.BLACK;
    
    private Color   light;
    private JButton onOffButton;
    StateChangedListener stateChangedListener;
    
    public MainWindow(StateChangedListener stateChangedListener){
        light = OFF_COLOR;
        onOffButton = new JButton();
        onOffButton.setBorderPainted(false);
       try {
           Image img = ImageIO.read(getClass().getResource("power_button.bmp"));
           onOffButton.setIcon(new ImageIcon(img)); 
       }
       catch (Exception ex){
           System.out.println(ex);
           
         
       }
        setLayout(null);
        onOffButton.setBounds(150,460,100,100);
        add(onOffButton);
        
        onOffButton.addActionListener(this);
        setBackground(light);
        this.stateChangedListener = stateChangedListener;
        
        
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if(source == onOffButton){
            if(light.equals(OFF_COLOR)){
                light = Color.CYAN;
                try {
           Image img = ImageIO.read(getClass().getResource("power_button2.bmp"));
           onOffButton.setIcon(new ImageIcon(img)); 
       }
       catch (Exception ex){
           System.out.println(ex);
           
       }
                
                
            }
            else{
                light = OFF_COLOR;
                try {
           Image img = ImageIO.read(getClass().getResource("power_button.bmp"));
           onOffButton.setIcon(new ImageIcon(img)); 
       }
       catch (Exception ex){
           System.out.println(ex);
            }
        }
        }
        
        setBackground(light);
        stateChangedListener.onStateChanged(new EchoTurnedOnEvent(this));
    }
    
    public void setupUi() {
        JFrame frame = new JFrame(APP_NAME);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        
        class MyCanvas extends JComponent {

  public void paint(Graphics g) {
    g.drawRect (10, 10, 200, 200);  
  }
}
        

        frame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            System.exit(0);
          }
        });

        Container contentPane = frame.getContentPane();
        contentPane.add(new MainWindow(stateChangedListener) );

        frame.setVisible(true);
    }


}

