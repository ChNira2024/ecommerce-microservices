package com.niranjana.ecommerce.user.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjana.ecommerce.user.dto.UserRequest;
import com.niranjana.ecommerce.user.dto.UserResponse;
import com.niranjana.ecommerce.user.service.UserService;
import com.niranjana.ecommerce.user.util.LogUtil;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired private  UserService userService;
    
    //get all user
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
    	log.info("inside UserController class,getAllUsers method");
    	log.info("Request: Fetch all users");

        List<UserResponse> users = userService.fetchAllUsers();
        if (users.isEmpty()) 
        {
            log.warn("No users found");
            return ResponseEntity.noContent().build(); // 204
        }
        log.info("Fetched {} users", users.size());
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
        log.info("Inside UserController - getUser method, id={}", id);

        //return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());//using stream

        log.info("Request: Fetch user by id={}", id);
        UserResponse response = userService.fetchUser(id);
        log.info("Response: {}", LogUtil.toJson(response));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
    	log.info("inside UserController class, createUser method");
    	log.info("Request: Create user email={}", userRequest.getEmail());
        userService.addUser(userRequest);
        log.info("User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,@RequestBody UserRequest updateUserRequest){
    	log.info("inside UserController class, updateUser method");
    	log.info("Request: Update user id={}", id);

    	userService.updateUser(id, updateUserRequest);
        log.info("User updated successfully, id={}", id);
        return ResponseEntity.ok("User updated successfully");
    }
}