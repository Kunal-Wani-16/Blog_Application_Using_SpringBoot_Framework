package com.kunal.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kunal.blog.entities.Category;
import com.kunal.blog.entities.Post;
import com.kunal.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post>findByTitleContaining(String title);
	
	@Query("select p from Post p where p.content LIKE %:content%")
	List<Post> searchByContent(@Param("content") String content);
}
