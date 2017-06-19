
package echodot.mode.answer;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author 650016706
 */
public class Speaker {
   private static Speaker instance;
   
   private void Speaker(){}
   
   /**
    * 
    * @return instance - an instance of a speaker
    */
   public static Speaker getInstance(){
       if(instance == null){
           synchronized(Speaker.class){
               if(instance == null){
                   return new Speaker();
               }
           }
       }
       
       return instance;
   }
   
   /**
    * Plays the contents of a wav file from the recordings directory.
    * 
    * @param filePath the file which contains the sound to be played
    */
   public synchronized void playSound(final String filePath) {
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("recordings/" + filePath));
            AudioFormat format = stream.getFormat();
            SourceDataLine auline = null;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            
            if (auline.isControlSupported(FloatControl.Type.MASTER_GAIN)){
                FloatControl volume = (FloatControl) auline.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(6.0F);
            }
            auline.start();
            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            byte [] buffer = new byte[ bufferSize ];

            try {
               int bytesRead = 0;
               while ( bytesRead >= 0 ) {
                  bytesRead = stream.read( buffer, 0, buffer.length );
                  if ( bytesRead >= 0 ) {
                     int framesWritten = auline.write( buffer, 0, bytesRead );
                  }
               }
            } catch ( IOException e ) {
               System.out.println(e);
               System.exit(1);
            }
            auline.drain();
            auline.close();
            
        } catch(IOException | LineUnavailableException | UnsupportedAudioFileException e){
            System.out.println(e);
            System.exit(1);
        }
   }
}
