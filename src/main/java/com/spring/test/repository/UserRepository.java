package com.spring.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.test.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Iterable<User> findByUserNameContaining(String username);
}
