package com.work.data;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Person {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Instant createdDate;
}
