package org.semicorp.mscitemapi.utils;

import org.apache.commons.text.StringEscapeUtils;

import java.util.Locale;

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
        String specialCharacters = ".*[&\\W].*";

        // Check if string has special characters
        return input.matches(specialCharacters);
    }
}
