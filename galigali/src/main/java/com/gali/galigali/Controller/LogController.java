package com.gali.galigali.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gali.galigali.dao.Log;
import com.gali.galigali.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogMapper logMapper;
    @GetMapping("/list")
    public List<Log> getlist(){
        QueryWrapper<Log> logQueryWrapper=new QueryWrapper<>();
        return logMapper.selectList(logQueryWrapper);
    }

}
