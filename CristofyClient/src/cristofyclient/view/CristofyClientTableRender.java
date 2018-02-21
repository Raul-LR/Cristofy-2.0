/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyclient.view;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Raúl
 */
public class CristofyClientTableRender extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof JButton){
            JButton btn = (JButton)value;
            btn.setText("");
            btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buttons/play.png")));
            return btn;
        }
        
        if(value instanceof JProgressBar){
            JProgressBar progressBar = (JProgressBar)value;
            return progressBar;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
