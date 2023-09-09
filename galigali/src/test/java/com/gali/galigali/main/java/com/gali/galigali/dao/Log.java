package com.gali.galigali.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Log {
    Integer id;
    String username;
    @TableField(value = "create_time")
    String createTime;
    String event;
}
