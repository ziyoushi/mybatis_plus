package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author changchen
 * @create 2019-11-06 23:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CRUDTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("chang2");
        user.setAge(33);
        user.setEmail("doublecc123@163.com");

        int insert = userMapper.insert(user);
        System.out.println(insert);
        System.out.println(user);
        System.out.println("新增后返回的id"+user.getId());
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(7L);
        user.setAge(28);
        //根据id修改用户
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    //获取当前时间的最后一秒
    @Test
    public void testSelectById() throws ParseException {
        User user = userMapper.selectById(7L);
        Date createTime = user.getCreateTime();
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(createTime);
        Date parse = sdf.parse(format);
        cal.setTime(parse);
        cal.set(Calendar.HOUR,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        Date time = cal.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = sdf1.format(time);
        System.out.println("form==="+format1);
    }

    //测试乐观锁
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById(1L);
        //修改
        user.setName("Jone Test");
        user.setAge(18);
        //执行更新
        userMapper.updateById(user);
    }

    //测试乐观锁失败案例
    @Test
    public void testOptimisticLockerFail(){
        User user = userMapper.selectById(1L);
        //修改
        user.setName("Jone Test1");
        user.setAge(18);

        //模拟另一个线程同时修改
        User user2 = userMapper.selectById(1L);
        //修改
        user2.setName("Jone Test2");
        user2.setAge(19);
        userMapper.updateById(user2);

        userMapper.updateById(user);

    }


    //通过多个id批量查询
    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //通过map封装查询条件
    @Test
    public void testSelectByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","Sandy");
        map.put("age",21);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);

    }

    //测试分页
    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
    }

    //测试逻辑删除
    @Test
    public void testLogicDelete(){
        int i = userMapper.deleteById(1L);
        System.out.println(i);
    }

    //测试逻辑删除后的查询
    @Test
    public void testLogicDeleteSelect(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    //测试合并
    @Test
    public void testMerge(){

    }



}
