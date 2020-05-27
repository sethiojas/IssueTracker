import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// SOURCE http://burnignorance.com/java-web-development-tips/store-java-class-object-in-database/

public class ConvertObject<T> implements Serializable{
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

}