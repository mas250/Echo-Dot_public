package echodot.mode.answer;

/**
 * The answer mode manager is responsible for answering the question obtained from the
 * user by the listen mode manager. It uses the classes in the mode.answer package and also
 * relies on the parser from the tool package.
 * 
 * @author 650020356
 */

public class AnswerModeManager {
    QueryWolfram query; 
    Parser parser;
    Speaker speaker;
    
    public AnswerModeManager() {
        query = new QueryWolfram();
        parser = Parser.getInstance();
        speaker = new Speaker();
    }
    
    /**
     * This method performs the required steps to answer a single question. It 
     *  1. queries the Wolfram API
     *  2. passes the JSON returned by the API to the parser
     *  3. passes the plain text answer extracted by the parser to the 
     *     text-to-speech API
     *  4. plays the answer obtained from the API
     * 
     * @param question - the question to be answered as a String
     */
    public synchronized void answer(String question) {
        //pass question to QueryWolfram & store answer in String 
        String wolframJSON = query.getAnswer(question);
        //pass JSON to parser
        String simpleAnswer = parser.parse(wolframJSON);
        //pass simple answer to TextToSpeech
        //play answer

        speaker.playSound(TextToSpeech.convertToSpeech(simpleAnswer));
    }
}
