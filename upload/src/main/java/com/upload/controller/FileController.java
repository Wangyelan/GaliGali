package com.upload.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upload.dao.Video;
import com.upload.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Component
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    VideoMapper videoMapper;

    @CrossOrigin
    @GetMapping("/uploadFile")
    public String uploadFile(String name,String content){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Video video = new Video(name,content);
        QueryWrapper<Video> userQueryWrapper=new QueryWrapper<>();
        videoMapper.insert(video);
        return "上传成功";
    }

    @CrossOrigin
    @GetMapping("/showChecking")//展示视频信息表
    public HashMap<Integer, String> showChecking(){
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.lambda().eq(Video::getStatus,"in checking");
        List<Video> temp = videoMapper.selectList(videoQueryWrapper);
        HashMap<Integer,String> map = new HashMap<>();
        int len = temp.size();
        Object[] userArray = temp.toArray();
        for(int i =0;i < len;i ++){
            map.put(((Video)userArray[i]).getId(),((Video)userArray[i]).getContent());
        }
        return map;
    }

    @CrossOrigin
    @GetMapping("/showReady")//展示已经审核通过的信息表
    public HashMap<Integer,String> showReady(){
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.lambda().eq(Video::getStatus,"ready");
        List<Video> temp = videoMapper.selectList(videoQueryWrapper);
        HashMap<Integer,String> map = new HashMap<>();
        int len = temp.size();
        Object[] userArray = temp.toArray();
        for(int i =0;i < len;i ++){
            map.put(((Video)userArray[i]).getId(),((Video)userArray[i]).getContent());
        }
        return map;
    }

    @CrossOrigin
    @GetMapping("/changeStatus")//用于审核信息，更改信息状态
    public String changeStatus(Integer id){
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        List<Video> temp = videoMapper.selectList(videoQueryWrapper);
        videoQueryWrapper.lambda().eq(Video::getId,id);
        Video video= videoMapper.selectOne(videoQueryWrapper);
        if(!video.getStatus().equals("in checking")){
            return "error";
        }
        else{
            video.setStatus("ready");
            videoMapper.update(video,videoQueryWrapper);
            return "success";
        }
    }
}
