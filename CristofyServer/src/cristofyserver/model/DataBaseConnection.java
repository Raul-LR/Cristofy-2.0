/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.model;

import cristofyserver.controller.CristofyServerLogInfo;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Raúl
 */
public class DataBaseConnection {
    
        public DataBaseConnection(){}
        
        public static Connection connect(){
        Connection con = null;
        
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            
            String server_url = "jdbc:mysql://178.62.241.66:3306/cristofy";
            
            con = DriverManager.getConnection(server_url, "cristofy", "cristofy");
            System.out.println("Connection succesful");
            //log.setLogInfo("connection succesful");
        }
        catch(Exception e){
            System.out.println("Connection error");
            //log.setLogInfo("connection error");
        }
        
        return con;
    } 
}
