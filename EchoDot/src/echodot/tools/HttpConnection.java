package echodot.tools;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that makes an HTTP connection
 * @author 650023085
 */
public class HttpConnection {
    public final static int TIMEOUT = 5000;
    public final static int BUFFERSIZE = 4096;
    
    /**
     * Method used to send an HTTP request and receive a response
     * @param method    The method(usually POST).
     * @param url       The URL for establishing the connection.
     * @param headers   The HTTP headers
     * @param data      The data to be sent.
     * @return          The HTTP response.
     */
    public static byte[] connect(String method, String url,
            String[][] headers, byte [] data) {
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)u.openConnection();
            
            // Set up the connection
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            for(int i = 0; i < headers.length; i++) {
                connection.setRequestProperty(headers[i][0], headers[i][1]);
            }
            connection.connect();
            
            // Send data
            DataOutputStream dos;
            dos = new DataOutputStream(connection.getOutputStream());
            dos.write(data);
            dos.flush();
            dos.close();
            
            // Get data
            DataInputStream dis;
            dis = new DataInputStream(connection.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFERSIZE];
            while(true) {
                int n = dis.read(buffer);
                if(n > 0) {
                    baos.write(buffer, 0, n);
                } else {
                    break;
                }
            }
            
            byte[] response = baos.toByteArray();
            dis.close();
            
            connection.disconnect();
            
            return response;
        } catch(Exception e) { // Catch any exceptions.
            System.out.println(e);
            System.exit(1);
            return null;
        }
    }
}
