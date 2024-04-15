package com.kunal.blog.services;

import java.util.List;
import java.util.Map;

import com.kunal.blog.entities.Post;
import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId, Integer CategoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	// Get All Post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// Get Single Post
	PostDto getPostById(Integer postId);
	
	// Get Post By Category
	List<PostDto> getPostsByCategory(Integer CategoryId);
	
	// Get Post By User
	List<PostDto> getPostsByUser(Integer userId);
	
	// Search Posts
	List<PostDto> searchPosts(String keyword);
	
	//Partial Update
	PostDto partialPostUpdate(Map<String, Object> fields,Integer postId);
	
	// search By Content
	List<PostDto> searchByContentKeyword(String keyword);
}
