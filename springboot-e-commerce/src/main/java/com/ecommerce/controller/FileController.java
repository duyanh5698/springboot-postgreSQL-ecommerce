package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.model.File;
import com.ecommerce.model.dto.FileResponse;
import com.ecommerce.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("productId") Long productId) {
        try {
            fileService.save(file, productId);

            return ResponseEntity.status(HttpStatus.OK)
                                 .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<FileResponse> list() {
        return fileService.getAllFiles()
                          .stream()
                          .map(this::mapToFileResponse)
                          .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(File fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path("/files/")
                                                        .path(fileEntity.getId())
                                                        .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<File> fileEntityOptional = fileService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                                 .build();
        }

        File fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                             .contentType(MediaType.valueOf(fileEntity.getContentType()))
                             .body(fileEntity.getData());
    }
    
//    @GetMapping(value = "/get-file/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> getFile1(@PathVariable String id) throws IOException {
//    	Optional<File> file = fileService.getFile(id);
//    	if(!file.isPresent()) {
//    		//baso loix
//    	}
////    	return file.get().getData();
//    	return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get().getName() + "\"")
//                .contentType(MediaType.valueOf(file.get().getContentType()))
//                .body(file.get().getData());
//    }
}