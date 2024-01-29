package com.takeo.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.takeo.dto.ReportDto;
import com.takeo.entity.Category;
import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.Role;
import com.takeo.entity.User;
import com.takeo.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	final Font RED_BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
	final Font RED_NORMAL = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
	final String line = "-----------------------------------------------------------"
			+ "-------------------------------------------------------------------";

	@Override
	public void generatePdfReport(ReportDto data, OutputStream outputStream) {
		// Use iText to generate PDF report
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			addRoleUserDataToPdf(data.getRoles(), document);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			addUserPostDCommentataToPdf(data.getUsers(), document);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			addCategoryPostDataToPdf(data.getCategories(), document);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}

	private void addRoleUserDataToPdf(List<Role> roles, Document document) throws DocumentException {
		document.add(new Paragraph(line, RED_BOLD));
		document.add(new Paragraph("Users and Roles", RED_BOLD));
		document.add(new Paragraph(line, RED_BOLD));
		for (Role role : roles) {
			document.add(new Paragraph("Role ID: " + role.getRoleId()));
			document.add(new Paragraph("Role: " + role.getRole()));
			document.add(new Paragraph(line, RED_NORMAL));
			document.add(new Paragraph("Users in Role ID: " + role.getRoleId(), RED_NORMAL));
			document.add(new Paragraph(line, RED_NORMAL));
			for (User user : role.getRolesUsers()) {
				document.add(new Paragraph("User ID: " + user.getUId()));
				document.add(new Paragraph("Name: " + user.getName()));
				document.add(new Paragraph("Email: " + user.getEmail()));
				document.add(new Paragraph("Mobile: " + user.getMobile()));
				document.add(new Paragraph(line));
				document.add(Chunk.NEWLINE);
			}
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
		}
	}

	private void addUserPostDCommentataToPdf(List<User> users, Document document) throws DocumentException {
		document.add(new Paragraph(line, RED_BOLD));
		document.add(new Paragraph("Users, Posts and related Comments", RED_BOLD));
		document.add(new Paragraph(line, RED_BOLD));
		for (User user : users) {
			document.add(new Paragraph("User ID: " + user.getUId()));
			document.add(new Paragraph("Name: " + user.getName()));
			document.add(new Paragraph("Email: " + user.getEmail()));
			document.add(new Paragraph("Mobile: " + user.getMobile()));
			document.add(new Paragraph(line, RED_NORMAL));
			document.add(new Paragraph("Posts by User ID: " + user.getUId(), RED_NORMAL));
			document.add(new Paragraph(line, RED_NORMAL));
			for (Post post : user.getPosts()) {
				document.add(new Paragraph("Post ID: " + post.getPid()));
				document.add(new Paragraph("Post Title: " + post.getTitle()));
				document.add(new Paragraph("Post Content: " + post.getContent()));
				document.add(new Paragraph(line, RED_NORMAL));
				document.add(new Paragraph("Comments in Post ID: " + post.getPid(), RED_NORMAL));
				document.add(new Paragraph(line, RED_NORMAL));
				for (Comment comment : post.getComments()) {
					document.add(new Paragraph("Comment ID: " + comment.getCid()));
					document.add(new Paragraph("Comment Content: " + comment.getContent()));
					document.add(new Paragraph(line));
				}
				document.add(Chunk.NEWLINE);
			}
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
		}
	}

	private void addCategoryPostDataToPdf(List<Category> categories, Document document) throws DocumentException {
		document.add(new Paragraph(line, RED_BOLD));
		document.add(new Paragraph("Category and related Posts", RED_BOLD));
		document.add(new Paragraph(line, RED_BOLD));
		for (Category category : categories) {
			document.add(new Paragraph("Category ID: " + category.getCategoryId()));
			document.add(new Paragraph("Post Name: " + category.getCategoryName()));
			document.add(new Paragraph(line, RED_NORMAL));
			document.add(new Paragraph("Posts in Category ID: " + category.getCategoryId(), RED_NORMAL));
			document.add(new Paragraph(line, RED_NORMAL));
			for (Post post : category.getCategoriesPosts()) {
				document.add(new Paragraph("Post ID: " + post.getPid()));
				document.add(new Paragraph("Post Title: " + post.getTitle()));
				document.add(new Paragraph("Post Content: " + post.getContent()));
				document.add(Chunk.NEWLINE);
			}
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
		}
	}

	@Override
	public void generateExcelReport(ReportDto data, OutputStream outputStream) {
		Workbook workbook = new XSSFWorkbook();

		try {
			addRoleUserDataToExcel(data.getRoles(), workbook.createSheet("Users_and_Roles"));
			addUserDataToExcel(data.getUsers(), workbook.createSheet("Users_Posts_Comments"));
			addCategoryPostDataToExcel(data.getCategories(), workbook.createSheet("Category_and_Posts"));

			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addRoleUserDataToExcel(List<Role> roles, Sheet sheet) {
		int rowIndex = 0;

		for (Role role : roles) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue("Role ID: " + role.getRoleId());
			row.createCell(1).setCellValue("Role: " + role.getRole());

			// Add an empty line between role and users
			rowIndex++;

			for (User user : role.getRolesUsers()) {
				Row userRow = sheet.createRow(rowIndex++);
				userRow.createCell(2).setCellValue("User ID: " + user.getUId());
				userRow.createCell(3).setCellValue("Name: " + user.getName());
				userRow.createCell(4).setCellValue("Email: " + user.getEmail());
				userRow.createCell(5).setCellValue("Mobile: " + user.getMobile());

				// Add an empty line between users
				rowIndex++;
			}

			// Add an empty line between roles
			rowIndex++;
		}
	}

	private void addUserDataToExcel(List<User> users, Sheet sheet) {
		int rowIndex = 0;

		for (User user : users) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue("User ID: " + user.getUId());
			row.createCell(1).setCellValue("Name: " + user.getName());
			row.createCell(2).setCellValue("Email: " + user.getEmail());
			row.createCell(3).setCellValue("Mobile: " + user.getMobile());

			// Add an empty line between user and posts
			rowIndex++;

			for (Post post : user.getPosts()) {
				Row postRow = sheet.createRow(rowIndex++);
				postRow.createCell(4).setCellValue("Post ID: " + post.getPid());
				postRow.createCell(5).setCellValue("Post Title: " + post.getTitle());
				postRow.createCell(6).setCellValue("Post Content: " + post.getContent());

				// Add an empty line between posts
				rowIndex++;

				for (Comment comment : post.getComments()) {
					Row commentRow = sheet.createRow(rowIndex++);
					commentRow.createCell(7).setCellValue("Comment ID: " + comment.getCid());
					commentRow.createCell(8).setCellValue("Comment Content: " + comment.getContent());

					// Add an empty line between comments
					rowIndex++;
				}
			}

			// Add an empty line between users
			rowIndex++;
		}
	}

	private void addCategoryPostDataToExcel(List<Category> categories, Sheet sheet) {
		int rowIndex = 0;

		for (Category category : categories) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue("Category ID: " + category.getCategoryId());
			row.createCell(1).setCellValue("Category Name: " + category.getCategoryName());

			// Add an empty line between category and posts
			rowIndex++;

			for (Post post : category.getCategoriesPosts()) {
				Row postRow = sheet.createRow(rowIndex++);
				postRow.createCell(2).setCellValue("Post ID: " + post.getPid());
				postRow.createCell(3).setCellValue("Post Title: " + post.getTitle());
				postRow.createCell(4).setCellValue("Post Content: " + post.getContent());

				// Add an empty line between posts
				rowIndex++;
			}

			// Add an empty line between categories
			rowIndex++;
		}
	}
}
