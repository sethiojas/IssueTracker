import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class CheckCreds{
    public static String check(String uname, String passwd){
        String retrievedPass = "";
        String retrievedRole = "";
        String dbPath = "jdbc:sqlite:../test.db";
        String getCreds = "select pass, role from test where uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(getCreds);
            pstm.setString(1, uname);
            ResultSet res = pstm.executeQuery();
            if(res.next()){
                retrievedPass = res.getString("pass");
                retrievedRole = res.getString("role");
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (passwd.equals(retrievedPass)) return retrievedRole;
            return null;
        }
    }
}