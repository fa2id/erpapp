package com.fa2id.erpapp.Repository;

import com.fa2id.erpapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
