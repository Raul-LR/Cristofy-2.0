/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.model;

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
    private boolean favorite = false;
    
    public Song(int id, String path, int size, String tittle, String artist, String genre, String duration, double valoration, String user){
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
    
    public Song(){}

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Song{" + "id = " + id 
                + ", tittle = " + tittle 
                + ", artist = " + artist 
                + ", duration = " + duration 
                + ", genre = " + genre 
                + ", valoration = " + valoration 
                + ", path = " + path 
                + ", size = " + size 
                + ", user = " + user 
                + ", is favorite = " + favorite + '}';
    }
}
