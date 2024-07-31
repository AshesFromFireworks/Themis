package com.themis.Utils;


import java.io.*;
import java.util.Base64;


public class ImgBase64 {
    public static  String ImageToBase64(String filepath){
        InputStream in = null;
        byte[] data = null;
        try{
            in = new FileInputStream(filepath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return new String(Base64.encodeBase64(data));
        return Base64.getEncoder().encodeToString(data);
    }

    public static  File Base64ToImage(String Base64String){
        try{
            byte[] b = Base64.getDecoder().decode(Base64String);
            OutputStream out = new FileOutputStream("result.jpg");
            out.write(b);
            out.flush();
            out.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return new File("result.jpg");
    }

    public static  File Base64ToImage(String Base64String, String filename){
        try{
            byte[] b = Base64.getDecoder().decode(Base64String);
            OutputStream out = new FileOutputStream(filename);
            out.write(b);
            out.flush();
            out.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return new File(filename);
    }






}
