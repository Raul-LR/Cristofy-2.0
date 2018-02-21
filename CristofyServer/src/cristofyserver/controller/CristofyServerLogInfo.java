/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTextArea;

/**
 *
 * @author Raúl
 */
public class CristofyServerLogInfo extends Thread{
        private JTextArea logArea = null;
        
        public CristofyServerLogInfo(JTextArea logArea){this.logArea = logArea;}
        
        public void setLogInfo(String info){
            Calendar calendario = new GregorianCalendar();
            int hora, minutos, segundos;

            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);

            this.logArea.append(info + "  [" + hora + ":" + minutos + ":" + segundos + "]");
        }        
}
