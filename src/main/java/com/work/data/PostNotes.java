package com.work.data;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


public class PostNotes {
    private MultipartFile file;
    private String title;
    private String body;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
