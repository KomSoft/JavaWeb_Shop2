package com.komsoft.shop2;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.model.UserRegisteringData;
import com.komsoft.shop2.repository.UserRepository;

public class MainRunner {

    public static void main(String[] args) {
/*
        UserRepository userRepository = new UserRepository();
        UserRegisteringData user;
        String login = "bcrypt@com.com";
        String hashedPassword;
        String savedPassword;
        String password = "qweR1234";
        try {
            userRepository.getConnection();
            user = userRepository.getUserByLogin(login);
            hashedPassword = user.getEncryptedPassword();
            savedPassword = user.getSavedPassword();
            System.out.println("Testing password: " + password);
            System.out.println("hashed password: " + hashedPassword);
            System.out.println(" saved password: " + savedPassword);
            System.out.println("user.isPasswordCorrect(password): " + user.isPasswordCorrect(password));
            System.out.println("user.isPasswordsEquals()        : " + user.isPasswordsEquals());

        } catch (DataBaseException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                userRepository.closeConnection();
            } catch (DataBaseException e) {
                throw new RuntimeException(e);
            }
        }
*/
    }
}
