package com.komsoft.shop2.dao.xml;

import com.komsoft.shop2.dao.factory.DAOFactory;
import com.komsoft.shop2.dao.repository.CustomerDAO;

public class XMLDAOFactory extends DAOFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return new XMLCustomerDAO();
    }
}
