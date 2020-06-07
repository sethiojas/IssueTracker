package com.issue_tracker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**This class converts java object into
*byte array and byte array into java
*object for the purpose of storage and 
*retrival from database 
*/

// SOURCE http://burnignorance.com/java-web-development-tips/store-java-class-object-in-database/
// https://www.tutorialspoint.com/How-to-convert-Byte-Array-to-BLOB-in-java
// https://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/

// https://stackoverflow.com/questions/936377/static-method-in-a-generic-class/936951#:~:text=11%20Answers&text=You%20can't%20use%20a,instance%20methods%20and%20instance%20fields.
public class ConvertObject {
    public static<T> byte[] getByteArrayObject(T obj) {
        /**Conerts java object into byte array
        *@param obj Java object
        *returns    byte array representation of java object 
        */
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
    
    // https://stackoverflow.com/questions/509076/how-do-i-address-unchecked-cast-warnings
    // https://stackoverflow.com/questions/12886769/java-compiler-error-not-making-any-sense-identifier-expected
    // https://stackoverflow.com/questions/39366263/does-the-placement-of-suppresswarningsunchecked-matter
    
    // for unchecked cast to type T : line 60
    @SuppressWarnings("unchecked")
    public static<T> T getJavaObject(byte[] conversionObj) {
        /**Converts byte array into java object
        *@param conversionObj   byte array
        *@returns               corresponding Java object
        */
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