package com.komsoft.shop2.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encoding {
    private static final String SALT = "com.itea";
    private static final String BCRYPT_SALT = BCrypt.gensalt(13);
    static Logger logger = Logger.getLogger(Encoding.class.getName());

    private static String getHashFromDigest(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String md5Encryption(String inputData) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(StandardCharsets.UTF_8.encode(inputData));
            result = getHashFromDigest(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, String.format("[Encoding] %s", e.getMessage()));
        }
        return result;
    }

    public static String sha512Encryption(String inputData) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(StandardCharsets.UTF_8.encode(inputData));
            result = getHashFromDigest(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, String.format("[Encoding] %s", e.getMessage()));
        }
        return result;
    }

    public static String bCryptEncryption(String inputData) {
        return BCrypt.hashpw(inputData, BCRYPT_SALT);
    }

    public static String md5EncryptionWithSalt(String inputData) {
        return md5Encryption(inputData + SALT);
    }

    public static String sha512EncryptionWithSalt(String inputData) {
        return sha512Encryption(inputData + SALT);
    }
}
