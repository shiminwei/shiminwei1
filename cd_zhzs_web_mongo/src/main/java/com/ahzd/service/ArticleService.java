package com.ahzd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahzd.dao.ArticleDao;
import com.ahzd.pojo.Article;


@Service("articleService")
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public Article findByid(String id) {
		return (Article) articleDao.findById(id);
	}

	public Article findOne(Map<String, String> params) {
		return this.articleDao.findOne(params);
	}

	public Article save(Article bean) {
		return (Article) articleDao.save(bean);
	}
	/**
	 * 暂时只是固定去修改,会有下一篇博客,写高级修改...
	 */
	public void update(String id, Map<String, String> params) {
		this.articleDao.updateEntity(id, params);
	}
	
	
	
	//删除选中的记录
	public void removeRecord(String id ){
		
		this.articleDao.removeRecord(id);
	}
	
	//动态条件查询
	 public List<Article>getByActiveCondition(Map<String,String> params){
		 
		 return  articleDao.getByActiveCondition(params);
	 }
	 //更新内容
	 public void updateMessageByid(Article article){
		 if(article!=null){
			 Update update = new Update();
			 if(!StringUtil.isBlank(article.getContent())){
				 update.set("content",article.getContent());
			 }
			 if(!StringUtil.isBlank(article.getTitle())){
				 update.set("title", article.getTitle());
			 }
			 articleDao.updateFirst(new Query(Criteria.where("_id").is(article.getId())), update);
		 }
		 
	 }
}
