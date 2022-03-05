package com.tuzhi.mybatisplusstudy;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuzhi.mybatisplusstudy.mapper.UserMapper;
import com.tuzhi.mybatisplusstudy.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class MybatisplusStudyApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll() {
        System.out.println(userMapper.selectList(null));
    }
    @Test
    void findById() {
        System.out.println(userMapper.selectById(1));
    }
    @Test
    void insert() {
        System.out.println(userMapper.insert(new User("乐观锁测试1", "测试", "aa")));
    }
    @Test
    void update() {
        User user = new User();
        user.setId(2042732551);
        user.setName("测试1111");
        System.out.println(userMapper.updateById(user));
    }

    @Test
    void updateOptimistic() {
        User user = userMapper.selectById(2042732552);
        user.setPassword("123");
        System.out.println(userMapper.updateById(user));
    }
    @Test
    void deletes() {
        int[] a = new int[]{1,2,3};
        System.out.println(userMapper.deleteBatchIds(Arrays.asList(a)));
    }

    @Test
    void page() {
        Page<User> userPage = new Page<>(1, 3);
        userPage = userMapper.selectPage(userPage, null);
        System.out.println("当前页：" + userPage.getCurrent());
        System.out.println("每页数据List的集合：" + userPage.getRecords());
        System.out.println("每页显示的记录数：" + userPage.getSize());
        System.out.println("总记录数：" + userPage.getTotal());
        System.out.println("总页数：" + userPage.getPages());
        System.out.println("是否有下一页：" + userPage.hasNext());
        System.out.println("是否有上一页：" + userPage.hasPrevious());
    }
}
