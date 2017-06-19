package echodot.mode.listen;

import echodot.tools.HttpConnection;
import java.util.UUID;

/**
 * A class that converts speech to text using Microsoft Cognitive Service.
 * @author 650023085
 */
public class SpeechToText {
    public static final String KEY = "eba9c55a928b4654a6015bc7a7a219d5";
    
    /**
     * Method sends a HTTP request to aquire a token to be used in converting
     * speech to text. The token expires after 10 minutes.
     * 
     * @return a String containing the access token
     */
    public static String getToken() {
        final String method = "POST";
        final String url = "https://api.cognitive.microsoft.com/sts/v1.0/"
                + "issueToken";
        final String[][] headers = { { "Content-Length",            "0" },
                                     { "Ocp-Apim-Subscription-Key", KEY} };
        byte[] data = {}; // Empty as no data needs to be sent
        
        byte[] response = HttpConnection.connect(method, url, headers, data);
        
        return new String(response);
    }
    
    /**
     * Method that converts speech to text using Microsoft Cognitive Service
     * @param data  The byte[] representing the speech
     * @param token The token needed to aquire access to the service
     * @return      A string
     */
    public static String convert(byte[] data, String token) {
        final String method = "POST";
        final String url = "https://speech.platform.bing.com/recognize"
                + "?" + "version"    + "=" + "3.0"
                + "&" + "requestid"  + "=" + UUID.randomUUID().toString()
                + "&" + "appID"      + "=" + "D4D52672-91D7-4C74-8AD8-42B1D98141A5"
                + "&" + "format"     + "=" + "json"
                + "&" + "locale"     + "=" + "en-GB"
                + "&" + "device.os"  + "=" + "wp7"
                + "&" + "scenarios"  + "=" + "websearch"
                + "&" + "instanceid" + "=" + UUID.randomUUID().toString();
        final String[][] headers = {{ "Host",          "speech.platform.bing.com"},
                                    { "Content-Type",  "audio/wav; samplerate=16000"},
                                    { "Authorization", "Baerer " + token}};
        
        byte[] response = HttpConnection.connect(method, url, headers, data);
        
        
        return strip(new String(response));
    }
    
    private static String strip (String s) {
        int start, end;
        start = s.indexOf("\"name\":") + 8;
        end = s.indexOf(",\"lexical\":") - 1;
        return s.substring(start, end);
        
    }
}
