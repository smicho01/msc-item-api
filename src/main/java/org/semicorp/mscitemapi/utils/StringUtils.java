package org.semicorp.mscitemapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Locale;

@Slf4j
public class StringUtils {

    public static String cleanString(String input){
        input = StringEscapeUtils.escapeHtml4(input);
        input = StringEscapeUtils.escapeCsv(input);
        input = StringEscapeUtils.escapeJava(input);

        return input.trim().toLowerCase(Locale.ROOT);
    }


    /**
     * Checks if the given string contains any special characters.
     *
     * @param input the string to check
     * @return true if the string contains special characters, false otherwise
     */
    public static boolean incorrectString(String input) {
        // Regular expression for special characters
        String specialCharacters = ".*[^\\s\\w&].*";

        // Check if string has special characters
        return input.matches(specialCharacters);
    }

    public static String generateHash(String... strings) {
        StringBuilder combinedStrings = new StringBuilder();
        for(String str : strings) {
            combinedStrings.append(str);
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.warn("Error while creating SHA-256 digest");
            throw new RuntimeException(e);
        }
        byte[] hashBytes = digest.digest(combinedStrings.toString().getBytes(StandardCharsets.UTF_8));
        return byteArrayToHexadecimal(hashBytes);
    }

    private static String byteArrayToHexadecimal(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
