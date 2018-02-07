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
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		  <legend>表单集合演示</legend>
		</fieldset>
		  <form class="layui-form" action="">
		  	  
			  <div class="layui-form-item">
			    <div class="layui-inline">
			      <label class="layui-form-label">搜索选择框</label>
			      <div class="layui-input-inline">
			        <select name="coin" lay-verify="required" lay-search="">
			          <option value="">直接选择或搜索选择</option>
			          <c:forEach items="${biteNames}" var="biteName">
			          	 <option value="${biteName}">${biteName}</option>
			          </c:forEach>
			        </select>
			      </div>
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">挂单金额</label>
			    <div class="layui-input-block">
			      <input type="text" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,10})?/)||[''])[0]" name="price"  lay-verify="required|number|minInsertPrice"   placeholder="请输入" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">挂单数量</label>
			    <div class="layui-input-block">
			      <input type="text" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" name="amount"  lay-verify="required|number|minInsertAmount"   placeholder="请输入" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			 <div class="layui-form-item" pane="">
			    <label class="layui-form-label">买单/卖单</label>
			    <div class="layui-input-block">
			      <input type="radio" name="type" value="buy" title="买" checked="">
			      <input type="radio" name="type" value="sell" title="卖">
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
		<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	</div>
	
	<script src="${ctxStatic}/layui/layui.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery1_11.js" charset="utf-8"></script>
	<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		  
		  //日期
		  laydate.render({
		    elem: '#date'
		  });
		  laydate.render({
		    elem: '#date1'
		  });
		  
		 
		  //自定义验证规则
		  form.verify({
		    title: function(value){
		      if(value.length < 5){
		        return '标题至少得5个字符啊';
		      }
		    }
		    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
		    ,content: function(value){
		      layedit.sync(editIndex);
		    },minInsertPrice:function(value){
		    	console.log(value);
		    	if(value < 0.0000000001){
		    		return "不得低于0.0000000001";
		    	}
		    },minInsertAmount:function(value){
		    	console.log(value);
		    	if(value < 0.01){
		    		return "不得低于0.01";
		    	}
		    }
		  });
		  
		  
		  //监听提交
		  form.on('submit(demo1)', function(data){
		    console.log(data.field);
		    entrustAPI(data.field.amount,data.field.price,data.field.type,data.field.coin);
		    return false;
		  });
		});
	function entrustAPI(amount,price,type,coin){
		var allPrice = amount*price;
		if(allPrice < 0.001){
			 layer.alert("交易金额不得低于0.001BTC");
		}
		$.ajax({
			url:"/entrust/entrustDown",
			type:"post",
			data:{
				"amount":amount,
				"price":price,
				"type":type,
				"coin":coin
			},
			success:function(data,textStatus){
				 console.log(data);
		         console.log(textStatus);
		         if(data.code == 100){
		        	 layer.alert(data.errorMessage);
		         }else{
		        	 var code = data.code;
		        	 if(code == 200){
		        		 layer.alert("200成功");
		        		//询问框
		        		 layer.confirm('委托已提交成功，是否继续委托?', {
		        		   btn: ['继续委托','去委托列表看看'] //按钮
		        		 }, function(){
		        		 }, function(){
		        			
		        		 });
		        	 }else{
		        		 checkErrorCode(code);
		        	 }
		         }
			}
		});
	}
	function checkErrorCode(code){
		 if(code == 505){
			 //余额不足
			 layer.alert("余额不足");
		 }else if(code == 405){
			 //币种交易暂时关闭
			 layer.alert("币种交易暂时关闭");
		 }else if(code == 206){
			 //小数位错误
			 layer.alert("小数位错误");
		 }else if(code == 205){
			 //限制挂单价格
			 layer.alert("限制挂单价格");
		 }else if(code == 204){
			 //挂单金额必须在 0.001 BTC以上
			 layer.alert("挂单金额必须在 0.001 BTC以上");
		 }else if(code == 203){
			 //订单不存在
			 layer.alert("订单不存在");
		 }else if(code == 401){
			 //系统错误
			 layer.alert("系统错误");
		 }else if(code == 402){
			 //请求过于频繁
			 layer.alert("请求过于频繁");
		 }else if(code == 403){
			 //非开放API
			 layer.alert("非开放API");
		 }else if(code == 404){
			 //IP限制不能请求该资源
			 layer.alert("IP限制不能请求该资源");
		 }else if(code == 202){
			 //下单价格必须在 0 - 1000000 之间
			 layer.alert("下单价格必须在 0 - 1000000 之间");
		 }else if(code == 201){
			 //买卖的数量小于最小买卖额度
			 layer.alert("买卖的数量小于最小买卖额度");
		 }else if(code == 200){
			 //余额不足
			 layer.alert("余额不足");
		 }else if(code == 106){
			 //请求过期(nonce错误)
			 layer.alert("请求过期(nonce错误)");
		 }
	}
	</script>
	
</body>
</html>