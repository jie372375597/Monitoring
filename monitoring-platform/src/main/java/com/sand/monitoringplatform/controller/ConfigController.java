package com.sand.monitoringplatform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.monitoringplatform.pojo.Message;

@Controller
@RequestMapping(value = "/monitoring")
public class ConfigController {
	
	@RequestMapping(value = "/config")
	public String  test(HashMap<String, Object> map) {
		map.put("hello", "欢迎进入HTML页面");
        return "/index";

	}
	@ResponseBody
    @RequestMapping("/list")
    public List<Message> list(){
        List<Message> list=new ArrayList<>();
        Message message=new Message();
        message.setId("1");
        message.setCommand("许嵩");
        message.setDescription("歌手");
        message.setContent("最佳歌手");
        Message message1=new Message();
        message1.setId("2");
        message1.setCommand("蜀云泉");
        message1.setDescription("程序员");
        message1.setContent("不断成长的程序员");
        list.add(message);
        list.add(message1);
        return list;
    }

}
