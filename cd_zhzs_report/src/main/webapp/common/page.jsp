<!-- 用户选项卡窗口的分页组件 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			
			<strong>
			<script>  
                $("select[name='numPerPage']").val('${pageList.numPerPage}');  
            </script>
            </strong>  
			<span>条，共${pageList.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage}" pageNumShown="10" currentPage="${pageList.pageNum}"></div>
	</div>