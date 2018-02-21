/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raúl
 */
public class CristofyServerTransmisionSongThread extends Thread{
    private int song_id = 0;
    private String login = null;
    private PrintWriter out = null;
    
    public CristofyServerTransmisionSongThread(int song_id, String login, PrintWriter out){
        this.song_id = song_id;
        this.login = login;
        this.out = out;
    }
    
    public void run(){          
        SongController song = new SongController();
        String path = null;

        try {
            path = song.getPath(this.song_id);
        } catch (SQLException ex) {
            Logger.getLogger(CristofyServerTransmisionSongThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = new File(path);
        
        byte[] bytes = new byte[(int) file.length()];
        
        FileInputStream fileIn = null;
        
        try{
            
            fileIn = new FileInputStream(file);
            fileIn.read(bytes);
            fileIn.close();
            
            int num_packages = (bytes.length/512);
            int bytesPosition = 0;
            String message;
            
            byte[] bytesPackage;
            
            System.out.println("Packages to transfer: " + (num_packages+1) + " whit a max of bytes size of: " + bytes.length);
            
            for(int i=0; i<num_packages; i++){
                bytesPackage = new byte[512];
                for(int j = 0; j<512; j++){
                    bytesPackage[j] = bytes[bytesPosition];
                    bytesPosition++;
                    System.out.print(".");
                }
                System.out.println("");
                message = encodeUTF8("PROTOCOLCRISTOFY1.0#TRANSMISION_TO#" + this.login + "#" + this.song_id + "#" + Base64.getEncoder().encodeToString(bytesPackage));
                out.println(message);
            }
            
            bytesPackage = new byte[bytes.length - bytesPosition];
            
            for(int i=bytesPosition; i<bytesPackage.length; i++){
                bytesPackage[i] = bytes[i];
            }
            
            message = encodeUTF8("PROTOCOLCRISTOFY1.0#TRANSMISION_TO#" + this.login + "#" + this.song_id + "#" + Base64.getEncoder().encodeToString(bytesPackage));
            System.out.println(message);
            out.println(message);
            System.out.println("PROTOCOLCRISTOFY1.0#TRANSMISION_TO#" + this.login + "#" + this.song_id + "#COMPLETED");
            out.println(encodeUTF8("PROTOCOLCRISTOFY1.0#TRANSMISION_TO#" + this.login + "#" + this.song_id + "#COMPLETED"));
            
        }catch(Exception e){
                
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
