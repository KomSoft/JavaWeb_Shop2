package com.komsoft.shop2.repository;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.model.UserRegisteringData;

public interface UserDAO {

    String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException;
    UserRegisteringData getUserByLogin(String login) throws DataBaseException;
    UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException, ValidationException;

}
