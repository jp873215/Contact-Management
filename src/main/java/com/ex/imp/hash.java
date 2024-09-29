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

        // Hash the provided password with the stored salt
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();

        // Convert the computed hash to a Base64 string
        String computedHash = Base64.getEncoder().encodeToString(hashedPassword);
        // Compare the computed hash with the stored hash
        return computedHash.equals(storedHash);
    }
}
