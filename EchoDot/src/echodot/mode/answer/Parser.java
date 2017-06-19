/**
 * This is the parser class which exploits the Wolfram alpha query output 
 * in order to extract an answer. It functions using JSON format.
 */
package echodot.mode.answer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This is the parser class which exploits the Wolfram alpha query output 
 * in order to extract an answer. It functions using JSON format.
 * @author rd366
 */
public class Parser {
    
    private static Parser instance = null;
    private String json = null;
    private String output = null;
    private boolean errorFlag = false;
    private boolean noResult = false;
    
    /**
     * Constructor of the Parser class
     */
    private Parser(){
        
    }
    
    /**
     * Synchronized method which makes sure only one instance of this
     * class is ever created.
     * 
     * @return an instance of the Parser class 
     */
    public static Parser getInstance(){
        if(instance == null){
            synchronized(Parser.class){
                if(instance ==null){
                    instance = new Parser();
                }
            }
        }      
        return instance;
    }
    
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
      }
    
    /**
     * @return the json field of the parser
     */
    
    public String getJson(){
        return json;
    }
    
    /**
     * @return the output field of the parser
     */
    public String getOutput(){
        return output;
    }
    
    /**
     * 
     * @param j value to set the json field
     * of the parser to analyse
     */
    public void setjson(String j){
        this.json = j;
    }
    
    /**
     * @param o value to set the output field
     * of the parser.
     */
    public void setOutput(String o){
        this.output = o;
    }
    
    /**
     * checkError is used to verify whether Wolfram encountered 
     * an error while trying to find an answer to the query.
     * 
     * @return returns true if the JSON input 
     * has the field "errors" set to true
     */
    
    public boolean checkError(){
        errorFlag = json.contains("\"error\" : true"); 
        return errorFlag;
    }
    
 
    /**
     * 
     * @return returns true if no pod has the title Result.
     */
    public boolean checkNoResult(){
        noResult = !json.contains("\"title\" : \"Result\"");
        return noResult;
    }
    
    
    public String parse(String s){

        getInstance();
        setjson(s);
                
        if(checkError()){
            setOutput("An error has occured, please try again");
            return this.output;
        }
        
        if(this.checkNoResult()){
            setOutput("No simple response can be provided");
            return this.output;
        }
        //set the output as the input string
        setOutput(s); 
        
        //cut off all characters before the first occurence of the 
        //Result pod in the json file
        setOutput(getOutput().substring(getOutput().indexOf("\"title\" : \"Result\"")));
        
        //cut of all the characters before the plaintext field of the pod
        setOutput(getOutput().substring(getOutput().indexOf("\"plaintext\"")));
        
        //cut out the "\"plaintext\"" field and get the start and end indices
        //of the plaintext field to extract
        setOutput(getOutput().substring(11));
        int index1 = getOutput().indexOf("\"") + 1;
        int index2 = getOutput().indexOf("\"", index1);
        
        //extract desired substring
        setOutput((String) getOutput().subSequence(index1, index2));
        
        
        return this.output;
        
        
}
    
}

