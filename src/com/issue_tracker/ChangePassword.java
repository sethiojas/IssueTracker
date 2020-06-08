package com.issue_tracker;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**Handles password change 
*/

public class ChangePassword {
    private static String dbPath = "jdbc:sqlite:../issueTracker.db";

    public static void change(String uname, String passwd) {
        /**Changes password for a given uname
        *@param uname   Uname for which password has to be changed
        *@param passwd  New password to set
        */
        String change = "UPDATE credentials SET password=? WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(change);
            pstm.setString(1, passwd);
            pstm.setString(2, uname);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}