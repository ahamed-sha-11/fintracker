package com.ahamed.fintracker.repository;

import com.ahamed.fintracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}