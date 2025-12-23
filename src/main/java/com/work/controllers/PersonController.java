package com.work.controllers;

import com.google.gson.Gson;
import com.work.constants.MyConstants;
import com.work.data.ApiResponse;
import com.work.data.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PersonController {
    Gson gson = new Gson();

    @GetMapping("/getPerson")
    public ResponseEntity<ApiResponse> getPerson() {
        ApiResponse apiResponse = new ApiResponse();

        Person person = new Person();
        person.setFirstName("kishor");
        person.setLastName("Kona");
        apiResponse.setData(new ArrayList());
        apiResponse.getData().add(person);
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.SUCCESS);

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }


}
