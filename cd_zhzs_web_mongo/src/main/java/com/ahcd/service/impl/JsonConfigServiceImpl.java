package com.ahcd.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ahcd.common.FileUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.dbutil.DBconnUtil;
import com.ahcd.dbutil.JDBCUtils;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahcd.service.IJsonConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("jsonConfigService")
public class JsonConfigServiceImpl implements IJsonConfigService {

	private final static String datasourcePath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("datasource");

	/**
	 * 
	 * @param page
	 *            分页对象
	 * @param queryBean
	 *            查询条件
	 * @return
	 */
	public Page<DatasourceBean> getDatasourcePage(Page<DatasourceBean> page, DatasourceBean queryBean) {
		Page<DatasourceBean> pageList = new Page<DatasourceBean>();
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			List<DatasourceBean> allResultList = new ArrayList<DatasourceBean>();
			int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
			int endRow = page.getPageNum() * page.getNumPerPage();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + "/" + filelist[i];
					//只读取配置文件
					if (!fileName.endsWith(".json") ) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db == null)
							continue;
						boolean flag = true;
						if (queryBean != null) {
							if (db.getName() != null && queryBean.getName() != null
									&& !db.getName().contains(queryBean.getName())) {
								flag = false;
							}
							if (db.getId() != null && queryBean.getId() != null
									&& !db.getId().contains(queryBean.getId())) {
								flag = false;
							}
						}
						if (flag)
							allResultList.add(db);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
			List<DatasourceBean> resultList = new ArrayList<DatasourceBean>();
			for (int i = 0; i < allResultList.size(); i++) {
				if (i >= beginRow && i < endRow) {
					resultList.add(allResultList.get(i));
				}
			}
			pageList.setResult(resultList);
			pageList.setTotalCount(allResultList.size());
			pageList.setPageNum(page.getPageNum());
			pageList.setNumPerPage(page.getNumPerPage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageList;
	}

	@Override
	public List<String> getAllDatasourceId() {
		List<String> allResultList = new ArrayList<String>();
		File file = new File(datasourcePath);
		String[] filelist = file.list();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				String fileName = filelist[i].contains(".") ? filelist[i].substring(0, filelist[i].lastIndexOf("."))
						: filelist[i];
				allResultList.add(fileName);
			}
		} else {
			System.out.println("文件夹不不存在");
		}
		return allResultList;
	}

	public OpreateResult saveDatasource(DatasourceBean db) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = datasourcePath + db.getId() + ".json";
			FileUtil.makeDirectory(filePath);
			if (FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件已存在");
			} else {
				if (DBconnUtil.testConnection(db)) {
					String jsonString = JSON.toJSONString(db, SerializerFeature.WriteMapNullValue);
					Writer write = new FileWriter(filePath);
					write.write(jsonString.toString());
					write.flush();
					write.close();
					op.setStatusCode("200");
					op.setMessage("保存成功");
					op.setNavTabId("datasourceList");
					op.setCallbackType("closeCurrent");
				} else {
					op.setStatusCode("300");
					op.setMessage("数据库连接失败，请确认无误后提交");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	public OpreateResult updateDatasource(DatasourceBean db) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = datasourcePath + db.getId() + ".json";
			if (!FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件不存在");
			} else {
				JDBCUtils.close(db.getId());
				if (DBconnUtil.testConnection(db)) {
					String jsonString = JSON.toJSONString(db, SerializerFeature.WriteMapNullValue);
					Writer write = new FileWriter(filePath);
					write.write(jsonString.toString());
					write.flush();
					write.close();
					JDBCUtils.reloadDatasource();
					op.setStatusCode("200");
					op.setMessage("保存成功");
					op.setNavTabId("datasourceConfigList");
					op.setCallbackType("closeCurrent");
				} else {
					op.setStatusCode("300");
					op.setMessage("数据库连接失败，请确认无误后提交");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	public DatasourceBean getDatasourceById(String id) {
		DatasourceBean datasourceBean = new DatasourceBean();
		boolean isExist = false;
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + "/" + filelist[i];
					//只读取配置文件
					if (!fileName.endsWith(".json") ) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db != null && db.getId() != null && db.getId().equals(id)) {
							datasourceBean = db;
							isExist = true;
						}
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isExist) {
			return datasourceBean;
		} else {
			return null;
		}
	}

	public OpreateResult deleteDatasourceById(String id) {
		OpreateResult op = new OpreateResult();
		String filePath = datasourcePath + id + ".json";
		if (!FileUtil.isFileExist(filePath)) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			JDBCUtils.close(id);
			FileUtil.deleteFile(filePath);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}

	@Override
	public List<DatasourceBean> getAllDatasource() {
		List<DatasourceBean> allResultList = new ArrayList<DatasourceBean>();
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + "/" + filelist[i];
					//只读取配置文件
					if (!fileName.endsWith(".json") ) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db == null)
							continue;
						allResultList.add(db);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allResultList;
	}
public static void main(String[] args) {
	JsonConfigServiceImpl impl=new JsonConfigServiceImpl();
	DatasourceBean datasourceBean= impl.getDatasourceById("1");

}
}
