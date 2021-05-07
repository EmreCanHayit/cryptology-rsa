package com.company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.security.SecureRandom;
import java.util.Random;

public class Main {
    static char[] messageASCII;
    static String message;
    static int[] encryptASCII;
    static char[] testEncryptASCII;

    public static void main(String[] args) {
        cryptology();
    }

    public static void cryptology() {
        int bitRange = 12;
        BigInteger p, q, e, d, n, phi;

        p = BigInteger.probablePrime(bitRange, new Random());

        do {
            q = BigInteger.probablePrime(bitRange, new Random());
        } while (p.equals(q));

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("--------------------------");

        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        System.out.println("n = " + n);
        System.out.println("m/phi = " + phi);
        System.out.println("--------------------------");

        e = findOpenE(phi);
        d = findClosedD(phi, e);

        System.out.println("Açık anahtar [" + n + ", " + e + "]");
        System.out.println("Kapalı anahtar [" + n + ", " + d + "]");
        System.out.println("--------------------------");

        message = "Aşırı önemli gizli mesaj denemesi";
        System.out.println("Şifrelenecek olan metin : " + message);

        encrypt(message, e, n);
        System.out.println("Şifrelenmiş metin : " + String.valueOf(messageASCII));

        decrypt(encryptASCII, d, n);
        System.out.println("Şifresi çözülen metin : " + String.valueOf(messageASCII));
    }

    public static void encrypt(String message, BigInteger e, BigInteger n) {
        messageASCII = new char[message.length()];
        encryptASCII = new int[message.length()];
        testEncryptASCII = new char[message.length()]; // test
        int messageBuffer, i;
        BigInteger buffer;
        for (i = 0; i < message.length(); i++) {
            buffer = BigInteger.valueOf(message.charAt(i));
            buffer = buffer.modPow(e, n);

            messageBuffer = buffer.intValue();
            messageASCII[i] = (char) messageBuffer;
            encryptASCII[i] = messageBuffer;
            testEncryptASCII[i] = (char) messageBuffer; // test
        }
    }

    public static void decrypt(int[] message, BigInteger d, BigInteger n) {
        BigInteger buffer;
        int messageBuffer;
        int i;
        for (i = 0; i < message.length; i++) {
            buffer = BigInteger.valueOf(message[i]);
            buffer = buffer.modPow(d, n);

            messageBuffer = buffer.intValue();
            messageASCII[i] = (char) messageBuffer;
        }
    }

    public static BigInteger findOpenE(BigInteger phi) {
        BigInteger i;

        do {
            i = new BigInteger(phi.bitLength(), new SecureRandom()).mod(phi);
        } while ((i.gcd(phi)).compareTo(BigInteger.ONE) != 0);
        return i;
    }

    public static BigInteger findClosedD(BigInteger phi, BigInteger openE) {
        BigDecimal x, i, counter = BigDecimal.ONE, decimalPhi, decimalE;
        decimalPhi = new BigDecimal(phi);
        decimalE = new BigDecimal(openE);
        boolean isInteger = false;
        do {
            x = counter.multiply(decimalPhi);
            x = x.add(BigDecimal.ONE);
            x = x.divide(decimalE, MathContext.DECIMAL64);
            counter = counter.add(BigDecimal.ONE);

            if (x.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
                isInteger = true;
            }
        } while (!isInteger || (x.compareTo(BigDecimal.ZERO) < 0) || x.compareTo(decimalE) == 0);
        return x.toBigInteger();
    }
}
