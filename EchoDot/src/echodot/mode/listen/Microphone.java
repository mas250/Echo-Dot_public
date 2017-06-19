package echodot.mode.listen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author 650016706
 * 
 * This thread-safe singleton acts as a microphone controller, being able to 
 * enable/disable and to record audio input.
 */
public class Microphone { 
    private static volatile Microphone instance;
    private static final String FILE_FORMAT      = ".wav";
    private static final String OUTPUT_DIRECTORY = "recordings/";
    private static final int    SAMPLE_RATE      = 16000; // MHz
    private static final int    SAMPLE_SIZE      = 16;    // bits
    private static final int    SAMPLE_CHANNELS  = 1;     // mono
    
    private Microphone(){}
    
    /**
     * Synchronized method which makes sure only one instance of this class
     * can be created.
     * 
     * @return an instance of the Microphone class
     */
    public static Microphone getInstance(){
        if(instance == null){
            synchronized(Microphone.class){
                if(instance == null){
                    instance = new Microphone();
                }
            }
        }
        
        return instance;
    }
    
    /**
     * Sets the audio format and tries to set up an audio input stream.
     * 
     * @return the microphone input stream or null if no device is available
     */
    private static synchronized AudioInputStream setupStream(){
        try{
            AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE,
                                                      SAMPLE_SIZE,
                                                      SAMPLE_CHANNELS
                                                      , true, true);
            Info info = new Info(TargetDataLine.class, audioFormat);
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            AudioInputStream stream = new AudioInputStream(line);
            
            line.open(audioFormat);
            line.start();
            return stream;
        } catch(LineUnavailableException e){
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Reads an audio input stream and puts its raw contents into an output stream.
     * 
     * @param timer       the intended duration for the sound
     * @param inputStream contains the stream for the audio input
     * @return
     */
    private static synchronized ByteArrayOutputStream readStream(int timer,
                                                         AudioInputStream inputStream){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int bufferSize = SAMPLE_RATE * inputStream.getFormat().getFrameSize();
            byte buffer[] = new byte[bufferSize];
            
            for(int counter = timer; counter > 0; counter--){
                int rawSound = inputStream.read(buffer, 0, buffer.length);
                
                if(rawSound > 0){
                    outputStream.write(buffer, 0, rawSound);
                }
                else{
                    break;
                }
            }
            
            return outputStream;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Records sound from a byte array stream and writes its contents to a wav
     * file which name is given by the current timestamp.
     * 
     * @param stream 
     */
    private static synchronized String recordSound(ByteArrayOutputStream stream){
        try{
            File file;
            File outputDirectory = new File(OUTPUT_DIRECTORY);
            AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE,
                                                      SAMPLE_SIZE,
                                                      SAMPLE_CHANNELS
                                                      , true, true);
            byte[] byteArray        = stream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            AudioInputStream audioInputStream = new AudioInputStream(inputStream,
                                                                     audioFormat,
                                                                     byteArray.length);
            String outputFileName = new SimpleDateFormat("dd-MM-YYYY--HH-mm-ss")
                                        .format(new java.util.Date());
            
            outputFileName  = OUTPUT_DIRECTORY + outputFileName;
            outputFileName += FILE_FORMAT;
            outputDirectory.mkdir();
            file = new File(outputFileName);
            
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
            
            return outputFileName;
        } catch(IOException e){
            System.out.println(e);
            return null;
        }
    }
    
    public synchronized String getSound(int timer){
        AudioInputStream stream = setupStream();
        
        return recordSound(readStream(timer, stream));
    }
}
