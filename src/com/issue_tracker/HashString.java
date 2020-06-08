package com.issue_tracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

/**Class handles generation of SHA-256 hash */

public class HashString {
    public static String hash(String string){
        /**Generate SHA-256 hash of given string
        *@param string  String to generate hash of
        *@returns       generated hash in ASCII
        */
        String hex = null;
        try{
            // https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            hex = String.format("%064x", new BigInteger(1, digest));
            if (hex == null) throw new Exception("Unable to generate hex");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return hex;
        }
    }
}
