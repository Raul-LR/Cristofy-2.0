/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cristofyserver.controller;

import cristofyserver.model.UserModel;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Raúl
 */
public class UserController {

    public UserController(){}

    public boolean validateUser(String login, String password) throws SQLException{
        UserModel user = new UserModel();
        Connection con = user.connect();
        
        String validation = user.searchUser(con, login);
        if(validation.equals(password)){
            return true;
        }
        else
            return false;
    }
}
