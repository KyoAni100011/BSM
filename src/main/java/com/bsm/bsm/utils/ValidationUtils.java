package com.bsm.bsm.utils;

import javafx.collections.ObservableList;

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
        int introductionLength = introduction.length();
        boolean checkWrongField = introductionLength > 255 ;
        if (checkWrongField) {
            return "Introduction should be less than 255 characters.";
        }
        return introduction.isEmpty() ? "Please enter "+ field + " introduction." : null;
    }

    public static String validateDescription(String description, String field) {
        if (description.length() > 255) return "Description should be less than 255 characters.";
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

    public static String validateReleaseDay(String dob, String field) {
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        if (dob.isEmpty()) {
            return "Please enter " + field + " release day.";
        }
        if (!dob.matches(dobRegex)) {
            return "Invalid date format. Please use dd/mm/yyyy.";
        } else {
            return null;
        }
    }

    public static String validateImportDay(String dob) {
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        if (dob.isEmpty()) {
            return "Please enter " + " import day.";
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
        if(address.isEmpty()) return "Please enter "+ field +" address.";
        if (address.length() > 255) return "Address should be less than 255 characters.";
        return null;
    }

    public static String validateCategory(ObservableList<String> category, String field) {
        return category.isEmpty() ? "Please choose "+ field +" category." : null;
    }

    public static String validateAuthor(ObservableList<String> author, String field) {
        return author.isEmpty() ? "Please choose "+ field +" name." : null;
    }

    public static String validateLanguage(String languages, String field) {
        return languages == null ? "Please choose "+ field +" language." : null;
    }
    public static String validatePublisher(String publisher, String field) {
        return publisher == null ? "Please choose "+ field  : null;
    }
    public static String validateQuantity(String quantity, String field) {
        if (quantity.isEmpty()) {
            return "Please enter "+ field +" quantity.";
        }
        if (!quantity.matches("^[0-9]+$")) {
            return "Quantity should contain only number.";
        }
        return null;
    }
    public static String validatePrice(String price, String field) {
        if (price.isEmpty()) {
            return "Please enter "+ field +" price.";
        }
        if (!price.matches("^[0-9]+$")) {
            return "Price should contain only number.";
        }
        return null;
    }

    public static boolean validateEmailRegex(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255;
    }

    public static String validateCategoryName(String name) {
        if (name.isEmpty()) {
            return "Please enter category name.";
        } else if (name.length() < 3 || name.length() > 255) {
            return "Category name should be between 3 and 255 characters.";
        } else {
            return null;
        }

    }
}
