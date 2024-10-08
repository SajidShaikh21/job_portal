package io.job.my_app.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseUserDto {

    private String message;
    private List<UserDto> list;
    private String exception;
    private int totalPages;
    public ResponseUserDto(String message){
        this.message=message;
    }

    public ResponseUserDto(String message, List<UserDto> list, int totalPages) {
        this.message =message;
        this.list = list;
        this.totalPages = totalPages;
    }
}
