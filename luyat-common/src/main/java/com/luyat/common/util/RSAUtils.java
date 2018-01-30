package com.luyat.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

public class RSAUtils {
    private static final KeyPair keyPair = initKey();
    private static Logger logger = LogManager.getLogger(RSAUtils.class.getName());
    
    private static KeyPair initKey() {  
        try {  
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();  
            Security.addProvider(provider);  
            SecureRandom random = new SecureRandom();  
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);  
            generator.initialize(1024, random);   
            return generator.generateKeyPair();  
        } catch(Exception e) {  
        	logger.error("initKey错误:",e); 
        	return null;
        }  
    }  
    /** 
     * 产生public key 
     * @return public key字符串 
     */  
    public static String generateBase64PublicKey() {  
        PublicKey publicKey = (RSAPublicKey)keyPair.getPublic();  
        return new String(Base64.encodeBase64(publicKey.getEncoded()));  
    }  
    /** 
     * 解密数据 
     * @param string 需要解密的字符串 
     * @return  破解之后的字符串 
     */  
    public static String decryptBase64(String string) {  
        return new String(decrypt(Base64.decodeBase64(string.getBytes())));  
    }  
  
    private static byte[] decrypt(byte[] byteArray) {  
        try {  
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();  
            Security.addProvider(provider);  
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);  
            PrivateKey privateKey = keyPair.getPrivate();  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] plainText = cipher.doFinal(byteArray);  
            return plainText;  
        } catch(Exception e) {  
        	logger.error("decrypt错误:",e); 
        	return null;
        }  
    }  
}
