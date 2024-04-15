package com.kunal.blog.controllers;

import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kunal.blog.payloads.ApiResponse;
import com.kunal.blog.payloads.UserDto;
import com.kunal.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	// POST - create user
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
	{
		UserDto createUserDto=userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId)
	{
		UserDto userDto2= this.userService.updateUser(userDto, uId);
		//return new ResponseEntity<>(userDto2,HttpStatus.OK); 
		return ResponseEntity.ok(userDto2);
	}
	// Put - update user
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userid") Integer uId)
	{
		this.userService.deleteUser(uId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted sucessfully",true),HttpStatus.OK );
	}
	// GET - retrive user
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId)
	{
		return new ResponseEntity<UserDto>(this.userService.getUserById(userId),HttpStatus.OK);
	}
	
	@PatchMapping("updatebyfield/{userId}")
	public UserDto updateByField(@Valid @RequestBody Map<String, Object> map,@PathVariable("userId") Integer userId)
	{
		return this.userService.partialUpdate(map, userId);
	}
	
}
