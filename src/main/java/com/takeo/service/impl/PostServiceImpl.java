package com.takeo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.PostDto;
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
	private final String DB_PATH = "/Users/tusharmahat/db/";
//	private final String DB_PATH = "C:\\Users\\himal\\OneDrive\\Desktop\\db\\";

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ImageFile imageFile;

	@Autowired
	private PostRepo postDaoImpl;

	@Autowired
	private UserRepo userDaoImpl;

	@Autowired
	private ImageNameGenerator fileNameGenerator;

	@Override
	public String create(PostDto postDto, Long uid) {
		Optional<User> existingUser = userDaoImpl.findById(uid);
		String message = "Post not created";
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			Post post = new Post();
			BeanUtils.copyProperties(postDto, post);
			post.setUser(user);
			Post savePost = postDaoImpl.save(post);
			if (savePost != null) {
				message = "Post  created";
			}
			return message;
		}
		throw new ResourceNotFoundException("User with uid " + uid + " not found");
	}

	@Override
	public List<PostDto> read() {
		List<Post> posts = postDaoImpl.findAll();
		List<PostDto> postsDto = new ArrayList<>();
		if (posts.size() != 0) {
			for (Post p : posts) {
				PostDto postDto = new PostDto();
				BeanUtils.copyProperties(p, postDto);
				postsDto.add(postDto);
			}
			return postsDto;
		}
		throw new ResourceNotFoundException("No posts found");
	}

	@Override
	public List<PostDto> read(Long uid) {
		Optional<User> existingUser = userDaoImpl.findById(uid);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			List<Post> posts = postDaoImpl.findByUser(user);
			List<PostDto> postsDto = new ArrayList<>();
			if (posts.size() != 0) {
				for (Post p : posts) {
					PostDto postDto = new PostDto();
					BeanUtils.copyProperties(p, postDto);
					postsDto.add(postDto);
				}
				return postsDto;
			}
			throw new ResourceNotFoundException("User with uid " + uid + " has not posts");
		}
		throw new ResourceNotFoundException("User with uid " + uid + " does not exist");

	}

	@Override
	public PostDto readPost(Long pid) {
		Optional<Post> post = postDaoImpl.findById(pid);
		if (post.isPresent()) {
			Post returnPost = post.get();
			PostDto postDto = new PostDto();
			BeanUtils.copyProperties(returnPost, postDto);
			return postDto;
		}
		throw new ResourceNotFoundException("Post with " + pid + " not found");
	}

	@Override
	public String update(PostDto postDto, Long uid, Long pid) {
		// TODO Auto-generated method stub
		String message = "Not updated";
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if (existingPost.isPresent()) {
			if (existingPost.get().getUser().getUId() == uid) {
				postDto.setPid(existingPost.get().getPid());
				Post post = existingPost.get();
				modelMapper.map(postDto, post);

				Post savePost = postDaoImpl.save(post);
				if (savePost != null) {
					message = "Post updated";
				}
			}
			return message;
		}

		throw new ResourceNotFoundException("Update failed");
	}

	@Override
	public String delete(Long pid) {
		String message = "Post not deleted";
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if (existingPost.isPresent()) {
			postDaoImpl.deleteById(pid);
			message = "Post deleted";
			return message;
		}
		throw new ResourceNotFoundException("Post with " + pid + " not found");
	}

	@Override
	public byte[] viewPostPicture(Long pid) {
		Optional<Post> post = postDaoImpl.findById(pid);
		if (post.isPresent()) {
			String filePath = post.get().getImage();
			try {
				return Files.readAllBytes(Paths.get(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new ResourceNotFoundException("Post  not found with post id: " + pid);
	}

	@Override
	public String updatePostPicture(@RequestParam("file") MultipartFile file, Long pid) {
		// TODO Auto-generated method stub
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if (existingPost.isPresent()) {
			Post post = existingPost.get();
			String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS"));
			String fileName = timeStamp + fileNameGenerator.getFileExtensionName(file.getOriginalFilename());
			String filePath = DB_PATH + fileName;

			if (imageFile.isImageFile(file)) {
				// Create the folder if it doesnt exist
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
				Post updatePhoto = postDaoImpl.save(post);
				if (updatePhoto != null) {
					return "Updated";
				}
			} else {
				return ("Only image files are allowed.");
			}
		}
		return null;
	}
}
