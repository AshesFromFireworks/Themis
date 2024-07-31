package com.themis.Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
    public static String Hash_pwd(String message){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes());
            return Base64.getEncoder().encodeToString(md.digest());
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] hash(String message){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes());
            return md.digest();
        } catch(NoSuchAlgorithmException e){
            System.out.println(e);
            return null;
        }
    }


    public static String hash_bytes(byte[] bytes){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static BigInteger Hash_BigInteger(String string){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes());
            byte[] hash_code = md.digest();
            return new BigInteger(hash_code);
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }

    public static BigInteger Hmac(BigInteger key, byte[] message){
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec s_key = new SecretKeySpec(key.toByteArray(), "HmacSHA256");
            hmac.init(s_key);
            byte[] Hmac_Bytes = hmac.doFinal(message);
            return new BigInteger(Hmac_Bytes);
        } catch(NoSuchAlgorithmException | InvalidKeyException e){
            e.printStackTrace();
            return BigInteger.valueOf(-1);
        }
    }

}
