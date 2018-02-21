/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.controller;

import cristofyclient.model.Song;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raúl
 */
public class DownloadSong extends Thread{   
    private String id;
    private Song song;
    private byte[] bytesPackage;
    ArrayList<byte[]> bytes = new ArrayList<>();
    
    public DownloadSong(String id, Song song){
        super(id);
        this.id = id;
        this.song = song;
    }
    
    public void addPackage(String bytesPackage){
        this.bytesPackage = Base64.getDecoder().decode(bytesPackage);
        this.bytes.add(this.bytesPackage);
    }
    
    public void run(){  
        try {
            File file = new File(this.song.getPath());
            FileOutputStream fileOut = new FileOutputStream(file);
            
            for(int i=0; i<this.bytes.size(); i++){
                fileOut.write(bytes.get(i));
            }
            fileOut.close();
            System.out.println("Download complete");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DownloadSong.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadSong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
