
package echodot.mode.answer;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rd366
 */
public class ParserTest {
     
    @Test
    public void testCheckError() throws IOException{
        Parser p = Parser.getInstance();
        p.setjson(Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\example01.txt"));
        assertFalse(p.checkError());
        p.setjson(Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\example06.txt"));
        assertTrue(p.checkError());
    }
    
    @Test
    public void testCheckNoResult() throws IOException{
        Parser p = Parser.getInstance();
        p.setjson(Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\example01.txt"));
        assertFalse(p.checkNoResult());
        p.setjson(Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\example06.txt"));
        assertTrue(p.checkNoResult());
    }
    
    
    @Test
    public void testReadFile() throws IOException{
        Parser p = Parser.getInstance();
        String s = Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\testReadFile.txt");
        assertEquals("This is a test string.", s);
    }
    
    @Test
    public void testParse() throws IOException {
        Parser p = Parser.getInstance();
        String s = Parser.readFile("C:\\Users\\Raphael\\Desktop\\WORK\\ECM2415 SOFTWARE ENGINEERING\\Echo-Dot\\EchoDot\\test\\echodot\\mode\\answer\\example01.txt");
        s = p.parse(s);
        assertEquals("961.78 °C  (degrees Celsius)", s);
    }
    
}
