package com.kunal.blog;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kunal.blog.entities.Post;
import com.kunal.blog.entities.Role;
import com.kunal.blog.entities.User;
import com.kunal.blog.repositories.PostRepo;
import com.kunal.blog.repositories.UserRepo;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	private User user;
	
//	@Test
//	void contextLoads() {
//	}

//	@Test
//	public void repoTest()
//	{
//		String className=this.userRepo.getClass().getName();
//		String packName=this.userRepo.getClass().getPackageName();
//		System.out.println(className+"  "+packName);
//	}
	
	@Test
	public void postRepoTest()
	{
		List<Post>posts=this.postRepo.searchByContent("4");
		for(Post post:posts)
		{
			System.out.println(post.getContent()+" "+post.getPostId());
		}
		
		List<Post>posts1=this.postRepo.findByTitleContaining("4");
		for(Post post:posts1)
		{
			System.out.println(post.getContent()+" "+post.getPostId()+"inner");
		}
		
	}
	
	@Test
	public void authoritites() 
	{
		List<SimpleGrantedAuthority> authorities =this.user.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		for(int i=0;i<authorities.size();i++)
		{
			System.out.println(authorities.get(i));
		}
	}
}
