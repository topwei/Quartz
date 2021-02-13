package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
 
/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User select(int id){
        return userMapper.select(id);
    }
    
	public void add(User user) {
		// TODO Auto-generated method stub
		userMapper.addUser(user);
	}
	
	public Page<User> list(int pageNo, int pageSize) {
		Page<User> page = PageHelper.startPage(pageNo,pageSize);
        List<User> userList = userMapper.list();
        return page;
	}
}