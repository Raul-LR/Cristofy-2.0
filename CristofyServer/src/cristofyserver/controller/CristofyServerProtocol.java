/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import java.util.StringTokenizer;

/**
 *
 * @author Raúl
 */
public class CristofyServerProtocol {
    private static final int SENTLOGIN_PASSWORD = 0;
    private static final int CHECKED = 1;
    private static final int LISTENING = 2;
    private static final int BROADCASTING = 3;
    
    private int state = SENTLOGIN_PASSWORD;
    
    public String processInput(String theInput){
        String theOutput = null;
        boolean checked = false;
        
        if(state == SENTLOGIN_PASSWORD){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("CONNECT")){
                    theOutput = theInput;
                    state = CHECKED;
                }
            }
        }else if(state == CHECKED){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("FAVORITES")){
                    theOutput = theInput;
                    state = LISTENING;
                }
            }
        }else if(state == LISTENING){
            StringTokenizer st = new StringTokenizer(theInput, "#");
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                if(temp.equalsIgnoreCase("GET_FILE")){
                    theOutput = theInput;
                }else if(temp.equalsIgnoreCase("GET_PHOTO")){
                    theOutput = theInput;
                }
            }
        }

        return theOutput;
    }
}
