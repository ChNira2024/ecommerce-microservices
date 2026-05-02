package com.niranjana.ecommerce.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.user.dto.AddressDTO;
import com.niranjana.ecommerce.user.dto.UserRequest;
import com.niranjana.ecommerce.user.dto.UserResponse;
import com.niranjana.ecommerce.user.entity.Address;
import com.niranjana.ecommerce.user.entity.User;
import com.niranjana.ecommerce.user.exception.UserNotFoundException;
import com.niranjana.ecommerce.user.repository.UserRepository;
import com.niranjana.ecommerce.user.service.UserService;


@Service
public class UserServiceImpl implements  UserService{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired private UserRepository userRepository;

    @Override
    public List<UserResponse> fetchAllUsers(){
    	log.info("inside UserServiceImpl class,fetchAllUsers method");
        //return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
    	try {
            List<UserResponse> users = userRepository.findAll().stream().map(this::mapToUserResponse).toList();

            if (users.isEmpty()) {
                log.warn("No users found in database");
            } else {
                log.info("Fetched {} users", users.size());
            }
            return users;
        } catch (Exception ex) {
            log.error("Error fetching users", ex);
            throw new RuntimeException("Failed to fetch users");
        }
    }
    
    @Override
    public void addUser(UserRequest userRequest){
    	log.info("Creating new user: email={}", userRequest.getEmail());

        try {
            User user = new User();
            updateUserFromRequest(user, userRequest);
            userRepository.save(user);
            log.info("User created successfully with id={}", user.getId());
        } catch (Exception ex) {
            log.error("Error creating user", ex);
            throw new RuntimeException("Failed to create user");
        }
    }
    
    @Override
    public UserResponse fetchUser(Long id) {
    	log.info("Inside UserServiceImpl - fetchUser method, id={}", id);
        //return userRepository.findById(id).map(this::mapToUserResponse);
    	
    	log.info("Fetching user by id={}", id);
        return userRepository.findById(id).map(this::mapToUserResponse)
						                .orElseThrow(() -> {
						                    log.warn("User not found with id={}", id);
						                    return new UserNotFoundException("User not found with id: " + id);
						                });
    }
    
    @Override
    public void updateUser(Long id, UserRequest updatedUserRequest) {
       /* return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
     */
    	log.info("Updating user with id={}", id);

        User user = userRepository.findById(id)
                				  .orElseThrow(() -> {
                					  				log.warn("User not found for update, id={}", id);
                					  				return new UserNotFoundException("User not found with id: " + id);
                				  					});        
        try {
            updateUserFromRequest(user, updatedUserRequest);
            userRepository.save(user);
            log.info("User updated successfully, id={}", id);
        } catch (Exception ex) {
            log.error("Error updating user id={}", id, ex);
            throw new RuntimeException("Failed to update user");
        }
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}