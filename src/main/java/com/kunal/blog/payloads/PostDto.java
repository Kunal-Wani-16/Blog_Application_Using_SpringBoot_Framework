package com.kunal.blog.payloads;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import com.kunal.blog.entities.Category;
import com.kunal.blog.entities.Comment;
import com.kunal.blog.entities.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	@NotBlank
	private Integer postId;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String content;
	
	@NotBlank
	private String imageName;
	
	@NotBlank
	private Date addedDate;
	
	private CategoryDto category;// if we use category then it goes into infinity as categorydto does not contain category field
	
	private UserDto user;
	// image we take from service
	// category and user Id we can take it from 2 ways 1) direct here 2) URLs
	
	private Set<CommentDto>comments=new HashSet<>();
	// if we use comment it goes in recursion
	//because commentdto does not contain post
}
