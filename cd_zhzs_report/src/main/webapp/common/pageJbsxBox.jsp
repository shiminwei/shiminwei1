<!-- 用于局部刷新页面的分页组件 -->
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, '${param.jbsxBoxId }')">
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
		<div class="pagination" rel="${param.jbsxBoxId }" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage}" pageNumShown="10" currentPage="${pageList.pageNum}"></div>
	</div>