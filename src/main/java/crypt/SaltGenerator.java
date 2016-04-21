package crypt;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by User on 21.04.2016.
 */
public class SaltGenerator {

    public static String generate() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }
}
