/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import cristofyserver.controller.CristofyServerProtocol;
import cristofyserver.controller.UserController;
import cristofyserver.model.Song;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raúl
 */
public class CristofyServerClientThread extends Thread{
    private Socket socket = null;
    private String login = null;
    private String password = null;
    private int song_id = 0;
    private boolean ok = false;
    
    public CristofyServerClientThread(Socket socket){
        super("CristofyServerThread");
        this.socket = socket;
    }
    
    public void run(){
        try {        
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            CristofyServerProtocol cp = new CristofyServerProtocol();
            
            while(true){
                String inputLine = null;
                if((inputLine = in.readLine()) != null){
                    inputLine = decodeUTF8(inputLine);
                    StringTokenizer st = new StringTokenizer(inputLine, "#");
                    while(st.hasMoreTokens()){
                        String checkInput = st.nextToken();
                        if(checkInput.equalsIgnoreCase("CONNECT")){
                            validateUser(cp, out, in, inputLine);
                        }
                        if(checkInput.equalsIgnoreCase("FILES_USERS_SHARED")){
                            sentSongs(cp, out, in, inputLine);
                        }
                        if(checkInput.equalsIgnoreCase("GET_FILE")){
                            startSongTransmision(cp, out, in, inputLine);
                        }
                        if(checkInput.equalsIgnoreCase("GET_PHOTO")){
                            startPhotoTransmision(cp, out, in, inputLine);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(CristofyServerClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void validateUser(CristofyServerProtocol cp, PrintWriter out, BufferedReader in, String inputLine) throws UnsupportedEncodingException{
        String outputLine = null;
        outputLine = cp.processInput(inputLine);
        System.out.println("Client: " + outputLine);
        //this.log.setLogInfo("Client: " + outputLine);
        
        StringTokenizer petition = new StringTokenizer(outputLine, "#");
        
        int i = 0;
        while(petition.hasMoreTokens()){
            String temp = petition.nextToken();
            if(i==2){
                this.login = temp;
            }
            if(i==3){
                this.password = temp;
            }
            i++;
        }

        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        //this.log.setLogInfo("Login: " + login);
        //this.log.setLogInfo("Password: " + password);

        UserController user = new UserController();
        
        try {
            this.ok = user.validateUser(login, password);
        } catch (SQLException ex) {
            Logger.getLogger(CristofyServerClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ok){
        inputLine = encodeUTF8("PROTOCOLCRISTOFY1.0#OK#CONNECTED");
        out.println(inputLine);
        System.out.println("Server: " + inputLine);
        //this.log.setLogInfo("Server: " + inputLine);
        this.ok = false;

        }else if(!ok){
        inputLine = encodeUTF8("PROTOCOLCRISTOFY1.0#ERROR#INVALID_CREDENTIALS");
        out.println(inputLine);
        System.out.println("Server: " + inputLine);
        //this.log.setLogInfo("Server: " + inputLine);
        }
    }
    
    public void sentSongs(CristofyServerProtocol cp, PrintWriter out, BufferedReader in, String inputLine) throws UnsupportedEncodingException{
        String outputLine = null;
        outputLine = cp.processInput(inputLine);
        System.out.println("Client: " + outputLine);
        //this.log.setLogInfo("Client: " + outputLine);
        
        StringTokenizer petition = new StringTokenizer(outputLine, "#");
        
        int i = 0;
        while(petition.hasMoreTokens()){
            String temp = petition.nextToken();
            if(i==3 && this.login.equals(temp)){
                inputLine = "PROTOCOLCRISTOFY1.0#";
                try{
                    ArrayList<Song> songs = new ArrayList<Song>();
                    SongController song = new SongController();
                    songs = song.getSongs();
                    inputLine = inputLine + "OKSTART";

                    for(int j=0; j<songs.size(); j++){
                        inputLine = inputLine + "#" + songs.get(j).getId() 
                                              + "@" + songs.get(j).getPath() 
                                              + "@" + songs.get(j).getSize() 
                                              + "@" + songs.get(j).getTittle() 
                                              + "@" + songs.get(j).getArtist() 
                                              + "@" + songs.get(j).getGenre() 
                                              + "@" + songs.get(j).getDuration() 
                                              + "@" + songs.get(j).getValoration() 
                                              + "@" + songs.get(j).getUser();
                        if(temp.equals(songs.get(j).getUser())){
                            inputLine = inputLine + ":TRUE";
                        }else
                            inputLine = inputLine + ":FALSE";
                    }
                    inputLine = encodeUTF8(inputLine + "#END");

                }catch(Exception e){
                    inputLine = encodeUTF8(inputLine + "ERROR#CANT_READ_FILES");
                }

                System.out.println("Server: " + inputLine);
                //this.log.setLogInfo("Server: " + inputLine);
                out.println(inputLine);
            }
            i++;
        }
    }
    
    public void startSongTransmision(CristofyServerProtocol cp, PrintWriter out, BufferedReader in, String inputLine) throws UnsupportedEncodingException{
        String outputLine = null;
        outputLine = cp.processInput(inputLine);
        System.out.println("Client: " + outputLine);
        
        StringTokenizer petition = new StringTokenizer(outputLine, "#");
        
        int i = 0;
        while(petition.hasMoreTokens()){
            String temp = petition.nextToken();
            if(i==2){
                this.song_id = Integer.parseInt(temp);
                System.out.println("Song_id: " + this.song_id);
            }
            if(i==3 && this.login.equals(temp)){
                inputLine = encodeUTF8("PROTOCOLCRISTOFY1.0#RECEIVE#"+ this.song_id +"#PETITION_FROM#" + this.login);
                out.println(inputLine);
                System.out.println("Server: " + inputLine);
                
                CristofyServerTransmisionSongThread transmision = new CristofyServerTransmisionSongThread(this.song_id, this.login, out);
                transmision.start();

            }
            i++;
        }
    }
    
    public void startPhotoTransmision(CristofyServerProtocol cp, PrintWriter out, BufferedReader in, String inputLine) throws UnsupportedEncodingException{
        String outputLine = null;
        outputLine = cp.processInput(inputLine);
        System.out.println("Client: " + outputLine);
        
        StringTokenizer petition = new StringTokenizer(outputLine, "#");
        
        int i = 0;
        while(petition.hasMoreTokens()){
            String temp = petition.nextToken();
            if(i==2){
                this.song_id = Integer.parseInt(temp);
                System.out.println("Song_id: " + this.song_id);
            }
            if(i==3 && this.login.equals(temp)){
                inputLine = encodeUTF8("PROTOCOLCRISTOFY1.0#RECEIVE#"+ this.song_id +"#PHOTO_PETITION_FROM#" + this.login);
                out.println(inputLine);
                System.out.println("Server: " + inputLine);
                
                CristofyServerTransmisionPhotoThread transmision = new CristofyServerTransmisionPhotoThread(this.song_id, this.login, out);
                transmision.start();

            }
            i++;
        }
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
