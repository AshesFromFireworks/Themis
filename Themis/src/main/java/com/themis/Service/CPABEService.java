package com.themis.Service;

import com.themis.Model.Keys.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static com.themis.Utils.Hash.hash;
import static com.themis.Utils.ImgBase64.Base64ToImage;
import static com.themis.Utils.ImgBase64.ImageToBase64;
import static com.themis.Utils.keysTran.*;

public class CPABEService {
    public static PKMSK Setup(Pairing bp){
        Field G = bp.getG1();
        Field H = bp.getG2();
        Field Z = bp.getZr();
        Element g = G.newRandomElement().getImmutable();
        Element h = H.newRandomElement().getImmutable();
        Element a1 = Z.newRandomElement().getImmutable();
        Element a2 = Z.newRandomElement().getImmutable();
        Element b1 = Z.newRandomElement().getImmutable();
        Element b2 = Z.newRandomElement().getImmutable();
        Element d1 = Z.newRandomElement().getImmutable();
        Element d2 = Z.newRandomElement().getImmutable();
        Element d3 = Z.newRandomElement().getImmutable();
        Element H1 = h.mulZn(a1).getImmutable();
        Element H2 = h.mulZn(a2).getImmutable();
        Element T1 = bp.pairing(g, h).mulZn(d1.mul(a1).add(d3)).getImmutable();
        Element T2 = bp.pairing(g, h).mulZn(d2.mul(a2).add(d3)).getImmutable();
        Element g_d1 = g.mulZn(d1).getImmutable();
        Element g_d2 = g.mulZn(d2).getImmutable();
        Element g_d3 = g.mulZn(d3).getImmutable();
        PK pk = new PK(h, H1, H2, T1, T2);
        MSK msk = new MSK(g, h, a1, a2, b1, b2, g_d1, g_d2, g_d3);
        PKMSK pkmsk = new PKMSK();
        pkmsk.msk = msk;
        pkmsk.pk = pk;
        return pkmsk;
    }
//

    public static Element transformation(String msg, Pairing bp){
        int l = msg.length();
        if(l < 128) {
            for(int i = 0; i < 128-l; i++){
                msg = msg + " ";
            }
        }
        Field GT = bp.getGT();
        return GT.newElementFromBytes(msg.getBytes()).getImmutable();
    }

    public static String recover(Element gt){
        String s = new String(gt.toBytes());
        int i = 128;
        for(;i > 0; i--){
            if(!s.substring(i-1, i).equalsIgnoreCase(" ")){
                break;
            }
        }
        return s.substring(0, i);
    }


    public static String ABE(String filename, KEY key, String[] str, PKMSK pkmsk) {
        Pairing bp = PairingFactory.getPairing("a.properties");
        int[][] M = new int[3][3];
        M[0][0] = -1;M[0][1] = 0;M[0][2] = 0;
        M[1][0] = 1;M[1][1] = 0;M[1][2] = 0;
        M[2][0] = 1;M[2][1] = 0;M[2][2] = 0;
        String base64 = ImageToBase64(filename);
        int len = base64.length();
        String res = "";
        Cipher cipher;
        String temp;
        Element msg;
        for(int i = 0; i < len; i = i + 128){
            if(len < i + 128){
                temp = base64.substring(i);
                msg = transformation(temp, bp);
                cipher = Encrypt(pkmsk.pk, M, msg, bp, str);
                String s = CipherToString(cipher);
                res = res + ";" + s;

            } else {
                temp = base64.substring(i, i+128);
                msg = transformation(temp, bp);
                cipher = Encrypt(pkmsk.pk, M, msg, bp, str);
                String s = CipherToString(cipher);
                res = res + ";" + s;
            }
        }
        return res;
    }

    public static String ABD(String s, Pairing pairing, PKMSK pkmsk, KEY key, String[] strs) throws Exception {
        String[] strings = s.substring(1).split(";");
        String res = "";
        for(String str: strings){
            Cipher cipher = StringToCipher(str, pairing);
            res = res + recover(Decrypt(pairing, key, cipher, strs));
        }
        return res;
    }


    public static String deal(String filename, KEY key, PKMSK pkmsk, String[] strings, Pairing pairing){
        int[][] M = new int[3][3];
        M[0][0] = -1;M[0][1] = 0;M[0][2] = 0;
        M[1][0] = 1;M[1][1] = 0;M[1][2] = 0;
        M[2][0] = 1;M[2][1] = 0;M[2][2] = 0;
        String base64 = ImageToBase64(filename);
        int len = base64.length();
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        for(int i = 0; i < len; i = i + 128){
            if(len < i +128){
                list.add(base64.substring(i));
            } else {
                list.add(base64.substring(i, i+128));
            }
        }
        HashMap<Integer, String> map = new HashMap<>();
        list.parallelStream().forEach(string -> {
            Element element = transformation(string, pairing);
            Cipher cipher1 = Encrypt(pkmsk.pk, M, element, pairing, strings);
            String s = CipherToString(cipher1);
            cipher1 = StringToCipher(s, pairing);
            try {
                String recover = recover(Decrypt(pairing, key, cipher1, strings));
                int i = list.indexOf(string);
                map.put(i, recover);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
        String s = "";
        for(int i = 0; i < len/128 + 1; i++){
            s = s + map.get(i);
        }
        Base64ToImage(s);
        System.out.println(s.equals(base64));
        System.out.println(System.currentTimeMillis()-stop);
        return s;
    }

    public static void main(String[] args) throws Exception{
        Pairing bp = PairingFactory.getPairing("a.properties");
        PKMSK pkmsk = Setup(bp);
        String[] str = new String[]{"police", "detective", "office"};
        KEY key = Keygen(bp, pkmsk.msk, str);
//        keyBase64 k = keyToBase64(key);
//        key = Base64ToKey(k, bp);
        int[][] M = new int[3][3];
        M[0][0] = -1;M[0][1] = 0;M[0][2] = 0;
        M[1][0] = 1;M[1][1] = 0;M[1][2] = 0;
        M[2][0] = 1;M[2][1] = 0;M[2][2] = 0;




//        String filename = "E:\\competition\\测试\\res\\test-res.png";
        String filename = "E:\\competition\\测试\\res\\250.jpg";
        String base64 = ImageToBase64(filename);
        String res = "";

        Cipher cipher;
        String temp;
        Element msg;
        String r = "";
        int len = base64.length();

        Long start1 = System.currentTimeMillis();

        for(int i = 0; i < len; i = i + 128){
            if(len < i + 128){
                temp = base64.substring(i);
                long start2 = System.currentTimeMillis();
                msg = transformation(temp, bp);
                cipher = Encrypt(pkmsk.pk, M, msg, bp, str);
                r = r + CipherToString(cipher);
//                long stop2 = System.currentTimeMillis();
//                System.out.println("e time: "+(stop2-start2));
//                String s = CipherToString(cipher);
//                cipher = StringToCipher(s, bp);
//                res = res + recover(Decrypt(bp, key, cipher, str));
//                System.out.println("d time: "+(System.currentTimeMillis()-stop2));

            } else {
                temp = base64.substring(i, i+128);
                long start2 = System.currentTimeMillis();
                msg = transformation(temp, bp);
                cipher = Encrypt(pkmsk.pk, M, msg, bp, str);
                r = r + CipherToString(cipher);
//                long stop2 = System.currentTimeMillis();
//                System.out.println("e time: "+(stop2-start2));
//                String s = CipherToString(cipher);
//                Cipher cipher1 = StringToCipher(s, bp);
//                res = res + recover(Decrypt(bp, key, cipher1, str));
//                System.out.println("d time: "+(System.currentTimeMillis()-stop2));
            }
        }
        Long stop1 = System.currentTimeMillis();
        System.out.println("character number: "+len);
        System.out.println("whole time: " + (stop1-start1));
        System.out.println(res.equals(base64));
        System.out.println("cipher length: "+r.length());
        Base64ToImage(res);


//        start = System.currentTimeMillis();
//        List<String> list = new ArrayList<>();
//        for(int i = 0; i < len; i = i + 128){
//            if(len < i +128){
//                list.add(base64.substring(i));
//            } else {
//                list.add(base64.substring(i, i+128));
//            }
//        }
//        HashMap<Integer, String> map = new HashMap<>();
//        list.parallelStream().forEach(string -> {
//            Element element = transformation(string, bp);
//
//            Cipher cipher1 = Encrypt(pkmsk.pk, M, element, bp, str);
//            String s = CipherToString(cipher1);
//            cipher1 = StringToCipher(s, bp);
//            try {
//                String recover = recover(Decrypt(bp, key, cipher1, str));
//                int i = list.indexOf(string);
//                map.put(i, recover);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        stop = System.currentTimeMillis();
//        System.out.println(stop-start);
//        String s = "";
//        for(int i = 0; i < len/128 + 1; i++){
//            s = s + map.get(i);
//        }
//        Base64ToImage(s);
//        System.out.println(s.equals(base64));
//        System.out.println(System.currentTimeMillis()-stop);

    }

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
//
//
//
//
//    public static Cipher StringToCipher(String string, Pairing pairing){
//        Cipher cipher = new Cipher();
//        String[] str = string.split("\\|");
//        cipher.ct_ = pairing.getGT().newElementFromBytes(Base64.getDecoder().decode(str[0]));
//        for(int i = 0; i < 3; i++){
//            cipher.ct0[i] = pairing.getG2().newElementFromBytes(Base64.getDecoder().decode(str[i+1]));
//        }
//        for(int i = 0; i < 9; i++){
//            cipher.cti[i] = pairing.getG1().newElementFromBytes(Base64.getDecoder().decode(str[i+4]));
//            cipher.cti[i] = pairing.getG1().newZeroElement();
//
//        }
//
//        return cipher;
//    }

    public static KEY Keygen(Pairing bp, MSK msk, String[] S){
        Field G = bp.getG1();
        Field H = bp.getG2();
        Field Z = bp.getZr();
        KEY key = new KEY();
        Element[] Msk = msk.getKey();
        Element g = Msk[0];
        Element h = Msk[1];
        Element a1 = Msk[2];
        Element a2 = Msk[3];
        Element b1 = Msk[4];
        Element b2 = Msk[5];
        Element g_d1 = Msk[6];
        Element g_d2 = Msk[7];
        Element g_d3 = Msk[8];
        Element r1 = Z.newRandomElement().getImmutable();
        Element r2 = Z.newRandomElement().getImmutable();
        Element h_b1r1 = h.mulZn(b1.mul(r1)).getImmutable();
        Element h_b2r2 = h.mulZn(b2.mul(r2)).getImmutable();
        Element h_r1r2 = h.mulZn(r1.add(r2)).getImmutable();

        key.set_sk0(new Element[]{h_b1r1, h_b2r2, h_r1r2});

        Element sigma1 = Z.newRandomElement().getImmutable();
        Element sigma2 = sigma1.getImmutable();
        Element sigma3 = sigma2.getImmutable();
        Element sigma_ = Z.newRandomElement().getImmutable();

        for(String y: S){
            Element temp1 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"11")).getBytes()).mulZn(b1.mul(r1).div(a1)).getImmutable();
            Element temp2 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"21")).getBytes()).mulZn(b2.mul(r2).div(a1)).getImmutable();
            Element temp3 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"31")).getBytes()).mulZn(r1.add(r2).div(a1)).getImmutable();
            Element temp4 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"12")).getBytes()).mulZn(b1.mul(r1).div(a2)).getImmutable();
            Element temp5 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"22")).getBytes()).mulZn(b2.mul(r2).div(a2)).getImmutable();
            Element temp6 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(y+"32")).getBytes()).mulZn(r1.add(r2).div(a2)).getImmutable();
            Element g_1 = g.mulZn(sigma1.div(a1)).getImmutable();
            Element g_2 = g.mulZn(sigma2.div(a2)).getImmutable();
            Element t1 = temp1.mul(temp2).mul(temp3).mul(g_1).getImmutable();
            Element t2 = temp4.mul(temp5).mul(temp6).mul(g_2).getImmutable();
            key.set_sky("sk"+y, new Element[]{t1, t2});

        }

        Element temp1 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0111")).getBytes()).mulZn(b1.mul(r1).div(a1)).getImmutable();
        Element temp2 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0121")).getBytes()).mulZn(b2.mul(r2).div(a1)).getImmutable();
        Element temp3 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0131")).getBytes()).mulZn(r1.add(r2).div(a1)).getImmutable();
        Element temp4 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0112")).getBytes()).mulZn(b1.mul(r1).div(a2)).getImmutable();
        Element temp5 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0122")).getBytes()).mulZn(b2.mul(r2).div(a2)).getImmutable();
        Element temp6 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0132")).getBytes()).mulZn(r1.add(r2).div(a2)).getImmutable();
        Element g_1 = g.mulZn(sigma_.div(a1)).getImmutable();
        Element g_2 = g.mulZn(sigma_.div(a2)).getImmutable();
        Element t1 = g_d1.mul(temp1).mul(temp2).mul(temp3).mul(g_1).getImmutable();
        Element t2 = g_d2.mul(temp4).mul(temp5).mul(temp6).mul(g_2).getImmutable();
        key.setG_sigma(new Element[]{g.mulZn(Z.newZeroElement().sub(sigma1)), g.mulZn(Z.newZeroElement().sub(sigma2)), g.mulZn(Z.newZeroElement().sub(sigma3))});
        key.setSk_(new Element[]{t1, t2, g_d3.mul(g.mulZn(Z.newZeroElement().sub(sigma_)))});
        return key;
    }

    public static Cipher Encrypt(PK pk, int[][] M, Element msg, Pairing bp, String[] Pai){

        Field G = bp.getG1();
        Field H = bp.getG2();
        Field Z = bp.getZr();
        Field GT = bp.getGT();
        try{
            Element h = pk.h;
            Element H1 = pk.H1;
            Element H2 = pk.H2;
            Element T1 = pk.T1;
            Element T2 = pk.T2;
            Element s1 = Z.newRandomElement().getImmutable();
            Element s2 = Z.newRandomElement().getImmutable();
            Element H1_s1 = H1.mulZn(s1).getImmutable();
            Element H2_s2 = H2.mulZn(s2).getImmutable();
            Element h_s1s2 = h.mulZn(s1.add(s2)).getImmutable();
            Cipher cipher = new Cipher();
            cipher.ct0 = new Element[]{H1_s1, H2_s2, h_s1s2};
            for(int l = 1; l < 4; l++){
                for(int i = 1; i < 4; i++){
                    Element temp1 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(Pai[i-1]+l+"1")).getBytes()).mulZn(s1).getImmutable();
                    Element temp2 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash(Pai[i-1]+l+"2")).getBytes()).mulZn(s2).getImmutable();
                    Element temp3 = G.newOneElement().getImmutable();
                    for(int j = 1; j < 4; j++){
                        Element tem1 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0"+ j + l +"1")).getBytes()).mulZn(s1).getImmutable();
                        Element tem2 = G.newElementFromBytes(Base64.getEncoder().encodeToString(hash("0"+ j + l +"2")).getBytes()).mulZn(s2).getImmutable();
                        Element tem = tem1.mul(tem2).mulZn(Z.newElement(M[i-1][j-1])).getImmutable();
                        temp3 = temp3.mul(tem).getImmutable();
                    }
                    temp3 = temp3.mul(temp1).mul(temp2).getImmutable();
                    cipher.cti[3*(i-1) + l-1] = temp3;
                }

            }
            cipher.ct_ = T1.mulZn(s1).mul(T2.mulZn(s2)).mul(msg).getImmutable();
            return cipher;
        } catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    public static Element Decrypt(Pairing bp, KEY sk, Cipher cipher, String[] str) throws Exception{
        Field G = bp.getG1();
        Field H = bp.getG2();
        Field GT = bp.getGT();
        Field Z = bp.getZr();

        Element sk01 = sk.sk0[0];
        Element sk02 = sk.sk0[1];
        Element sk03 = sk.sk0[2];
        Element sk1_ = sk.sk_[0];
        Element sk2_ = sk.sk_[1];
        Element sk3_ = sk.sk_[2];
        Element sk11 = sk.sky.get("sk"+str[0])[0];
        Element sk12 = sk.sky.get("sk"+str[0])[1];
        Element sk21 = sk.sky.get("sk"+str[1])[0];
        Element sk22 = sk.sky.get("sk"+str[1])[1];
        Element sk31 = sk.g_sigma[0];
        Element sk32 = sk.g_sigma[1];
        Element sk33 = sk.g_sigma[2];
        Element sk1 = sk.sky.get("sk"+str[2])[0];
        Element sk2 = sk.sky.get("sk"+str[2])[1];

        Element ct01 = cipher.ct0[0];
        Element ct02 = cipher.ct0[1];
        Element ct03 = cipher.ct0[2];
        Element ct11 = cipher.cti[0];
        Element ct12 = cipher.cti[1];
        Element ct13 = cipher.cti[2];
        Element ct21 = cipher.cti[3];
        Element ct22 = cipher.cti[4];
        Element ct23 = cipher.cti[5];
        Element ct31 = cipher.cti[6];
        Element ct32 = cipher.cti[7];
        Element ct33 = cipher.cti[8];
        Element ct_ = cipher.ct_;


        Element temp1 = bp.pairing(ct11.mul(ct21.mul(ct31)), sk01).getImmutable();
        Element temp2 = bp.pairing(ct12.mul(ct22.mul(ct32)), sk02).getImmutable();
        Element temp3 = bp.pairing(ct13.mul(ct23.mul(ct33)), sk03).getImmutable();
        Element num = ct_.mul(temp1).mul(temp2).mul(temp3).getImmutable();

        temp1 = bp.pairing(sk1_.mul(sk11.mul(sk21.mul(sk1))), ct01).getImmutable();
        temp2 = bp.pairing(sk2_.mul(sk12.mul(sk22.mul(sk2))), ct02).getImmutable();
        temp3 = bp.pairing(sk3_.mul(sk31.mul(sk32.mul(sk33))), ct03).getImmutable();
        Element den = temp1.mul(temp2).mul(temp3).getImmutable();


        Element res = num.div(den).getImmutable();
        return res;
    }

}
