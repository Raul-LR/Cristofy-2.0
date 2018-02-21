/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import cristofyserver.model.Song;
import cristofyserver.model.SongModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Raúl
 */
public class SongController {
    
    private ArrayList<Song> songs;
    
    public SongController(){this.songs = new ArrayList<Song>();}

    public ArrayList<Song> getSongs() throws SQLException{
        
        SongModel song = new SongModel();
        Connection con = song.connect();
        
        this.songs = song.getSongs(con);
        
        return songs;
    }
    
    public String getPath(int song_id) throws SQLException{
        SongModel song = new SongModel();
        Connection con = song.connect();
        
        String path = song.getPath(con, song_id);
        
        return path;
    }
    
    public String getTittle(int song_id) throws SQLException{
        SongModel song = new SongModel();
        Connection con = song.connect();
        
        String tittle = song.getTittle(con, song_id);
        
        return tittle;    
    }
    
    public String getUser(int song_id) throws SQLException{
        SongModel song = new SongModel();
        Connection con = song.connect();
        
        String user = song.getUser(con, song_id);
        
        return user;    
    }
}
