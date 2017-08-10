package com.ahzd.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahzd.dao.SysUserMongoDao;
import com.ahzd.pojo.SysUser;
@Service("sysUserMongoService")
public class SysUserMongoService {
	@Autowired
	private SysUserMongoDao sysUserMongoDao;
	
	/**
	 * 说明 ：获取用户列表
	 * @author yff 时间 ： 2017-04-17
	 */
	public Page<SysUser> getUserPage(Page<SysUser> page, SysUser queryBean) {
		if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(queryBean.getUserName())) {
				query.addCriteria(Criteria.where("userName").regex(".*?" + queryBean.getUserName().trim() + ".*"));
				page.setQueryBean(query);
			}
		}
		return sysUserMongoDao.getPageList(page);
	}
	
	/**
	 * 说明 ：保存
	 * @author yff 时间 ： 2017-04-17
	 */
	public  OpreateResult save(SysUser bean){
		sysUserMongoDao.save(bean);
		OpreateResult op = new OpreateResult();
		op.setStatusCode("200");
		op.setMessage("新增成功!");
		op.setNavTabId("userManager");
		op.setCallbackType("closeCurrent");
		return op;
	}
	
	/**
	 * 说明 ：获取全部
	 * @author yff 时间 ： 2017-04-17
	 */
	public List<SysUser>  findAll() {
		List<SysUser> list=sysUserMongoDao.findAll();
		return list;
	}
	

	
	/**
	 * 说明 ：根据id获取用户
	 * @author yff 时间 ： 2017-04-17
	 */
	public SysUser findById(String _id){
		return sysUserMongoDao.findById(_id);
	}
	
	/**
	 * 说明 ：修改
	 * @author yff 时间 ： 2017-04-17
	 */
	public OpreateResult updateUserInfo(SysUser sysUser) {
		OpreateResult op = new OpreateResult();
			Update update = new Update();
			if (!StringUtil.isBlank(sysUser.get_id())) {
				update.set("_id", sysUser.get_id());
			}
			if (!StringUtil.isBlank(sysUser.getEmail())) {
				update.set("email", sysUser.getEmail());
			}
			if (!StringUtil.isBlank(sysUser.getPhoneNumber())) {
				update.set("phoneNumber", sysUser.getPhoneNumber());
			}
			if (!StringUtil.isBlank(sysUser.getUserName())) {
				update.set("userName", sysUser.getUserName());
			}
			sysUserMongoDao.updateFirst(new Query(Criteria.where("_id").is(sysUser.get_id())), update);
			op.setStatusCode("200");
			op.setMessage("修改成功");
			op.setNavTabId("userManager");
//			op.setCallbackType("closeCurrent");
			return op;
	}
	
	/**
	 * 说明 ：修改
	 * @author yff 时间 ： 2017-04-17
	 */
	public OpreateResult delete(SysUser sysUser) {
		OpreateResult op = new OpreateResult();
		sysUserMongoDao.findAndRemove(new Query(Criteria.where("_id").is(sysUser.get_id())));
		op.setStatusCode("200");
		op.setMessage("删除成功!");
		op.setNavTabId("userManager");
		return op;
	}
}
