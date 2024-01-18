package com.takeo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.repo.PostRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postDaoImpl;

	@Autowired
	private UserRepo userDaoImpl;

	@Override
	public Post create(Post post) {
		return postDaoImpl.save(post);
	}

	@Override
	public List<Post> read(Long uid) {
		List<Post> posts = postDaoImpl.findAll();
		return posts;
	}

	@Override
	public Post readPost(Long pid) {
		Post post = postDaoImpl.findById(pid).get();
		return post;
	}

	@Override
	public Post readPost(Long uid, Long pid) {
		// TODO Auto-generated method stub
		Post p = readPost(pid);
		if (p != null && p.getUser().getUId() == uid) {
			return p;
		}

		return null;

	}

	@Override
	public Post update(Post post, Long uid, Long pid) {
		// TODO Auto-generated method stub
		
		Post p= readPost(pid);
		if(p!=null&&p.getUser().getUId()==uid)
		{
			return create(post);
		}
		
		return null;
	}

	@Override
	public boolean delete(Long pid, Long uid) {
		// TODO Auto-generated method stub
		Post p =readPost(pid);
		if(p!=null&&p.getUser().getUId()==uid)
		{
			 postDaoImpl.deleteById(pid);
			 return true;
		}
		
		return false;
	}

}
