package com.komsoft.shop2.model;

import com.komsoft.shop2.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class AuthorizedUser {
    private final String OK_MESSAGE = "<font color='#00FF00'>&#10004;</font>";
    private static final String REGEXP_EMAIL = "^\\w+([\\.\\-_]?\\w+)*@(\\w+[\\.\\-_]?)+\\.[\\w+]{2,4}$";
    //      include lower, upper case letters, and minimum one 1 digit
    private static final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d)(?!.*[\\s]).{8,}$";
    //      include lower, upper case letters, one special character #?!@$%^*&- and minimum 2 digits
    //    private static final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d[^\\d]?\\d)(?=.*?[#?!@$%^*&\\-])(?!.*[\\s]).{8,}$";
    private String login;
    private String password;
    private String fullName;
    private String region;
    private String gender;
    private String comment;
    private String agreement;
    private static ArrayList<String> errors;
    private boolean isCorrect;

    public AuthorizedUser() {
        login = null;
        password = null;
        fullName = null;
        region = null;
        gender = null;
        comment = null;
        agreement = null;
        isCorrect = false;
        errors = null;
    }

    public AuthorizedUser(HttpServletRequest request) {
        this.checkUserRegisteringData(request);
    }

    private static boolean isLoginCorrect(String email) {
        return email.matches(REGEXP_EMAIL);
    }

    public UserRegisteringData checkUserRegisteringData(HttpServletRequest request) {
        login = request.getParameter("login");
        password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");
        fullName = request.getParameter("fullName");
        region = request.getParameter("region");
        gender = request.getParameter("gender");
        comment = request.getParameter("comment");
        agreement = request.getParameter("agreement");
        isCorrect = true;
        errors = new ArrayList<>();
        if (login == null || login.isEmpty()) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Login is empty!</font>");
        } else {
            if (login.matches(REGEXP_EMAIL)) {
                errors.add(OK_MESSAGE);
            } else {
                isCorrect = false;
                errors.add("<font color='#FF0000'>Login has to be a correct e-mail address</font>");
            }
        }
        if (password == null || password.isEmpty()) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Password is empty!</font>");
        } else {
            if (password.matches(REGEXP_PASSWORD)) {
                errors.add(OK_MESSAGE);
            } else {
                isCorrect = false;
                errors.add("<font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font>");
            }
        }
        if (rePassword == null || rePassword.isEmpty()) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Re-password is empty!</font>");
        } else {
            if (rePassword.matches(REGEXP_PASSWORD)) {
                if (rePassword.equals(password)) {
                    errors.add(OK_MESSAGE);
                } else {
                    isCorrect = false;
                    errors.add("<font color='#FF0000'>Passwords don't match!</font>");
                }
            } else {
                isCorrect = false;
                errors.add("<font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font>");
            }
        }
        if (fullName == null || fullName.trim().length() == 0) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Name is blank!</font>");
        } else {
            fullName = fullName.trim();
            errors.add(OK_MESSAGE);
        }
        if (region == null || region.trim().length() == 0) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>A region need to be selected!</font>");
        } else {
            errors.add(OK_MESSAGE);
        }
        if (gender == null || gender.trim().length() == 0) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Gender isn't selected!</font>");
        } else {
            errors.add(OK_MESSAGE);
        }
        if (comment == null || comment.trim().length() == 0) {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Please write a comment</font>");
        } else {
            errors.add(OK_MESSAGE);
        }
        if ("ON".equalsIgnoreCase(agreement)) {
            errors.add(OK_MESSAGE);
        } else {
            isCorrect = false;
            errors.add("<font color='#FF0000'>Please mark 'Glory to Ukraine'</font>");
        }
        return getUserRegisteringData();
    }

    public static boolean isUserRegisteringDataCorrect(UserRegisteringData user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("[CheckUser] Object User is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || !isLoginCorrect(user.getLogin())) {
            throw new ValidationException("[CheckUser] User login is null, empty or isn't valid e-mail");
        }
        if (user.getEncryptedPassword() == null || user.getEncryptedPassword().isEmpty()) {
            throw new ValidationException("[CheckUser] User password is null or empty");
        }
        if (user.getFullName() == null || user.getFullName().trim().length() == 0) { // isBlank() is not supported
            throw new ValidationException("[CheckUser] User full name is null or empty");
        }
        if (user.getRegion() == null || user.getRegion().trim().length() == 0) {
            throw new ValidationException("[CheckUser] User region is null or empty");
        }
        if (user.getGender() == null || user.getGender().trim().length() == 0) {
            throw new ValidationException("[CheckUser] User gender is null or empty");
        }
        if (user.getComment() == null || user.getComment().trim().length() == 0) {
            throw new ValidationException("[CheckUser] User comment is null or empty");
        }
        return true;
    }

    public UserRegisteringData getUserRegisteringData() {
        if (isCorrect) {
            return new UserRegisteringData(login, password, fullName, region, gender, comment);
        } else {
            return null;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRegion() {
        return region;
    }

    public String getGender() {
        return gender;
    }

    public String getComment() {
        return comment;
    }

    public String getAgreement() {
        return agreement;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}