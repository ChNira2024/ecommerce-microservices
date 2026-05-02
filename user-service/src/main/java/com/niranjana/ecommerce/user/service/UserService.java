package com.niranjana.ecommerce.user.service;

import java.util.List;

import com.niranjana.ecommerce.user.dto.UserRequest;
import com.niranjana.ecommerce.user.dto.UserResponse;

public interface UserService {
	 public List<UserResponse> fetchAllUsers();
	 public void addUser(UserRequest userRequest);
	 public UserResponse fetchUser(Long id);
	 public void updateUser(Long id, UserRequest updatedUserRequest);
	 
	 

}
