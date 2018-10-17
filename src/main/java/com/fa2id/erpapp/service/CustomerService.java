package com.fa2id.erpapp.service;

import com.fa2id.erpapp.model.Customer;
import com.fa2id.erpapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByEmail(String customerEmail) {
        return customerRepository.findCustomerByCustomerEmail(customerEmail);
    }

    public Customer saveOrGetCustomer(Customer customer) {
        Customer existedCustomer = getCustomerByEmail(customer.getCustomerEmail());
        if (existedCustomer == null)
            return customerRepository.save(customer);
        else
            return existedCustomer;
    }
}
