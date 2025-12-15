package com.work.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.work.data.PostNotes;
import com.work.entities.NotesEntity;
import com.work.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    NotesRepository notesRepository;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            // Return the URL of the uploaded file
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public NotesEntity uploadData(PostNotes postNotes) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setTitle(postNotes.getTitle());
        notesEntity.setNotes(postNotes.getBody());
        String fileUrl = uploadFile(postNotes.getFile());
        notesEntity.setUrl(fileUrl);
        NotesEntity notesEntityRslt = insertIntoDB(notesEntity);
        saveMultipleNotes(notesEntity);
        return notesEntityRslt;
    }
    public void saveMultipleNotes(NotesEntity notesEntity1) {
        for (int i = 1; i <= 10; i++) {
            NotesEntity notesEntity = new NotesEntity();
            notesEntity.setTitle(notesEntity1.getTitle());
            notesEntity.setNotes(notesEntity1.getNotes());
            notesEntity.setUrl(notesEntity1.getUrl());
            insertIntoDB(notesEntity);
        }
    }

    public NotesEntity getNotesById(UUID id) {
        NotesEntity notesEntity = notesRepository.findById(id).orElse(null);
        return notesEntity;
    }

    public List<NotesEntity> getNotesByTitle(String title) {
        return notesRepository.findByTitle(title);
    }

    public List<NotesEntity> getNotesByTitleAndNotes(String title, String notes) {
        return notesRepository.findByTitleAndNotes(title, notes);
    }

    public NotesEntity insertIntoDB(NotesEntity notesEntity) {
        return notesRepository.save(notesEntity);
    }

    private String generateUniqueFileName(String fileName) {
        // Add a timestamp or UUID to ensure a unique filename and avoid overwrites
        return System.currentTimeMillis() + "_" + fileName.replace(" ", "_");
    }
}