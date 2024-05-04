package com.bsm.bsm.utils;

import java.text.DecimalFormat;

public class convertProvider {
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] reverseHexString(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }

        return data;
    }

    public String formatNumber(String numberString) {
        try {
            // Remove commas from the input string
            String cleanNumberString = numberString.replaceAll(",", "");

            // Check if the clean string is empty
            if (cleanNumberString.isEmpty()) {
                return "Invalid input";
            }

            // Parse the clean string into a double
            double number = Double.parseDouble(cleanNumberString);

            // Create a DecimalFormat object to format the number
            DecimalFormat formatter = new DecimalFormat("#,###");

            // Return the formatted number
            return formatter.format(number);
        } catch (NumberFormatException e) {
            // Handle the case where the input string is not a valid number
            e.printStackTrace();
            return "Invalid input";
        }
    }

}
