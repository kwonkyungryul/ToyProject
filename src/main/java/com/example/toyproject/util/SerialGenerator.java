package com.example.toyproject.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SerialGenerator {
    public static String generateSerial(String academyName) {
        LocalDateTime now = LocalDateTime.now();
        String nowToString = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String combinedInfo = academyName + nowToString;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(combinedInfo.getBytes(StandardCharsets.UTF_8));
            String hashString = bytesToHex(encodedhash);

            // 암호화된 결과에서 12자리만 반환
            return (hashString
                    .substring(0, 4) + "-" + hashString.substring(4, 8) + "-" + hashString.substring(8, 12)
                    ).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
