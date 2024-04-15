package com.kunal.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kunal.blog.config.AppConstants;
import com.kunal.blog.entities.Post;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.ApiResponse;
import com.kunal.blog.payloads.CategoryDto;
import com.kunal.blog.payloads.FileResponse;
import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.payloads.PostResponse;
import com.kunal.blog.services.FileService;
import com.kunal.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/create")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId)
	{
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable("userId") Integer userId)
	{
		List<PostDto>postDtos=this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDto>postDtos=this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	// Get All Post
	@GetMapping("/all")
	public ResponseEntity<PostResponse>getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
			)
	{
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// Get Post By Id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId)
	{
		PostDto postDto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse>deletePostById(@PathVariable("postId") Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted sucessfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId)
	{
		PostDto updatePostDto=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
	}
	
	@PatchMapping("/update/{postId}")
	public ResponseEntity<PostDto>partialPostUpdate(@RequestBody Map<String, Object> field,@PathVariable("postId") Integer postId)
	{
		PostDto postDto=this.postService.partialPostUpdate(field, postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	// search by title
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPostByTitle(@PathVariable("keyword") String keyword)
	{
		List<PostDto>postDtos=this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/searchbycontent/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByContent(@PathVariable("keyword") String Keyword)
	{
		List<PostDto>postDtos=this.postService.searchByContentKeyword(Keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@PostMapping("/image/upload/{postid}")
	public ResponseEntity<PostDto> uploadPostImage( @RequestParam("image") MultipartFile image,@PathVariable("postid") Integer postId) throws IOException
	{
		String fileName = null;
		
			PostDto post=this.postService.getPostById(postId);
		
			fileName=this.fileService.uploadImage(path, image);
			post.setImageName(fileName);
			PostDto updatePost = this.postService.updatePost(post, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	@GetMapping(value="post/image/{imagename}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imagename") String imageName, HttpServletResponse response) throws IOException
	{
		InputStream resource= this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		//StreamUtils.copy() is a useful method for copying data between streams. It is especially useful when you need to copy data between streams of different types, such as an InputStream and an OutputStream.
	}
}
