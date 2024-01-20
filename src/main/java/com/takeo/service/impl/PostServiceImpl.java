package com.takeo.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.PostRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.PostService;
import com.takeo.utils.ImageFile;
import com.takeo.utils.ImageNameGenerator;

@Service
public class PostServiceImpl implements PostService {
//	private final String DB_PATH ="/Users/tusharmahat/db/";
	private final String DB_PATH ="C:\\Users\\himal\\OneDrive\\Desktop\\db\\";
	
	@Autowired
	private ImageFile imageFile;
	
	@Autowired
	private PostRepo postDaoImpl;

	@Autowired
	private UserRepo userDaoImpl;
	
	@Autowired
	private ImageNameGenerator fileNameGenerator;

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
		else throw new ResourceNotFoundException("Post with "+pid+"notfound");
		
	}

	@Override
	public byte[] viewPostPicture(Long pid) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public String updatePostPicture(MultipartFile file, Long pid) {
		// TODO Auto-generated method stub
		System.out.println(pid);
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if(existingPost.isEmpty()) {
			Post post = existingPost.get();
			String timeStamp =LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS"));
			String fileName = timeStamp+ fileNameGenerator.getFileExtensionName(file.getOriginalFilename());
		    String filePath =DB_PATH+fileName;
		    
		    if(imageFile.isImageFile(file)) {
		    	//Create the folder if it doesnt exist
		    	File folder = new File(filePath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				try {
					file.transferTo(new File(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				post.setImage(filePath);
				Post updatePhoto = create(post);
				if (updatePhoto != null) {
					return "successfull";
				}
			} else {
				return ("Only image files are allowed.");
			}
			
		}
		return null;
	}

}
