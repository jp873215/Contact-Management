package com.ex.imp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class hash {

    public String[] hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        
        
        md.update(salt);
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();

        return new String[]{saltString, Base64.getEncoder().encodeToString(hashedPassword)};
    }

    public boolean verifyPassword(String storedSalt, String storedHash, String password) throws NoSuchAlgorithmException {
        byte[] salt = Base64.getDecoder().decode(storedSalt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();

        String computedHash = Base64.getEncoder().encodeToString(hashedPassword);
        
        return computedHash.equals(storedHash);
    }
}
