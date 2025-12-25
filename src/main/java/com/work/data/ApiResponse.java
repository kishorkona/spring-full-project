package com.work.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class ApiResponse {
    private String code;
    private String status;
    private String message;
    private List data;
    private Object listData;

}
