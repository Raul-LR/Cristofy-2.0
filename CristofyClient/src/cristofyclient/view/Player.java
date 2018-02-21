/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.view;

import com.sun.javafx.application.PlatformImpl;
import cristofyclient.model.Song;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Raúl
 */
public class Player extends javax.swing.JFrame {
    
    private File song;
    private Song song_info;
    private Media hit;
    private MediaPlayer mediaPlayer;
    private boolean play = true;
    private boolean stopped = false;
    private ImageIcon play_icon;
    private ImageIcon pause_icon;
    private ImageIcon stop_icon;
    private ImageIcon cover;
    private Runnable r = new Runnable() {
            @Override
            public void run() {
                play_pauseButton.setIcon(new javax.swing.ImageIcon(play_icon.getImage().getScaledInstance(play_pauseButton.getWidth(), play_pauseButton.getHeight(), Image.SCALE_DEFAULT)));
                play = false;
                mediaPlayer.stop();
                progress_bar.setValue(0);
                stopped = true;
            }
        };
    /**
     * Creates new form Player
     */
    public Player(File song, Song song_info) {
        initComponents();
        initIcon();
        this.song = song;
        this.song_info = song_info;
        initImages();
        PlatformImpl.startup(() -> {});
        this.hit = new Media(song.toURI().toString()); 
        this.mediaPlayer = new MediaPlayer(hit);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.mediaPlayer.setOnEndOfMedia(r);
    }

    private Player() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        play_pauseButton = new javax.swing.JLabel();
        progress_bar = new javax.swing.JSlider();
        stopButton = new javax.swing.JLabel();
        coverLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        play_pauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buttons/pause-icon-44480.png"))); // NOI18N
        play_pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                play_pauseButtonMouseClicked(evt);
            }
        });

        stopButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(progress_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(play_pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(coverLable, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(coverLable, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progress_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(play_pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void initImages(){
        this.play_icon = new ImageIcon(getClass().getResource("/assets/buttons/play-button-icon-44451.png"));
        this.pause_icon =  new ImageIcon(getClass().getResource("/assets/buttons/pause-icon-44480.png"));
        this.stop_icon = new ImageIcon(getClass().getResource("/assets/buttons/stop-icon.png"));
        System.out.println(this.song_info.getTittle() + ".jpg");
        this.cover = new ImageIcon(getClass().getResource("/assets/photo/" + this.song_info.getUser() + "/" + this.song_info.getTittle() + ".jpg"));      
        this.play_pauseButton.setIcon(new javax.swing.ImageIcon(this.pause_icon.getImage().getScaledInstance(this.play_pauseButton.getWidth(), this.play_pauseButton.getHeight(), Image.SCALE_DEFAULT)));
        this.stopButton.setIcon(new javax.swing.ImageIcon(this.stop_icon.getImage().getScaledInstance(this.stopButton.getWidth(), this.stopButton.getHeight(), Image.SCALE_DEFAULT)));
        this.coverLable.setIcon(new javax.swing.ImageIcon(this.cover.getImage().getScaledInstance(this.coverLable.getWidth(), this.coverLable.getHeight(), Image.SCALE_DEFAULT)));
    }
    
    public void initIcon(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/buttons/play-xxl.png"));
        setIconImage(icon.getImage());
    }
    
    private void play_pauseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_play_pauseButtonMouseClicked
        // TODO add your handling code here:
        if(play){
            this.play_pauseButton.setIcon(new javax.swing.ImageIcon(this.play_icon.getImage().getScaledInstance(this.play_pauseButton.getWidth(), this.play_pauseButton.getHeight(), Image.SCALE_DEFAULT)));
            this.play = false;
            this.mediaPlayer.pause();
        }else if(!play){
            this.play_pauseButton.setIcon(new javax.swing.ImageIcon(this.pause_icon.getImage().getScaledInstance(this.play_pauseButton.getWidth(), this.play_pauseButton.getHeight(), Image.SCALE_DEFAULT)));
            this.play = true;
            this.mediaPlayer.play();
            if(stopped){
                updateSeekBar();
            }
        }
    }//GEN-LAST:event_play_pauseButtonMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.mediaPlayer.stop();
        this.stopped = true;
        this.mediaPlayer.play();
        this.play = true;
        this.stopped = false;
        updateSeekBar();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.mediaPlayer.stop();
        this.progress_bar.setValue(0);
        this.stopped = true;
    }//GEN-LAST:event_formWindowClosed

    private void stopButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopButtonMouseClicked
        // TODO add your handling code here:
        if(play){
            this.play_pauseButton.setIcon(new javax.swing.ImageIcon(this.play_icon.getImage().getScaledInstance(this.play_pauseButton.getWidth(), this.play_pauseButton.getHeight(), Image.SCALE_DEFAULT)));
            this.play = false;
            this.stopped = true;
            this.progress_bar.setValue(0);
            this.mediaPlayer.stop();
        }else if(!play){
            this.stopped = true;
            this.progress_bar.setValue(0);
            this.mediaPlayer.stop();
        }
    }//GEN-LAST:event_stopButtonMouseClicked
    
    private void updateSeekBar(){
        this.progress_bar.setMinimum(0);
        this.progress_bar.setMaximum((int)this.mediaPlayer.getStopTime().toSeconds());
        System.out.println((int)this.mediaPlayer.getStopTime().toSeconds());
        this.progress_bar.setValue(0);
        Timer timer = new Timer(250, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(play){
                    progress_bar.setValue((int)mediaPlayer.getCurrentTime().toSeconds());      
                }
            }
        });
        timer.start();
    }
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
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Player().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel coverLable;
    private javax.swing.JLabel play_pauseButton;
    public javax.swing.JSlider progress_bar;
    private javax.swing.JLabel stopButton;
    // End of variables declaration//GEN-END:variables
}
