package com.fa2id.erpapp.repository;

import com.fa2id.erpapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerByCustomerEmail(String customerEmail);
}
