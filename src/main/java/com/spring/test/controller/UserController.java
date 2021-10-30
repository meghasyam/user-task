package com.spring.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.model.User;
import com.spring.test.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository UserRepository;

	// #### Create user

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User User) {
		try {
			User _User = UserRepository.save(new User(User.getUserName(), User.getFirstName(), User.getLastName()));
			return new ResponseEntity<>(_User, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// #### Update user

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User User) {
		Optional<User> UserData = UserRepository.findById(id);

		if (UserData.isPresent()) {
			User _User = UserData.get();
			_User.setUserName(User.getUserName());
			_User.setFirstName(User.getFirstName());
			_User.setLastName(User.getLastName());
			return new ResponseEntity<>(UserRepository.save(_User), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// #### List all users

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String username) {
		try {
			List<User> Users = new ArrayList<User>();

			if (username == null)
				UserRepository.findAll().forEach(Users::add);
			else
				UserRepository.findByUserNameContaining(username).forEach(Users::add);

			if (Users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// #### Get User info

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		Optional<User> UserData = UserRepository.findById(id);

		if (UserData.isPresent()) {
			return new ResponseEntity<>(UserData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// #### Delete User info

	@DeleteMapping("/Users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		try {
			UserRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// #### Delete all User info

	@DeleteMapping("/Users")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			UserRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
