package com.work.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.work.constants.ConfigurationConstants;
import com.work.data.PostNotes;
import com.work.entities.NotesEntity;
import com.work.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    DBService dbService;

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
            //Map<String, String> props =  dbService.getAWSProperties();
            //AmazonS3 s3Client = amazonS3Client(props);
            PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(bucketName, fileName,
                    file.getInputStream(), metadata));

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
        if(fileUrl != null && !fileUrl.trim().isEmpty()) {
            notesEntity.setUrl(fileUrl);
            NotesEntity notesEntityRslt = insertIntoDB(notesEntity);
            saveMultipleNotes(notesEntity);
            return notesEntityRslt;
        }
        return null;
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

    public AmazonS3 amazonS3Client(Map<String, String> props) {
        AWSCredentials credentials =
                new BasicAWSCredentials(props.get(ConfigurationConstants.aws_access_key_id),
                        ConfigurationConstants.aws_secret_access_key);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

}