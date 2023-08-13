package com.upload.dao;


import lombok.Data;

@Data
public class Video {
    Integer id;
    String name;
    String content;
    String status;
    public Video(String name,String content){
        this.name = name;
        this.content = content;
        this.status = "in checking";
    }
}