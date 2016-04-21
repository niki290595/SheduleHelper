package crypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashText {
    public static String getHash(String text, String salt) {
        String s = text + salt;
        return sha512(s);
    }

    private static String sha512(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("algorithm sha-512 non found");
            System.exit(-1);
        }
        md.update(str.getBytes());
        byte byteData[] = md.digest();
        
        StringBuffer hashCodeBuffer = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            hashCodeBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return hashCodeBuffer.toString();
    }
}
