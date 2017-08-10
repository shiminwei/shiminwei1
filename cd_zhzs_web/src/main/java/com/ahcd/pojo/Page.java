package com.ahcd.pojo;

import java.util.List;
import java.util.Map;

public class Page <E>{
	
	/**
	 * 默认查询条数
	 */
	public  final static  Integer PAGE_SIZE_DEFAULT=20;
	
	/**
	 * 当前页
	 */
	private Integer currentPage=1;
	
	
	/**
	 * 上一页
	 */
	private Integer pre=0;
	
	/**
	 * 下一页
	 */
	private Integer next=0;

	private int pageNum=1;
	
	/**
	 * 每页显示条数
	 */
	private Integer numPerPage=PAGE_SIZE_DEFAULT;
	
	/**
	 * 页数
	 */
	private Integer totalPage=0;
	
	
	/**
	 * 条数
	 */
	private Integer totalCount=0;
	
	/**
	 * 结果集
	 */
	private List<E> result;

	/**
	 * 合计数
	 */
	private List<String> totalaList;
	
	/**
	 *小计数
	 */
	private List<String> subtotalaList;

	private int beginRow;
	
	private int endRow;
	
	private Object queryBean;
	
	/**
	 * 合计数
	 */
	private Map<String,String> totalaMap;

	public Map<String, String> getTotalaMap() {
		return totalaMap;
	}

	public void setTotalaMap(Map<String, String> totalaMap) {
		this.totalaMap = totalaMap;
	}

	public Integer getCurrentPage() {
		currentPage=pageNum;
		
		 if (currentPage>totalPage) {
			 currentPage=totalPage;
		 }
		
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPre() {
		return pre;
	}

	public void setPre(Integer pre) {
		this.pre = pre;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	
	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}


	

	public Integer getTotalPage() {
		if(totalCount%numPerPage>0){
			totalPage= (totalCount/numPerPage+1);
		}else{
			totalPage= totalCount/numPerPage;
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public int getPageNum() {
		
	   return pageNum;
	 }
	 
	 public void setPageNum(int pageNum) {
	   this.pageNum = pageNum;
	 }
	public static Integer getPageSizeDefault() {
		return PAGE_SIZE_DEFAULT;
	}

	
	 public int getFirstOfPage()
	  {
	    return ((this.pageNum - 1) * this.numPerPage + 1);
	  }


	public List<String> getTotalaList() {
		return totalaList;
	}

	public void setTotalaList(List<String> totalaList) {
		this.totalaList = totalaList;
	}

	public List<String> getSubtotalaList() {
		return subtotalaList;
	}

	public void setSubtotalaList(List<String> subtotalaList) {
		this.subtotalaList = subtotalaList;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public Object getQueryBean() {
		return queryBean;
	}

	public void setQueryBean(Object queryBean) {
		this.queryBean = queryBean;
	}



	 
	 
}
