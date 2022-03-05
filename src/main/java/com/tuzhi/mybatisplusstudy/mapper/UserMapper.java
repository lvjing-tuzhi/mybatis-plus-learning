package com.tuzhi.mybatisplusstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuzhi.mybatisplusstudy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @program: mybatisplus-study
 * @description:
 * @author: 兔子
 * @create: 2022-03-05 00:18
 **/

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
