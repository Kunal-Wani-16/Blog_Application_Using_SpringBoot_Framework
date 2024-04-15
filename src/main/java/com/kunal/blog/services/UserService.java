package com.kunal.blog.services;

import java.util.List;
import java.util.Map;

import com.kunal.blog.entities.User;
import com.kunal.blog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	User dtoToUser(UserDto userDto);
	UserDto userToDto(User user);
	
	UserDto partialUpdate(Map<String, Object> fields, Integer id);
}
