/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.controller;

import java.util.StringTokenizer;

/**
 *
 * @author Raúl
 */
public class CristofyClientProtocol {
    private static final int CHECKING = 0;
    private static final int CONNECTED = 1;
    private static final int INVALID_CREDENTIALS = 2;
    private static final int START_TRANSMISSION = 3;
    private static final int RECEIVING = 4;
    
    private int state = CHECKING;
    
    public String processInput(String theInput){
        String theOutput = null;
        
        if(state == CHECKING){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("OK")){
                    theOutput = theInput;
                    state = CONNECTED;
                }else if(temp.equalsIgnoreCase("ERROR")){
                    theOutput = theInput;
                    state = INVALID_CREDENTIALS;
                }
            }
        }else if(state == CONNECTED){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("END")){
                    theOutput = theInput;
                    state = START_TRANSMISSION;
                }else if(temp.equalsIgnoreCase("ERROR")){
                    theOutput = theInput;
                }
            }
        }else if(state == START_TRANSMISSION){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("RECEIVE")){
                    theOutput = theInput;
                }
            }
        }
        
        return theOutput;
    }
}
