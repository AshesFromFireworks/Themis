package com.themis.Utils;

import com.themis.Model.Keys.Cipher;
import com.themis.Model.Keys.KEY;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

import java.util.Base64;
import java.util.Set;

public class keysTran {
    public static String CipherToString(Cipher cipher){

        Element ct_ = cipher.ct_;
        String res = Base64.getEncoder().encodeToString(ct_.toBytes());
        for(int i = 0; i < 3; i++){
            res = res + "|" + Base64.getEncoder().encodeToString(cipher.ct0[i].toBytes());
        }
        for(int i = 0; i < 9; i++){
            res = res + "|" + Base64.getEncoder().encodeToString(cipher.cti[i].toBytes());
        }
        return res;
    }

//    public static keyBase64 keyToBase64(key key){
//        keyBase64 keyBase64 = new keyBase64();
//        keyBase64.g_sigma = new String[]{Base64.getEncoder().encodeToString(key.g_sigma[0].toBytes()),
//                Base64.getEncoder().encodeToString(key.g_sigma[1].toBytes()),
//                Base64.getEncoder().encodeToString(key.g_sigma[2].toBytes())};
//        for(int i = 0; i < 3; i ++){
//            keyBase64.sk0[i] = Base64.getEncoder().encodeToString(key.sk0[i].toBytes());
//            keyBase64.sk_[i] = Base64.getEncoder().encodeToString(key.sk_[i].toBytes());
//        }
//        Set<String> strings = key.sky.keySet();
//        for(String str: strings){
//            keyBase64.sky.put(str, new String[]{Base64.getEncoder().encodeToString(key.sky.get(str)[0].toBytes()),
//                    Base64.getEncoder().encodeToString(key.sky.get(str)[1].toBytes())});
//        }
//        return keyBase64;
//    }
//
//    public static key Base64ToKey(keyBase64 Base64key, Pairing pairing){
//        key k = new key();
//        Field H = pairing.getG2();
//        Field G = pairing.getG1();
//        k.g_sigma = new Element[]{G.newElementFromBytes(Base64.getDecoder().decode(Base64key.g_sigma[0])),
//                G.newElementFromBytes(Base64.getDecoder().decode(Base64key.g_sigma[1])),
//                G.newElementFromBytes(Base64.getDecoder().decode(Base64key.g_sigma[2]))};
//        for(int i = 0; i < 3; i++){
//            k.sk0[i] = H.newElementFromBytes(Base64.getDecoder().decode(Base64key.sk0[i]));
//            k.sk_[i] = H.newElementFromBytes(Base64.getDecoder().decode(Base64key.sk_[i]));
//        }
//        Set<String> strings = Base64key.sky.keySet();
//        for(String str: strings){
//            k.sky.put(str, new Element[]{G.newElementFromBytes(Base64.getDecoder().decode(Base64key.sky.get(str)[0])),
//                    G.newElementFromBytes(Base64.getDecoder().decode(Base64key.sky.get(str)[1]))});
//        }
//        return k;
//    }

    public static Cipher StringToCipher(String string, Pairing pairing){
        Cipher cipher = new Cipher();
        String[] str = string.split("\\|");
        cipher.ct_ = pairing.getGT().newElementFromBytes(Base64.getDecoder().decode(str[0]));
        for(int i = 0; i < 3; i++){
            cipher.ct0[i] = pairing.getG2().newElementFromBytes(Base64.getDecoder().decode(str[i+1]));
        }
        for(int i = 0; i < 9; i++){
            cipher.cti[i] = pairing.getG1().newElementFromBytes(Base64.getDecoder().decode(str[i+4]));
            cipher.cti[i] = pairing.getG1().newZeroElement();
        }
        return cipher;
    }

}
