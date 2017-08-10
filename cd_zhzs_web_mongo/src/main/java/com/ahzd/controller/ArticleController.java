package com.ahzd.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ahzd.pojo.Article;
import com.ahzd.service.ArticleService;


public class ArticleController {

	private static ApplicationContext app;
	private static ArticleService articleService;


	@Test
	public void save() {
		for (int i = 1; i < 21; i++) {
			Article a = new Article();
			a.setTitle("mongodb开始实战" + i);
			a.setContent("mongodb开始实战..内容" + i);
			articleService.save(a);
		}
	}

	@Test
	public void findArticle() {
		Article a = articleService.findByid("50fd0c36bc40ceec1a44308b");
		System.out.println(a);
	}

	@Test
	public void update() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "修改内容...");
		articleService.update("50fe23e6bc402ee4051f90b8", params);
	}

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
		ArticleService art = (ArticleService) ac.getBean("articleService");
		Article artc = new Article();
    	//User user=new User();
//		user.setId("45555255274783");
//    	user.setName("Q妹");
//    	userService.saveUser(user);
//    	List<User> list = userService.searchFriends("zhang");
//    	System.out.println(list.size());
//    	WriteResult result = userService.removeUserByUserName("wangwu");
//			artc.setId("20170330");
//			artc.setContent("123");
//			artc.setTitle("zhizhi");
//			art.save(artc);
//	System.out.println(result);
//		System.out.println(art.findByid("20170330").getContent());
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("id", "201dd70330");
//		map.put("content", "123");
		/*List<Article> list =  art.findA(map);
		String content ="";
		for (int i = 0; i < list.size(); i++) {
			content=list.get(i).getContent();
			System.out.println(content);
		}*/
//		System.out.println(art.findB(map));
//		
//		//根据title删除相应的记录
//		String  id ="58dcaac7d749d075fa8cbcb0";
//		System.out.println(art.findByid(id).getContent());
		
		Article arti = new Article();
		arti.setId("20170330");
		arti.setContent("你好世界");
		arti.setTitle("say hello to world");
		art.updateMessageByid(arti);
	}
}
