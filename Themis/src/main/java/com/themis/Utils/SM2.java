package com.themis.Utils;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SM2 {

    public final static String puk = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEIwQwAThdGTb41VC1iZSD1NaINbiggDuR3JOHvY3EjMh/D8ykrROID1XpV2krQgu5Tq/Y6OBxqVjYxOrNSruN/g==";
    public final static String prk = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgPvLQfzptB7X9KYm4Cs6bF8japGNFBk9rqly4AWBX/3ygCgYIKoEcz1UBgi2hRANCAAQjBDABOF0ZNvjVULWJlIPU1og1uKCAO5Hck4e9jcSMyH8PzKStE4gPVelXaStCC7lOr9jo4HGpWNjE6s1Ku43+";

    String DpPuk = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEIwQwAThdGTb41VC1iZSD1NaINbiggDuR3JOHvY3EjMh/D8ykrROID1XpV2krQgu5Tq/Y6OBxqVjYxOrNSruN/g==";
    String DpPrk = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgPvLQfzptB7X9KYm4Cs6bF8japGNFBk9rqly4AWBX/3ygCgYIKoEcz1UBgi2hRANCAAQjBDABOF0ZNvjVULWJlIPU1og1uKCAO5Hck4e9jcSMyH8PzKStE4gPVelXaStCC7lOr9jo4HGpWNjE6s1Ku43+";

    String AuPuk = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEBG/HUEW/S4PvECC0B+6/hqTc9mH9e10p83jGaVkDjsGR2OWTxNvVsITx/HiDkm7aEOH+xn0Aw6X1I9tJmAVVxA==";
    String AuPrk = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgVAiiKaBszEG270zjn4CTYqCXzAh91P7V0h8RTmNQYFSgCgYIKoEcz1UBgi2hRANCAAQEb8dQRb9Lg+8QILQH7r+GpNz2Yf17XSnzeMZpWQOOwZHY5ZPE29WwhPH8eIOSbtoQ4f7GfQDDpfUj20mYBVXE";
//    public static void main(String[] args) {
//        Security.addProvider(new BouncyCastleProvider());
//        try{
//
//
//
//
//            KeyPair keyPair = generateKeyPair();
//            PublicKey publicKey = keyPair.getPublic();
//            PrivateKey privateKey = keyPair.getPrivate();
//            System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
//            System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }

//    public static AsymmetricCipherKeyPair generateKeyPair() {
//        X9ECParameters ecParameters = ECUtil.getNamedCurveByName("sm2p256v1");
//        ECDomainParameters ecParams = new ECDomainParameters(
//                ecParameters.getCurve(),
//                ecParameters.getG(),
//                ecParameters.getN(),
//                ecParameters.getH()
//        );
//        ECKeyPairGenerator generator = new ECKeyPairGenerator();
//        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(ecParams, new SecureRandom());
//        generator.init(keyGenParams);
//        return generator.generateKeyPair();
//    }

    public static KeyPair generateKeyPair(){
        ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            kpg.initialize(sm2Spec, new SecureRandom());

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return kpg.generateKeyPair();

    }

    public static PublicKey stringToPk(String puk){
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(puk));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey stringToPr(String prk){
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(prk));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String Encrypt(String puk, String message){
        try {
        ECPublicKeyParameters publicKey = (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(stringToPk(puk));
        SM2Engine sm2Engine = new SM2Engine();
        ParametersWithRandom parametersWithRandom = new ParametersWithRandom(publicKey, new SecureRandom());
        sm2Engine.init(true, parametersWithRandom);
        byte[] plain = message.getBytes();
        byte[] cipher = sm2Engine.processBlock(plain, 0, plain.length);
        return Base64.getEncoder().encodeToString(cipher);
        } catch (InvalidCipherTextException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String Decrypt(String prk, String message){
        try {
            ECPrivateKeyParameters privateKey = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(stringToPr(prk));
            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(false, privateKey);
            byte[] cipher = Base64.getDecoder().decode(message);
            byte[] plain = sm2Engine.processBlock(cipher, 0, cipher.length);
            return new String(plain);
        } catch (InvalidCipherTextException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
}
