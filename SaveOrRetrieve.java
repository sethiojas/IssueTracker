import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.Serializable;

// SOURCE http://burnignorance.com/java-web-development-tips/store-java-class-object-in-database/
// https://www.tutorialspoint.com/How-to-convert-Byte-Array-to-BLOB-in-java
// https://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
// https://stackoverflow.com/questions/31477421/error-cannot-find-symbol-when-calling-a-method-defined-using-generics
// https://www.java2novice.com/java-generics/implements-interface/

public class SaveOrRetrieve<T extends Saveable>{

    private String dbPath = "jdbc:sqlite:test.db";

    public byte[] getByteArrayObject(T obj){
        byte[] byteArrayObject = null;
        
        try{
            ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
            ObjectOutputStream objOut  = new ObjectOutputStream(byteArr);

            objOut.writeObject(obj);
            
            byteArrayObject = byteArr.toByteArray();
            objOut.close();
            byteArr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return byteArrayObject;
        }
    }

    public T getJavaObject(byte[] conversionObj){
        T javaObject = null;
        try{
            ByteArrayInputStream byteArr = new ByteArrayInputStream(conversionObj);
            ObjectInputStream objIn = new ObjectInputStream(byteArr);
            javaObject = (T)objIn.readObject();
            objIn.close();
            byteArr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return javaObject;
        }
    }

    void saveThisObject(T saveObj){
        try{
            String uname = saveObj.getUName();
            String save = "insert into test_two values(?, ?)";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(save);
            pstm.setString(1, uname);

            byte[] arr = getByteArrayObject(saveObj);
            if(arr == null) throw new Exception("Cannot save object to database");
            
            pstm.setBytes(2, arr);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            System.out.println("Database Error");
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public T retrieveThisObject(String uname){
        T a = null;
        try{
            String retrival = "select obj from test_two where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(retrival);
            pstm.setString(1, uname);
            ResultSet s = pstm.executeQuery();
            
            byte[] arr = s.getBytes("obj");
            a = getJavaObject(arr);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return a;
        }
    }
}