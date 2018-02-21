/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.view;

import cristofyclient.controller.CristofyClient;
import cristofyclient.model.Song;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raúl
 */
public class CristofyClientGUI extends javax.swing.JFrame {
    private CristofyClient client = null;
    private ArrayList<Song> songs = new ArrayList<Song>();
            
    /**
     * Creates new form CristofyClientGUI
     */
    public CristofyClientGUI(CristofyClient client) {
        initComponents();
        initIcon();
        initClient(client);
        initTables();
        /*
        CristofyClientDownloadThread downloadListener = new CristofyClientDownloadThread(this.client.getCristofySocket(), this.songs);
        downloadListener.start();
        */
    }

    private CristofyClientGUI() {
    }
    
    public void initIcon(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/logo/cristofyIcon.png"));
        setIconImage(icon.getImage());
    }
        
    public void initClient(CristofyClient client){
        this.client = client;
        this.user.setText(this.client.getLogin());   
    }
    
    public void initTables(){
        readSongs();
        fillSongsTable();
        fillFavoritesSongsTable();
    }
    
    public void fillFavoritesSongsTable(){
        ArrayList<Song> favoritesSongs = new ArrayList<Song>();
        
        for(int i=0; i<this.songs.size();i++){
            if(this.songs.get(i).isFavorite()){
                favoritesSongs.add(this.songs.get(i));
            }
        }
        setTableModel(favoritesSongs, this.favoritesSongsTable);
    }
    
    public void fillSongsTable(){
        setTableModel(songs, this.songsTable);
    }

    public void setTableModel(ArrayList<Song> songs, JTable table){
        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        table.setDefaultRenderer(Object.class, new CristofyClientTableRender());
        
        model.addColumn("Tittle");
        model.addColumn("Artist");
        model.addColumn("Duration");
        model.addColumn("Genre");
        model.addColumn("Valoration");
        model.addColumn("Path");
        model.addColumn("Size");
        model.addColumn("User");
        model.addColumn("Play");
        model.addColumn("Download progress");
        
        Object[] row = new Object[10];
        
        for(int i=0; i<songs.size(); i++){
            JButton playButton = new JButton("Play");
            JProgressBar progressBar = new JProgressBar();
            playButton.setName(""+songs.get(i).getId());
            progressBar.setName("Download " + songs.get(i).getSize() + " bytes");
            row[0] = songs.get(i).getTittle();
            row[1] = songs.get(i).getArtist();
            row[2] = songs.get(i).getDuration();
            row[3] = songs.get(i).getGenre();
            row[4] = songs.get(i).getValoration();
            row[5] = songs.get(i).getPath();
            row[6] = songs.get(i).getSize();
            row[7] = songs.get(i).getUser();
            row[8] = playButton;
            row[9] = progressBar;
            
            model.addRow(row);
        }
        
        table.setModel(model);
    }
    
    public void readSongs(){
        String songsProtocol = this.client.downloadClientSongs();
        ArrayList<String> songsText = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(songsProtocol, "#");
        
        while(st.hasMoreTokens()){
            String temp = st.nextToken();
            songsText.add(temp);
        }
        
        for(int i=2; i<songsText.size()-1; i++){
            StringTokenizer st2 = new StringTokenizer(songsText.get(i), "@");
            Song temp_song = new Song();
            if(st2.hasMoreTokens()){
                temp_song.setId(Integer.parseInt(st2.nextToken()));
                temp_song.setPath(st2.nextToken());
                temp_song.setSize(Integer.parseInt(st2.nextToken()));
                temp_song.setTittle(st2.nextToken());
                temp_song.setArtist(st2.nextToken());
                temp_song.setGenre(st2.nextToken());
                temp_song.setDuration(st2.nextToken());
                temp_song.setValoration(Double.parseDouble(st2.nextToken()));
                temp_song.setUser(st2.nextToken(":").substring(1));
                if(st2.nextToken().equalsIgnoreCase("TRUE")){
                    temp_song.setFavorite(true);
                }
            }
            System.out.println(temp_song.toString());
            this.songs.add(temp_song);
        }
    }
    
    public void backScreen(){
        Login loginScreen = new Login();
        loginScreen.setVisible(true);
        this.dispose();
    }
    
    public Song getSong(int song_id){
        Song song = null;
        
        for(int i=0; i<this.songs.size(); i++){
            if(this.songs.get(i).getId() == song_id){
                song = this.songs.get(i);
            }
        }
        
        return song;
    }
    
    public Song searchSong(int id){
        boolean found = false;
        Song song = null;
        
        for(int i=0; i<this.songs.size() && !found; i++){
            if(this.songs.get(i).getId() == id){
                song = this.songs.get(i);
                found = true;
            }
        }
        return song;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        songsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        favoritesSongsTable = new javax.swing.JTable();
        FileChooser = new javax.swing.JFileChooser();
        userText = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        disconnect = new javax.swing.JButton();
        cristofyLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cristofy");

        songsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        songsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(songsTable);

        jTabbedPane1.addTab("Songs", jScrollPane1);

        favoritesSongsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        favoritesSongsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                favoritesSongsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(favoritesSongsTable);

        jTabbedPane1.addTab("Favorites", jScrollPane2);
        jTabbedPane1.addTab("Up song", FileChooser);

        userText.setText("User:");

        user.setText("<user>");

        disconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buttons/logout.png"))); // NOI18N
        disconnect.setText("Logout");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        cristofyLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo/CristofyLogo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cristofyLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(disconnect)
                .addGap(31, 31, 31)
                .addComponent(userText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(user)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userText)
                            .addComponent(user)
                            .addComponent(disconnect)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cristofyLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        // TODO add your handling code here:
        System.out.println("User " + this.client.getLogin() + " disconnected, goodbye.");
        this.client.close();
        backScreen();
    }//GEN-LAST:event_disconnectActionPerformed

    private void songsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songsTableMouseClicked
        // TODO add your handling code here:
        int column = this.songsTable.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY()/this.songsTable.getRowHeight();
        JProgressBar progressBar = null;
        
        if((row < this.songsTable.getRowCount() && row>= 0) && (column < this.songsTable.getColumnCount() && column >= 0)){
            Object value = this.songsTable.getValueAt(row, column);
            if(value instanceof JProgressBar){
                progressBar = (JProgressBar) value;
            }
            if(value instanceof JButton){
                ((JButton)value).doClick();
                JButton button = (JButton)value;
                System.out.println("Play song with id: " + button.getName());
                try {
                    this.client.downloadImage(searchSong(Integer.parseInt(button.getName())));
                } catch (IOException ex) {
                    Logger.getLogger(CristofyClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.client.playSong(searchSong(Integer.parseInt(button.getName())));
            }
        }
    }//GEN-LAST:event_songsTableMouseClicked

    private void favoritesSongsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favoritesSongsTableMouseClicked
        // TODO add your handling code here:
        int column = this.favoritesSongsTable.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY()/this.favoritesSongsTable.getRowHeight();
        JProgressBar progressBar = null;
        
        if((row < this.favoritesSongsTable.getRowCount() && row>= 0) && (column < this.favoritesSongsTable.getColumnCount() && column >= 0)){
            Object value = this.favoritesSongsTable.getValueAt(row, column);
            if(value instanceof JProgressBar){
                progressBar = (JProgressBar) value;
            }
            if(value instanceof JButton){
                ((JButton)value).doClick();
                JButton button = (JButton)value;
                System.out.println("Play favorite song with id: " + button.getName());
                try {
                    this.client.downloadImage(searchSong(Integer.parseInt(button.getName())));
                } catch (IOException ex) {
                    Logger.getLogger(CristofyClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.client.playSong(searchSong(Integer.parseInt(button.getName())));
            }
        }
    }//GEN-LAST:event_favoritesSongsTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CristofyClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CristofyClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CristofyClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CristofyClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CristofyClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JLabel cristofyLogo;
    private javax.swing.JButton disconnect;
    private javax.swing.JTable favoritesSongsTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable songsTable;
    private javax.swing.JLabel user;
    private javax.swing.JLabel userText;
    // End of variables declaration//GEN-END:variables
}
