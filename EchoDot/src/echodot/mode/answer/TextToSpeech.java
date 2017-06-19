package echodot.mode.answer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import echodot.tools.HttpConnection;

/**
 * This class allows text to speech conversion using Microsoft Cognitive Services.
 * 
 * It is based on the TextToSpeech.java class provided by David Wakeling as part
 * of a ECM2415 Software Engineering workshop.
 * 
 * @author 650020356
 */

public class TextToSpeech {
  final static String LANG   = "en-US";
  final static String GENDER = "Female";
  final static String OUTPUT = "echodot_output.wav";
  final static String FORMAT = "riff-16khz-16bit-mono-pcm";

  final static String KEY1   = "687ecc1f7f3643708083a7333cbada68"; //keys provided by 650020356
  final static String KEY2   = "318c3509a7e24a81948ab9bb1214a6d8";

  /**
   * This method renews the access token required by Microsoft Cognitive Services
   * because they expire after 10 minutes.
   * 
   * @param key1 - the developer key necessary for obtaining an access token
   */
  static String renewAccessToken( String key ) {
    final String method = "POST";
    final String url =
      "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
    final byte[] body = {};
    final String[][] headers
      = { { "Ocp-Apim-Subscription-Key", key                          }
        , { "Content-Length"           , String.valueOf( body.length ) }
        };
    byte[] response = HttpConnection.connect( method, url, headers, body );
    return new String( response );
  }

  /**
   * This method accesses the speech API of Microsoft Cognitive Services. The API converts
   * the text passed as an argument to speech. It depends on the writeData method to 
   * output the response to a .wav file. 
   * 
   * More detailed information on the method's arguments can be found here:
   * https://www.microsoft.com/cognitive-services/en-us/speech-api/documentation/API-Reference-REST/BingVoiceOutput#InputParam
   * 
   * @param token - the access token required (see also renewAccessToken)
   * @param text - the text to be converted into speech
   * @param lang  - the language to be used
   * @param gender - the gender of the voice (options are Female and Male)
   * @param format - the format of the output
   * 
   * @return the response of the Speech API as a byte array
   */
  static byte[] synthesizeSpeech( String token, String text
                                , String lang,  String gender
                                , String format ) {
    final String method = "POST";
    final String url = "https://speech.platform.bing.com/synthesize";
    final byte[] body
      = ( "<speak version='1.0' xml:lang='en-us'>"
        + "<voice xml:lang='" + lang   + "' "
        + "xml:gender='"      + gender + "' "
        + "name='Microsoft Server Speech Text to Speech Voice"
        + " (en-US, ZiraRUS)'>"
        + text
        + "</voice></speak>" ).getBytes();
    final String[][] headers
      = { { "Content-Type"             , "application/ssml+xml"        }
        , { "Content-Length"           , String.valueOf( body.length ) }
        , { "Authorization"            , "Bearer " + token             }
        , { "X-Microsoft-OutputFormat" , format                        }
        };
    byte[] response = HttpConnection.connect( method, url, headers, body );
    return response;
  }

  /**
   * This method writes the response obtained by synthesizeSpeech to a .wav file. 
   * 
   * @param buffer - the response obtained by the synthesizeSpeech method
   * @param name - the name of the file to which the output should be written
   * 
   */
  static void writeData( byte[] buffer, String name ) {
    try {
      File             file = new File( name );
      FileOutputStream fos  = new FileOutputStream( file );
      DataOutputStream dos  = new DataOutputStream( fos );
      dos.write( buffer );
      dos.flush();
      dos.close();
    } catch ( Exception ex ) {
      System.out.println( ex ); 
      System.exit( 1 ); 
      return;
    }
  }

  /**
   * This method converts the phrase passed as an argument to speech and writes it 
   * to the file specified in OUTPUT. 
   * 
   * @param phrase - the phrase to be converted to speech
   * 
   */
  public static String convertToSpeech (String phrase) {
      final String token  = renewAccessToken( KEY2 );
      final byte[] speech = synthesizeSpeech( token, phrase, LANG, GENDER, FORMAT );
      writeData( speech, OUTPUT );
      
      return  OUTPUT;
  }
  
  /**
   * This method converts the phrase passed as an argument to speech and returns it as
   * a byte array. 
   * 
   * @param phrase - the phrase to be converted to speech
   */
  public static byte[] convertToByteArray(String phrase){
      final String token  = renewAccessToken( KEY2 );
      final byte[] speech = synthesizeSpeech( token, phrase, LANG, GENDER, FORMAT );
      return speech;
  }
}
