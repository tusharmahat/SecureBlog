package com.takeo.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.ReportDto;
import com.takeo.entity.Category;
import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.Role;
import com.takeo.entity.User;
import com.takeo.repo.CategoryRepo;
import com.takeo.repo.CommentRepo;
import com.takeo.repo.PostRepo;
import com.takeo.repo.RoleRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	private ReportDto data = new ReportDto();

	public void getData() {
		List<User> users = userRepo.findAll();
		List<Post> posts = postRepo.findAll();
		List<Comment> comments = commentRepo.findAll();
		List<Role> roles = roleRepo.findAll();
		List<Category> categories = categoryRepo.findAll();
		data.setRoles(roles);
		data.setUsers(users);
		data.setPosts(posts);
		data.setComments(comments);
		data.setCategories(categories);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/pdf")
	public ResponseEntity<?> generatePdfReport() {
		getData();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			reportService.generatePdfReport(data, outputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "report.pdf");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDf file");
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/excel")
	public ResponseEntity<?> generateExcelReport() {
		getData();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			reportService.generateExcelReport(data, outputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "excel.xlsx");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDf file");
		}
	}
}
