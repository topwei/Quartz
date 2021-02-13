package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.Page;
 
/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */
 
@Controller
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    private UserService userService;
 
    @RequestMapping("getUser/{id}")
    @ResponseBody
    public String getUser(@PathVariable int id){
        return userService.select(id).toString();
    } 
    
    @RequestMapping("list")
    public String list(Model model){
        return "/user.html";
    }
    
    @RequestMapping("add")
    @ResponseBody
    public String add(User user) {
    	userService.add(user);
		return "添加成功！";
    }
    
    @RequestMapping("listData")
    @ResponseBody
    public Map<String, Object> listData(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize) {
        Page<User> page = userService.list(pageNum,pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagestudentdata", page);
        map.put("number", page.getTotal());
        return map;
    }
}