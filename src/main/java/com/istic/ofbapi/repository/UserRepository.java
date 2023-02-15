package com.istic.ofbapi.repository;

import com.istic.ofbapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
