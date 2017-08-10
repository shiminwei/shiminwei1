package com.ahzd.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ahzd.framework.common.mongodb.MongodbBaseDao;
import com.ahzd.pojo.Article;

/**
 * ＤＡＯ层操作类
 * 
 * @author oyhk
 * 
 *         2013-1-21下午1:57:14
 */
@SuppressWarnings("static-access")
@Repository("ArticleDao")
public class ArticleDao extends MongodbBaseDao<Article> {

	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public Article findOne(Map<String, String> params) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.where("id").is(params.get("id"));
		query.addCriteria(criteria);
		return super.findOne(query);
	}

	/**
	 * 暂时通过ＩＤ去修改title
	 * 
	 * @param id
	 * @param params
	 */
	public void updateEntity(String id, Map<String, String> params) {
		super.updateFirst(Query.query(Criteria.where("id").is(id)),
				Update.update("title", params.get("title")));
	}

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<Article> getEntityClass() {
		return Article.class;
	}

	// 删除选中的记录

	public void removeRecord(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		super.findAndRemove(query);

	}

	// 动态条件查询   模糊查询
	public List<Article> getByActiveCondition(Map<String, String> params) {
		String id = params.get("id");
		String title = params.get("title");
		if((id!=null||id!="")&&(title==null||title=="")){
			Query query = new Query(new Criteria("id").regex(".*?"+id+".*")).limit(9);  
			return super.find(query);
		}
		if((id==null||id=="")&&(title!=null||title!="")){
			Query query = new Query(new Criteria("title").regex(".*?"+title+".*")).limit(9);  
			return super.find(query);
		}
		if((id!=null||id!="")&&(title!=null||title!="")){
			Query query = new Query((new Criteria("id").regex(".*?"+id+".*").and("title").regex(".*?"+title+".*")));
			return super.find(query);
		}
		return null;
	}
}
