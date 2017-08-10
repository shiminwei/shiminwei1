<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>

  <script type="text/javascript" src="${basePath}static/js/jquery-2.1.4.min.js"></script>
  <script type="text/javascript" src="${basePath}static/uploadify/swfobject.js"></script>
  <script type="text/javascript" src="${basePath}static/uploadify/scripts/jquery.uploadify.js"></script>

<h2 class="contentTitle">uploadify多文件上传</h2>


<div class="pageContent" style="margin: 0 10px" layoutH="50">



    <input id="testFileInput2" type="file" name="image2" 
            uploaderOption="{
                swf:'static/uploadify/scripts/uploadify.swf',
                uploader:'web/dataReport/report',
                formData:{templateName:'xxx', ajax:1},
                queueID:'fileQueue',
                buttonImage:'static/uploadify/img/add.jpg',
                buttonClass:'my-uploadify-button',
                width:102,
                auto:false
            }"
        />
    
        <div id="fileQueue" class="fileQueue"></div>
        <input type="image" src="static/uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
        <input type="image" src="static/uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>


</div>