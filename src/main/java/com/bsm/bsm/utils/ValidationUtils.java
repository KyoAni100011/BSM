package com.bsm.bsm.utils;

public class ValidationUtils {
    public static String validateEmail(String email) {
        if (email.isEmpty()) {
            return "Please enter user email.";
        } else if (!email.toLowerCase().endsWith("@bms.com")) {
            return "Email domain must be '@bms.com'.";
        } else {
            String[] parts = email.split("@");
            if (parts.length != 2 || parts[0].isEmpty()) {
                return "Invalid email format.";
            } else {
                String[] usernameParts = parts[0].split("\\.");
                if (usernameParts.length != 2) {
                    return "Username must have the format '.<role>'.";
                } else {
                    if (!usernameParts[0].isEmpty() && (usernameParts[1].equalsIgnoreCase("admin") || usernameParts[1].equalsIgnoreCase("employee"))) {
                        return null; // Email is valid
                    } else {
                        return "Invalid username role.";
                    }
                }
            }
        }
    }

    public static String validateFullName(String fullName, String field) {
        if (fullName.isEmpty()) {
            return "Please enter " + field + " name.";
        } else if (fullName.matches(".*\\d.*")) {
            return "Full name should not contain numbers.";
        } else {
            return null;
        }
    }

    public static String validateIntroduction(String introduction,  String field) {
        return introduction.isEmpty() ? "Please enter "+ field + " introduction." : null;
    }

    public static String validateDescription(String description, String field) {
       return description.isEmpty() ? "Please enter " + field + " description" : null;
    }


    public static String validateDOB(String dob, String field) {
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        if (dob.isEmpty()) {
            return "Please enter " + field + " date of birth.";
        }
        if (!dob.matches(dobRegex)) {
            return "Invalid date format. Please use dd/mm/yyyy.";
        } else {
            return null;
        }
    }

    public static String validatePassword(String password, String field) {
        if (password.isEmpty()) {
            return "Please enter " + field +" password.";
        }
        if (password.length() < 8 || password.length() > 255) {
            return "Your password should be between 8 and 255 characters.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Your password should include at least one uppercase letter (A-Z).";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Your password should include at least one lowercase letter (a-z).";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Your password should include at least one number (0-9).";
        }
        if (!password.matches(".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*")) {
            return "Your password should include at least one special character.";
        }
        return null;
    }

    public static String validatePhone(String phone, String field) {
        if (phone.isEmpty()) {
            return "Please enter " + field + " phone number.";
        }
        if (!phone.matches("^[0-9]{10}$")) {
            return "Please enter a 10-digit phone number..";
        }
        return null;
    }

    public static String validateAddress(String address, String field) {
        return address.isEmpty() ? "Please enter "+ field +" address." : null;
    }


    public static boolean validateEmailRegex(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255;
    }
}
