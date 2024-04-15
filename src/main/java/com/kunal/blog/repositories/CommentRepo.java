package com.kunal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunal.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
