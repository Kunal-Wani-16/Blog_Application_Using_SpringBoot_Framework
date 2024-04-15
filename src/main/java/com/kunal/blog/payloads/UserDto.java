package com.kunal.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.kunal.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private int id;
	
	@NotNull(message="Name should not be null") @NotEmpty
	private String name;
	
	@Email(message="invalid email") @NotBlank
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password is not satisfying requirement")
	private String password;
	
	@NotBlank
	@Size(min=4, message = "Enter valid about")
	private String about;
	
	Set<RoleDto> roles=new HashSet<>();
}
