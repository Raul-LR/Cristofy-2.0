/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.model;

/**
 *
 * @author Raúl
 */
public class Song {
    private int id = 0;
    private String tittle = null;
    private String artist = null;
    private String duration = null;
    private String genre = null;
    private double valoration = 0;
    private String path = null;
    private int size = 0;
    private String user = null;   
    
    public Song(int id, String tittle, String artist, String duration, String genre, double valoration, String path, int size, String user){
        this.id = id;
        this.tittle = tittle;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
        this.valoration = valoration;
        this.path = path;
        this.size = size;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getValoration() {
        return valoration;
    }

    public void setValoration(double valoration) {
        this.valoration = valoration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
