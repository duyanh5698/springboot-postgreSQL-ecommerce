package com.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.dto.MessageResponse;
import com.ecommerce.model.File;
import com.ecommerce.service.FileService;
import com.ecommerce.service.FileStorageService;



@RestController
@RequestMapping
public class FileController {

	@Autowired
	FileStorageService storageService;
	
	@Autowired
	FileService fileService;
	
	@PostMapping("/upload")
	public ResponseEntity<MessageResponse> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("productId") Long productId) {
		String message = "";
		try {
			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {
				storageService.save(file);
				String imgURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
						.path(file.getOriginalFilename()).toUriString();
				File fileDB = new File(file.getOriginalFilename(), imgURL, productId);
				fileService.save(fileDB);
				fileNames.add(file.getOriginalFilename());
			});
			message = "Uploaded the files successfully: " + fileNames;
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			message = "Fail to upload files!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}

