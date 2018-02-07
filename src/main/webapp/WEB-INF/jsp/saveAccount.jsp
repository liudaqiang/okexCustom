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
		    <!-- 内容主体区域 -->
		    <div style="padding: 15px;">
		    	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				  <legend>表单集合演示</legend>
				</fieldset>
				 
				<form class="layui-form" action="">
				  <div class="layui-form-item">
				    <label class="layui-form-label">public_key</label>
				    <div class="layui-input-block">
				      <input type="text" name="publicKey" lay-verify="publicKey" autocomplete="off" placeholder="请输入public_key" class="layui-input">
				    </div>
				  </div>
				 <div class="layui-form-item">
				    <label class="layui-form-label">private_key</label>
				    <div class="layui-input-block">
				      <input type="text" name="privateKey" lay-verify="privateKey" autocomplete="off" placeholder="请输入private_key" class="layui-input">
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <div class="layui-input-block">
				      <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				  </div>
				</form>
		    </div>
		</div>
		<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	</div>
	
	<script src="${ctxStatic}/layui/layui.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery1_11.js" charset="utf-8"></script>
	<script>
	$(function(){
	})
	layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  layer.alert('${message}');
	  //日期
	  laydate.render({
	    elem: '#date'
	  });
	  laydate.render({
	    elem: '#date1'
	  });
	  
	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	 
	  //自定义验证规则
	  form.verify({
		publicKey: function(value){
	      if(value.length < 5){
	        return 'public_key太短啦';
	      }
	    }
	  	,privateKey: function(value){
  		  if(value.length < 5){
 	        return 'private_key太短啦';
 	      }
	  	}
	  });
	  
	  
	  //监听提交
	  form.on('submit(demo1)', function(data){
	    checkAccountValid(data.field.publicKey,data.field.privateKey);
	    return false;
	  });
	  
	  
	});
	function checkAccountValid(publicKey,privateKey){
		$.ajax({
			url:'/account/checkAccount',
			type:'POST',
			async:false,
			data:{
		        publicKey:publicKey,
		        privateKey:privateKey
		    },
		    dataType:'json',
		    success:function(data,textStatus){
		    	 console.log(data);
		         console.log(textStatus);
		         if(data.code == 200){
		        	 save(publicKey,privateKey);
		         }else{
		        	 layer.alert(data.message, {
		       	      title: '校验失败'
		       	    });
		         }
		    },
		    complete:function(){
		        console.log('结束')
		    }
		})
	}
	function save(publicKey,privateKey){
		$.ajax({
			url:'/account/save',
			type:'POST',
			async:false,
			data:{
		        publicKey:publicKey,
		        privateKey:privateKey
		    },
		    dataType:'json',
		    success:function(data,textStatus){
		    	 console.log(data);
		         console.log(textStatus);
		         if(data.code == 200){
		        	 window.location.href = "/page/toAccountInfo";
		         }else{
		        	 layer.alert(data.message, {
		       	      title: '保存失败'
		       	    });
		         }
		    },
		    complete:function(){
		        console.log('结束')
		    }
		})
	}
	</script>
	
	
</body>
</html>