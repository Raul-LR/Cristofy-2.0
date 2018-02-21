/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Raúl
 */
public class UserModel extends DataBaseConnection{
    
    public String login_model = "";
    public String password_model = "";

    public UserModel() {}
    
    public String getLogin(Connection con) throws SQLException {
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from usuario");
            
            while(rs.next()){
                login_model = rs.getString("login");
            }
        }
        catch(Exception e){
            System.out.println("Sorry, but something went wrong.");
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return login_model;
    }

    public void setLogin(String login) {
        this.login_model = login;
    }

    public String getPassword(Connection con) throws SQLException {
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from usuario");
            while(rs.next()){
                password_model = rs.getString("password");
            }
        }
        catch(Exception e){
            System.out.println("Sorry, but something went wrong.");
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return password_model;
    }

    public void setPassword(String password) {
        this.password_model = password;
    }
    
    public String searchUser (Connection con, String login_controller) throws SQLException{
        
        try{
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("Select * from usuario where Login = \"" + login_controller + "\"");
           
            if(rs.next()){
                password_model = rs.getString("password");
            }
           
        }
        catch(Exception e){
            System.out.println("Sorry, but something went wrong.");
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return password_model;
    }
}
