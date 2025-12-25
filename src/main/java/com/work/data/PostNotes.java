package com.work.data;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostNotes {
    private MultipartFile file;
    private String title;
    private String body;
}
