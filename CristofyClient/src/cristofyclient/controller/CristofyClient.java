/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.controller;

import cristofyclient.model.Song;
import cristofyclient.view.Login;
import cristofyclient.view.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Raúl
 */
public class CristofyClient {
    private String hostName = null;
    private int portNumber = 0;
    private String login = null;
    private String password = null;
    private Login actualLoginScreen = null;
    private Socket CristofySocket = null;
    private CristofyClientProtocol cp = null;
    
    public CristofyClient(String hostName, int portNumber, String login, String password, Login loginScreen){
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.login = login;
        this.password = password;
        this.actualLoginScreen = loginScreen;
        this.cp = new CristofyClientProtocol(); 
    }
    
    public void connectToServer(){
        try {
            this.CristofySocket = new Socket(this.hostName, this.portNumber);
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(CristofySocket.getInputStream()));
            String inputLine = null;
                    
            String connect = encodeUTF8("PROTOCOLCRISTOFY1.0#CONNECT#" + this.login + "#" + this.password);
            out.println(connect);
            
            String fromServer = in.readLine();
            inputLine = decodeUTF8(fromServer);
            inputLine = this.cp.processInput(inputLine);
            
            System.out.println("Server: " + inputLine);
            if(inputLine.equals("PROTOCOLCRISTOFY1.0#OK#CONNECTED")){
                this.actualLoginScreen.nextScreen(this);
            }else if(inputLine.equals("PROTOCOLCRISTOFY1.0#ERROR#INVALID_CREDENTIALS")){
                JOptionPane.showMessageDialog(null, "Username or password are incorrect, please try again.","Conection error",JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + this.hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                this.hostName);
        }
    }    
    
    public String downloadClientSongs(){
        String inputLine = null; 
        
        try {
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(CristofySocket.getInputStream()));  
            
            String petition = encodeUTF8("PROTOCOLCRISTOFY1.0#FILES_USERS_SHARED#FAVORITES#" + this.login);
            out.println(petition);
            String fromServer = in.readLine();
            fromServer = decodeUTF8(fromServer);
            System.out.println(fromServer);
            inputLine = this.cp.processInput(fromServer);
            System.out.println("Server: " + inputLine);
            
        } catch (IOException ex) {
            Logger.getLogger(CristofyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inputLine;
    }
    
    public void playSong(Song song){
        /*
        try {
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(CristofySocket.getInputStream()));
            String petition = encodeUTF8("PROTOCOLCRISTOFY1.0#GET_FILE#" + song.getId() + "#" + this.login);     
            out.println(petition);
        */
        String inputLine = null;
        try {
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(CristofySocket.getInputStream()));
            
            File file = new File(song.getPath());
            
            if(!file.exists()){
            
                String petition = encodeUTF8("PROTOCOLCRISTOFY1.0#GET_FILE#" + song.getId() + "#" + this.login);
                out.println(petition);
            
                String fromServer = in.readLine();
                inputLine = decodeUTF8(fromServer);
                inputLine = this.cp.processInput(inputLine);
                System.out.println("Server: " + inputLine);
            
                byte[] bytes = new byte[song.getSize()];
                byte[] bytesPackage;
                int bytesPosition = 0;
                FileOutputStream fileOut = new FileOutputStream(file);
            
                while(!fromServer.equals("PROTOCOLCRISTOFY1.0#TRANSMISION_TO#" + this.login + "#" + song.getId() + "#COMPLETED")){
                    fromServer = in.readLine();
                    fromServer = decodeUTF8(fromServer);
                    StringTokenizer st = new StringTokenizer(fromServer, "#");
                    int i = 0;
            
                    while(st.hasMoreTokens()){
                        String temp = st.nextToken();
                        if(i==4 && !temp.equalsIgnoreCase("COMPLETED")){
                            bytesPackage = Base64.getDecoder().decode(temp);
                            for(int j=0; j<bytesPackage.length; j++){
                                bytes[bytesPosition] = bytesPackage[j];
                                bytesPosition++;
                                System.out.print(".");
                            }
                            System.out.println("");
                        }
                        i++;
                    }
                }
                fileOut.write(bytes);
                fileOut.close();
                System.out.println("Donwload complete");
            }
            System.out.println("Playing song...");
            Player player = new Player(file, song);
            player.setVisible(true);
            
            
            } catch (IOException ex) {
            Logger.getLogger(CristofyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        /*
        } catch (IOException ex) {
            Logger.getLogger(CristofyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
    }
    
    public void downloadImage(Song song) throws UnsupportedEncodingException, IOException{
            String inputLine = null;
            
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(CristofySocket.getInputStream()));
            
            File filePhoto = new File("src\\assets\\photo\\" + song.getUser() + "\\" + song.getTittle() + ".jpg");
            
            if(!filePhoto.exists()){
                
                String petition = encodeUTF8("PROTOCOLCRISTOFY1.0#GET_PHOTO#" + song.getId() + "#" + this.login);
                out.println(petition);
                String fromServer = in.readLine();
                inputLine = decodeUTF8(fromServer);
                inputLine = this.cp.processInput(inputLine);
                System.out.println("Server: " + inputLine);
                
                ArrayList<byte[]> bytes = new ArrayList<byte[]>();
                byte[] bytesPackage;
                FileOutputStream fileOut = new FileOutputStream(filePhoto);

                while(!fromServer.equals("PROTOCOLCRISTOFY1.0#TRANSMISION_PHOTO_TO#" + song.getUser() + "#" + song.getId() + "#COMPLETED")){
                    fromServer = in.readLine();
                    fromServer = decodeUTF8(fromServer);
                    StringTokenizer st = new StringTokenizer(fromServer, "#");
                    int i = 0;

                    while(st.hasMoreTokens()){
                        String temp = st.nextToken();
                        if(i==4 && !temp.equalsIgnoreCase("COMPLETED")){
                            bytesPackage = Base64.getDecoder().decode(temp);
                            bytes.add(bytesPackage);
                        }
                        i++;
                    }
                }
                for(int i=0; i < bytes.size(); i++){
                    fileOut.write(bytes.get(i));
                }
                fileOut.close();
                System.out.println("Donwload complete");
            }
    }
    
    public void close(){
        try {
            this.CristofySocket.close();
        } catch (IOException ex) {
            Logger.getLogger(CristofyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getLogin() {
        return login;
    }

    public Socket getCristofySocket() {
        return CristofySocket;
    }
    
    public String encodeUTF8(String msj) throws UnsupportedEncodingException {
        byte[] bytesEncoded = Base64.getEncoder().encode(msj.getBytes());
        return new String(bytesEncoded);
    }
        
    public String decodeUTF8(String msj) throws UnsupportedEncodingException {
        byte[] bytesDecodes= Base64.getDecoder().decode(msj);
        return new String(bytesDecodes);
    }
}
