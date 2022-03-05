package com.tuzhi.mybatisplusstudy.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @program: mybatisplus-study
 * @description:
 * @author: 兔子
 * @create: 2022-03-05 00:17
 **/

@Data
@NoArgsConstructor
public class User{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String perm;

//    在插入时进行自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
//    在插入更新时自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

//    乐观锁版本
    @Version
//    默认值1
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    public User(String name, String password, String perm) {
        this.name = name;
        this.password = password;
        this.perm = perm;
    }
}
