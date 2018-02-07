<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctxStatic}/layui/css/layui.css"  media="all">
<title>Insert title here</title>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<%@ include file="/WEB-INF/jsp/common/top.jsp"%>
			<%@ include file="/WEB-INF/jsp/common/left.jsp"%>
		</div>
		<div class="layui-body">
			
			<table class="layui-hide" id="myTable"></table>
		</div>
		<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	</div>
	<script src="${ctxStatic}/layui/layui.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery1_11.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery.cookie.js" charset="utf-8"></script>
	<script>
	layui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		});
	
	$(function(){
		$.ajax({
			url:"/account/getUserInfo",
			method:"POST",
			data:{
				
			},
			success:function(data,textStatus){
				 console.log(data);
		         console.log(textStatus);
		         if(data.code == 200){
		        	 getTable(data.data);
		         }else{
		        	 layer.alert(data.message, {
		       	      title: '校验失败'
		       	  });
		         }
			}
		})
	})
	function getTable(data){
		layui.use('table', function(){
			  var table = layui.table;
			  table.render({
			    elem: '#myTable'
			    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
			    ,cols: [[
			      {field:'biteName', width:100, title: '币种'}
			      ,{field:'blance', minWidth:180, title: '总量',sort: true}
			      ,{field:'lock', minWidth:180, title: '冻结数量', sort: true}
			    ]],
			    data:data
			    ,page: true
			  });
			});
	}
	</script>
</body>
</html>