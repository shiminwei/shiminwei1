package com.ahzd.framework.common.mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ahcd.pojo.Page;

/**
 * mongodb 基础操作类
 * 
 * @author oyhk
 * 
 *         2013-1-22下午5:28:26
 */
public abstract class MongodbBaseDao<T> {

	/**
	 * 通过条件查询,查询分页结果
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public Page<T> getPageList(Page<T> page) {
		Query query = page.getQueryBean() != null ? (Query) page.getQueryBean() : new Query();
		long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		query.skip(beginRow);// skip相当于从那条记录开始
		query.limit(page.getNumPerPage());// 从skip开始,取多少条记录
		List<T> datas = this.find(query);
		page.setResult(datas);
		page.setTotalCount((int) totalCount);
		return page;
	}

	/**
	 * 通过条件查询实体(集合)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * 查询并且修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update) {

		return this.mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	/**
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param bean
	 * @return
	 */
	public T save(T bean) {
		mongoTemplate.save(bean);
		return bean;
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * 通过ID获取记录,并且指定了集合名(表的意思)
	 * 
	 * @param id
	 * @param collectionName
	 *            集合名
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * 通过Name获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findByName(String name) {
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), this.getEntityClass());
	}

	public List<T> findByIn(String[] values) {
		return mongoTemplate.find(new Query(Criteria.where("name").in(values)),
				this.getEntityClass());
		// return mongoTemplate.find(new
		// Query(Criteria.where("name").in(values), this.getEntityClass());
	}

	public boolean checkExistByName(String name) {
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), this.getEntityClass()) != null ? true
				: false;
	}

	public void deleteByName(String name) {
		mongoTemplate.remove(new Query(Criteria.where("name").is(name)), this.getEntityClass());
	}

	public void deleteById(String id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), this.getEntityClass());
	}

	// 根据id判断是否已存在 2017-04-05 peixizhu
	public boolean checkExistById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), this.getEntityClass()) != null ? true
				: false;
	}

	// 分组查询
	public GroupByResults<T> groupBy(Criteria criteria, String collectionName, GroupBy groupBy) {
		return mongoTemplate.group(criteria, collectionName, groupBy, this.getEntityClass());
	}

	// count
	public long getCount(Query query) {
		return mongoTemplate.count(query, this.getEntityClass());
	}

	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * 注入mongodbTemplate
	 * 
	 * @param mongoTemplate
	 */
	protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

	/**
	 * spring mongodb 集成操作类
	 */
	protected MongoTemplate mongoTemplate;
	
	
	
}
