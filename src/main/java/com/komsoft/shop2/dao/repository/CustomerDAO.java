package com.komsoft.shop2.dao.repository;

import com.komsoft.shop2.dao.model.Customer;

public interface CustomerDAO {
    Customer findCustomerById(int id);
}
