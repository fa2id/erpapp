package com.fa2id.erpapp.repository;

import com.fa2id.erpapp.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByRole(String role);
}
