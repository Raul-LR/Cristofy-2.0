/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.controller;

import cristofyclient.model.Song;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raúl
 */
public class CristofyClientDownloadThread extends Thread{
    private Socket CristofySocket;
    private ArrayList<Song> songs;
    private String id;
    
    public CristofyClientDownloadThread(Socket CristofySocket, ArrayList<Song> songs){
        this.CristofySocket = CristofySocket;
        this.songs = songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
    
    public Song getSong(int id){
        boolean found = false;
        Song song_found = null;
        
        for(int i=0; i<this.songs.size() && !found; i++){
            if(this.songs.get(i).getId() == id){
                song_found = songs.get(i);
                found = true;
            }
        }
        
        return song_found;
    }
    
    public void run(){
        String inputLine = null;
        
        try {
            PrintWriter out = new PrintWriter(CristofySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(CristofySocket.getInputStream()));
            
            ArrayList<DownloadSong> downloads_song = new ArrayList<>();
            
            while(true){
                String fromServer = in.readLine();
                inputLine = decodeUTF8(fromServer);
                System.out.println("Server: " + inputLine);
                
                ArrayList<String> tokens = new ArrayList<>();
                
                StringTokenizer st = new StringTokenizer(inputLine, "#");
                while(st.hasMoreTokens()){
                    String token = st.nextToken();
                    tokens.add(token);
                }
                
                if(tokens.get(3).equalsIgnoreCase("PETITION_FROM")){
                    this.id = tokens.get(2);
                    //File fileTemp = new File(getSong(Integer.parseInt(this.id)).getPath());
                    
                    //if(!fileTemp.exists()){
                        downloads_song.add(new DownloadSong(this.id, getSong(Integer.parseInt(this.id))));  
                    //}
                }
                if(tokens.get(1).equalsIgnoreCase("TRANSMISION_TO")){
                    this.id = tokens.get(3);
                    for(int i=0; i<downloads_song.size(); i++){
                        if(downloads_song.get(i).getName().equals(this.id)){
                            System.out.println(tokens.get(4));
                            downloads_song.get(i).addPackage(tokens.get(4));
                        }
                    }
                }
                if(tokens.get(4).equalsIgnoreCase("COMPLETED")){
                    this.id = tokens.get(3);
                    for(int i=0; i<downloads_song.size(); i++){
                        if(downloads_song.get(i).getName().equals(this.id)){
                            downloads_song.get(i).start();
                        }
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CristofyClientDownloadThread.class.getName()).log(Level.SEVERE, null, ex);
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
