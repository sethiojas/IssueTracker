package com.issue_tracker.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.issue_tracker.HashString;

/**Class cross-checks credentials with those stored
*in the database 
*/

class CheckCreds{
    public static String check(String uname, String passwd){
        /**Check credentials with those stored in database
        *@param uname   Entered uname
        *@param passwd  Entered password
        *@returns       Role associated with account if authentication
        *               was successful, null otherwise 
        */
        String password = HashString.hash(passwd);
        String retrievedPass = "";
        String retrievedRole = "";
        String dbPath = "jdbc:sqlite:../issueTracker.db";
        String getCreds = "SELECT password, role FROM credentials WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(getCreds);
            pstm.setString(1, uname);
            ResultSet res = pstm.executeQuery();
            if(res.next()){
                retrievedPass = res.getString("password");
                retrievedRole = res.getString("role");
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (password.equals(retrievedPass)) return retrievedRole;
            return null;
        }
    }
}