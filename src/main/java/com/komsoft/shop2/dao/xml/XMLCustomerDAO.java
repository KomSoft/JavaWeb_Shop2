package com.komsoft.shop2.dao.xml;

import com.komsoft.shop2.dao.model.Customer;
import com.komsoft.shop2.dao.repository.CustomerDAO;

public class XMLCustomerDAO implements CustomerDAO {

    public XMLCustomerDAO() {
    }

    @Override
    public Customer findCustomerById(int id) {
        Customer customer = new Customer();
        customer.setName("XML_Customer");
        customer.setRegion("XML_Region");
        return customer;
    }


}
