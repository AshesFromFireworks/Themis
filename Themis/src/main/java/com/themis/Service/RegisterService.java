package com.themis.Service;

import com.themis.Utils.Hash;
import com.themis.Utils.ImgBase64;
import com.themis.Utils.SM2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class RegisterService {
    public static final String pk_n_s = "110308618178505418532709119276460436790871277311083233176083810372313151461" +
            "4883401673597883392903397476260928254465182207080597033819018132137976551931291963534778488072321798148" +
            "9974511632580443649024708853573571591161079683137266819982488619824023837477745796890083143179649089100" +
            "5079224190084902423587967641";
    public static final String pk_e_s = "788868828669748325350698511806432380070980177095403114083872165307337515590" +
            "8583700617189403145141715557736577855707357417034416307313857582290590437145183";

    private static final String prk1_s = "16836366829141555130182476762271946303271620205036240314554444066232307590" +
            "6245839050980996822970706404494557782782231227346281896617564865107883493023110326927167464530982793992" +
            "5289854706133423201661038793110182807878780904001392548316375006865347796697095013417331112443819231900" +
            "2497528685468564812649732363";
    private static final String prk2_s = "16836366829141555130182476762271946303271620205036240314554444066232307590" +
            "6245839050980996822970706404494557782782231227346281896617564865107883493023110326927167464530982793992" +
            "5289854706133423201661038793110182807878780904001392548316375006865347796697095013417331112472755579372" +
            "5962746233650824695323261514";
    private static final String prk3_s = "16836366829141555130182476762271946303271620205036240314554444066232307590" +
            "6245839050980996822970706404494557782782231227346281896617564865107883493023110326927167464530982793992" +
            "5289854706133423201661038793110182807878780904001392548316375006865347796697095013417331112501691926844" +
            "9427963781833084577996790665";
    private static final String prk4_s = "16836366829141555130182476762271946303271620205036240314554444066232307590" +
            "6245839050980996822970706404494557782782231227346281896617564865107883493023110326927167464530982793992" +
            "5289854706133423201661038793110182807878780904001392548316375006865347796697095013417331112530628274317" +
            "2893181330015344460670319816";

    public static final BigInteger delta = BigInteger.valueOf(24);

    public static BigInteger puk_n;
    public static BigInteger puk_e;
    public static int const_n0 = 4;
    public static int const_t = 2;
    private static final BigInteger prk_1;
    private static final BigInteger prk_2;
    private static final BigInteger prk_3;
    private static final BigInteger prk_4;
    static {
        puk_n = new BigInteger(pk_n_s);
        puk_e = new BigInteger(pk_e_s);
        prk_1 = new BigInteger(prk1_s);
        prk_2 = new BigInteger(prk2_s);
        prk_3 = new BigInteger(prk3_s);
        prk_4 = new BigInteger(prk4_s);
    }

    public static void main(String[] args) {

        for(int j = 0; j < 1; j++){
            BigInteger[] sigma_i = new BigInteger[2];
            Long start = System.currentTimeMillis();

            for(int i = 0; i < 2; i++){
                HashMap<String, String> reg = register("yhldcasdddddd" + j, i);
                sigma_i[i] = new BigInteger(reg.get("cre"));
                String t1 = reg.get("cre") + reg.get("z") + reg.get("hv");
//                sigma_i[i] = new BigInteger(SM2.Decrypt(SM2.prk, SM2.Encrypt(SM2.puk, sigma_i[i].toString())));
            }
            BigInteger DAC = Comb(sigma_i, const_t, const_n0, puk_n);
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\20.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\50.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\80.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\100.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\150.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\200.jpg")).length());
            System.out.println(SM2.Encrypt(SM2.puk, ImgBase64.ImageToBase64("E:\\competition\\测试\\res\\250.jpg")).length());
            Long stop = System.currentTimeMillis();
            System.out.println(stop-start);
            Boolean res  = Verify(DAC, "yhldca"+j, puk_n, puk_e, delta);
            System.out.println("verify result: " + res);
    }
    }



    //    public RegisterService(String message, int n0, int t){
//
//
//        BigInteger x = Hash.Hash_BigInteger(message);
//        BigInteger[] prk = new BigInteger[]{prk_1, prk_2, prk_3, prk_4};
//        BigInteger[] sigma_i = new BigInteger[4];
//        for(int i = 0; i < t; i++){
//            sigma_i[i] = x.modPow(prk[i].multiply(delta.multiply(BigInteger.valueOf(2))), puk_n);
//        }
//        BigInteger DAC = Comb(sigma_i, t, n0, puk_n);
//        Boolean res  = Verify(DAC, message, puk_n, puk_e, delta);
//        System.out.println("verify result: " + res);
//        if(res){
//            System.out.println("Register success!");
//            System.out.println("DAC is: " + DAC);
//
//        }
//    }
    public RegisterService(){}

    public static BigInteger[] getPrk(){
        return new BigInteger[]{prk_1, prk_2, prk_3, prk_4};
    }


    public static HashMap<String, String> register(String message, int index){
        BigInteger x = Hash.Hash_BigInteger(message);
        BigInteger[] prk = new BigInteger[]{prk_1, prk_2, prk_3, prk_4};
        BigInteger sigma_i = x.modPow(prk[index].multiply(delta.multiply(BigInteger.valueOf(2))), puk_n);
        HashMap<String, String> map = new HashMap<>();
        BigInteger v = BigInteger.probablePrime(64, new Random());
        BigInteger r = BigInteger.probablePrime(64, new Random());
        BigInteger rm_4 = x.modPow(BigInteger.valueOf(4).multiply(delta), puk_n);
        BigInteger vi = v.modPow(prk[index], puk_n);
        BigInteger x_2 = x.modPow(x, puk_n);
        BigInteger v_ = v.modPow(r, puk_n);
        BigInteger rm_ = rm_4.modPow(r, puk_n);
        String res = v.toString() + rm_4 + vi + x_2;
        String res1 = res + v_ + rm_;
        BigInteger hv = Hash.Hash_BigInteger(res1);
        BigInteger z = hv.multiply(prk[index]).add(r);
        BigInteger temp1 = v.modPow(z, puk_n).multiply(vi.modPow(hv.negate(), puk_n)).mod(puk_n);
        BigInteger temp2 = rm_4.modPow(z, puk_n).multiply(sigma_i.modPow(hv.multiply(BigInteger.TWO).negate(), puk_n)).mod(puk_n);
        String res2 = res + temp1 + temp2;
//        System.out.println(Hash.Hash_BigInteger(res2).equals(Hash.Hash_BigInteger(res1)));
        map.put("cre", String.valueOf(sigma_i));
        map.put("z", String.valueOf(z));
        map.put("hv", String.valueOf(hv));
        return map;
    }

    public static BigInteger Comb(BigInteger[] sigma_i, int t, int n0, BigInteger n){
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 0; i < t; i++){
            BigInteger temp = BigInteger.valueOf(2).multiply(BigInteger.valueOf(lan(i+1, t, n0)));
            result = result.multiply(sigma_i[i].modPow(temp, n)).mod(n);
        }
        return result;
    }

    public static long lan(int i, int t, int n){
        long num = 1;
        while(n > 0){
            num = num * n;
            n--;
        }
        for (int j = 1; j < t + 1; j++){
            if (j != i){
                num = num * (-j) / (i-j);
            }
        }
        return num;
    }

    public static Boolean Verify(BigInteger DAC, String message, BigInteger n, BigInteger e,BigInteger delta){
        BigInteger temp1 = DAC.modPow(e, n);
        BigInteger x = Hash.Hash_BigInteger(message);
        BigInteger temp2 = x.modPow(BigInteger.valueOf(4).multiply(delta.multiply(delta)), n);
//        System.out.println(temp1);
//        System.out.println(temp2);
        return temp1.equals(temp2);
    }






}
