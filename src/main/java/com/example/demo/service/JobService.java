package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.QuartzBean;
import com.example.demo.mapper.JobMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
 
/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class JobService {
    @Autowired
    private JobMapper jobMapper;

	public PageInfo<QuartzBean> listQuartzBean(Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo,pageSize);
        List<QuartzBean> list = jobMapper.list();
		PageInfo<QuartzBean> page = new PageInfo<>(list);
        return page;
	}
}