/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raúl
 */
public class SongModel extends DataBaseConnection{
    private String tittle_model = null;
    private String artist_model = null;
    private String duration_model = null;
    private String genre_model = null;
    private double valoration_model = 0;
    private String path_model = null;
    private int size_model = 0;
    private String user_model = null;
    
    public ArrayList<Song> getSongs(Connection con) throws SQLException{
        
        ArrayList<Song> songs = new ArrayList<Song>();
        
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from cancion");
            
            while(rs.next()){
                songs.add(new Song(rs.getInt("id"), rs.getString("titulo"), rs.getString("artista"), rs.getString("duracion"), rs.getString("genero"), rs.getDouble("valoracion"), rs.getString("ruta"), rs.getInt("tamanio_bytes"), rs.getString("id_usuario")));
            }
            
        }catch(Exception e){
            System.out.println("Sorry, but the songs couldn't be loaded.");
        }finally{
            if(con!=null){
                con.close();
            } 
        }
        
        return songs;
    }
    
    public String getPath(Connection con, int song_id) throws SQLException{
        
        String path = null;
        
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from cancion where Id = " + song_id);
            
            while(rs.next()){
                path = rs.getString("Ruta");
            }
            System.out.println("Path extracted from the db: " + path);
            
        }catch(Exception e){
            System.out.println("Sorry, but the songs couldn't be loaded.");
        }finally{
            if(con!=null){
                con.close();
            } 
        }  
        
        return path;
    }
    
    public String getTittle(Connection con, int song_id) throws SQLException{
        
        String tittle = null;
        
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from cancion where Id = " + song_id);
            
            while(rs.next()){
                tittle = rs.getString("Titulo");
            }
            System.out.println("Tittle extracted from the db: " + tittle);
            
        }catch(Exception e){
            System.out.println("Sorry, but the songs couldn't be loaded.");
        }finally{
            if(con!=null){
                con.close();
            } 
        }  
        
        return tittle;
    }
    
    public String getUser(Connection con, int song_id) throws SQLException{
        
        String user = null;
        
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from cancion where Id = " + song_id);
            
            while(rs.next()){
                user = rs.getString("Id_usuario");
            }
            System.out.println("User extracted from the db: " + user);
            
        }catch(Exception e){
            System.out.println("Sorry, but the songs couldn't be loaded.");
        }finally{
            if(con!=null){
                con.close();
            } 
        }  
        
        return user;
    }
}