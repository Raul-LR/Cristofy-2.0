/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import cristofyserver.controller.CristofyServerClientThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raúl
 */
public class CristofyServerThread extends Thread{
    private ArrayList<CristofyServerClientThread> clients = new ArrayList<CristofyServerClientThread>();
    private ServerSocket serverSocket;
    private int portNumber = 0;
    private boolean listening;
    
    public CristofyServerThread(int portNumber){this.portNumber = portNumber;}
    
    public void stopServer(){
        this.listening = false;
        try {
            System.out.println("Server stopped");
            //this.log.setLogInfo("Server stopped");
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(CristofyServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        // TODO code application logic here
        int i = 0;
        
        this.listening = true;
         
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
            System.out.println("Server started");
            //this.log.setLogInfo("Server started");
            while (this.listening) {
               this.clients.add(new CristofyServerClientThread(serverSocket.accept()));
               this.clients.get(i).start();
               i++;
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + this.portNumber);
            System.exit(-1);
        }
    }
}
