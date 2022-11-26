package com.komsoft.shop2.dao;

import com.komsoft.shop2.dao.factory.DAOFactory;
import com.komsoft.shop2.dao.repository.CustomerDAO;

public class Main {

    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.XML);
        CustomerDAO repository = factory.getCustomerDAO();
        System.out.println(repository.findCustomerById(3));
    }
}
