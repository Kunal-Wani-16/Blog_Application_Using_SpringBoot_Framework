package com.kunal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kunal.blog.payloads.ApiResponse;
import com.kunal.blog.payloads.CommentDto;
import com.kunal.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/create/{postid}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postid") Integer postId)
	{
		CommentDto commentSavedDto=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(commentSavedDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentid}/comments")
	public ResponseEntity<ApiResponse> deleteComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted sucessfully",true),HttpStatus.OK);
	}
}
