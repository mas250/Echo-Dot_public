/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echodot.mode.answer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 650020356
 */
public class QueryWolframTest {
    private String testQuestion;
    private String testAnswer;
    private String fileName;
    
    public QueryWolframTest() {
        testQuestion = "";
        testAnswer = "";
        fileName = "expectedAnswerFromWolfram.txt";
    }
    
    @Before
    public void setUp() {
        //should read from expectedAnswerFromWolfram.txt here
    }
    
    @After
    public void tearDown() {
        testQuestion = null;
        testAnswer = null;
    }

    /**
     * This method tests the getAnswer method of the QueryWolfram class.
     */
    @org.junit.Test
    public void testGetAnswer() {
        //test here
    }
    
}
