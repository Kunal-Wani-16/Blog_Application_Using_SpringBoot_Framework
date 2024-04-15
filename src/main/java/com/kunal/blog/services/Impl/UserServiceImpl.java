package com.kunal.blog.services.Impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kunal.blog.config.AppConstants;
import com.kunal.blog.entities.Role;
import com.kunal.blog.entities.User;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.UserDto;
import com.kunal.blog.repositories.RoleRepo;
import com.kunal.blog.repositories.UserRepo;
import com.kunal.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=this.dtoToUser(userDto);
		User saveUser= userRepo.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
		
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());;
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto2=this.userToDto(user);
		return userDto2;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user"," Id ", userId));
		// .get() gives 1 entry
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDtos= users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	public User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}
	
	public UserDto userToDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		userRepo.delete(user);
	}
	

	public UserDto partialUpdate(Map<String, Object>map,Integer id) 
	{
		//List<User>list=this.userRepo.findAll();
		//List<UserDto> userDtos= list.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		//UserDto userDto=userDtos.stream().filter(l->l.getId()==id).findFirst().get();
		User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user","Id",id));
		//UserDto updatedUser=this.userToDto(user);
		map.forEach((key,value)->{
			Field field=ReflectionUtils.findRequiredField(User.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field,user, value);
		});
		User updatedUser2=this.userRepo.save(user);
		UserDto userDto2=this.userToDto(updatedUser2);
		return userDto2;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=this.modelMapper.map(userDto, User.class);
		
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role= this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser= this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}



	

	
}
