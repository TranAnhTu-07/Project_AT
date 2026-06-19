package vn.edu.nlu.fit.projectweb.utils;

public class Validator {
    private static final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
    public static boolean checkPassword(String password) {
        if (password == null) return false;
        return password.matches(PASS_REGEX);
    }
}
