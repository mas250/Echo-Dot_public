package echodot.mode.answer;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.DataInputStream;
import java.net.URLEncoder;
import echodot.tools.HttpConnection;

/**
 * This class implements a simple framework to query the WolframAlpha computational
 * knowledge engine (http://products.wolframalpha.com/api/documentation.html). 
 * 
 * It is based on the Computational.java class provided by David Wakeling as part
 * of a ECM2415 Software Engineering workshop.
 * 
 * @author 650020356
 */

public class QueryWolfram {
  final static String APPID   = "5PJX8G-L3K6RY2UXQ"; //the appID required by WolframAlpha
                                                     //this key is provided by 650020356
                                                     //for more details refer to the documentation

  /**
   * The getAnswer method queries the WolframAlpha API to obtain an answer for the question
   * provided in the corresponding argument.
   * 
   * @param question - a String providing the question that should be passed on to the 
   *                   WolframAlpha computational knowledge engine
   * 
   */
  public static String getAnswer( String question ) { 
    final String method = "POST";
    final String url    
      = ( "http://api.wolframalpha.com/v2/query"
        + "?" + "appid"  + "=" + APPID
        + "&" + "input"  + "=" + urlEncode( question )
        + "&" + "output" + "=" + "JSON"
        );
    final String[][] headers
      = { { "Content-Length", "0" }
        };
    final byte[] body = new byte[0];
    byte[] response   = HttpConnection.connect( method, url, headers, body );
    String xml        = new String( response );
    return xml;
  } 

  /**
   * The urlEncode method encodes the String passed as an argument according to the 
   * procedure outlined in the Java documentation (https://docs.oracle.com/javase/7/docs/api/java/net/URLEncoder.html).
   * 
   * @param s - string to be encoded
   */ 
  private static String urlEncode( String s ) {
    try {
      return URLEncoder.encode( s, "utf-8" );
    } catch ( Exception ex ) {
      System.out.println( ex ); 
      System.exit( 1 ); 
      return null;
    }
  }
}
