package com.ecommerce.model.dto;

import lombok.Data;

@Data
public class FileResponse {

    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
}
