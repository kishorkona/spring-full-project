package com.work.controllers;

import com.google.gson.Gson;
import com.work.data.PostNotes;
import com.work.entities.NotesEntity;
import com.work.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class NotesController {
    Gson gson = new Gson();

    @Autowired
    S3Service s3Service;

    @GetMapping("/getAllNotes")
    public ResponseEntity<String> getAllNotes() {
        System.out.println("------------>> getNotes:"+Thread.currentThread().getName());
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/getNotes")
    public ResponseEntity<String> getNotes() {
        PostNotes postNotes = new PostNotes();
        postNotes.setBody("body");
        postNotes.setTitle("title");

        System.out.println("------------>> getNotes:"+gson.toJson(postNotes));

        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @PostMapping(path = "/notes", consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity<NotesEntity> addNotes(
            @RequestParam("file") MultipartFile file,
            @RequestParam("postNotes") String jsonData) {
        PostNotes postNotes = gson.fromJson(jsonData, PostNotes.class);
        postNotes.setFile(file);
        NotesEntity notesEntity = null;
        try {
            notesEntity = s3Service.uploadData(postNotes);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity(notesEntity, HttpStatus.OK);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NotesEntity> getNotesById(@PathVariable("id") UUID id) {
        NotesEntity notesEntity = s3Service.getNotesById(id);
        return new ResponseEntity(notesEntity, HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NotesEntity>> getNotesByQuery(
            @RequestParam(value="title") String title,
            @RequestParam(value="notes", required = false) String notes
    ) {
        List<NotesEntity> notesEntityList = null;
        if(!title.isEmpty()) {
            notesEntityList = s3Service.getNotesByTitle(title);
        }
        if(notes != null) {
            notesEntityList = s3Service.getNotesByTitleAndNotes(title, notes);
        }
        if(notesEntityList.isEmpty()) {
            notesEntityList = new ArrayList<>();
        }
        return new ResponseEntity(notesEntityList, HttpStatus.OK);
    }
}
