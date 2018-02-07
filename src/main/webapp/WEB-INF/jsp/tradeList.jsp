<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctxStatic}/layui/css/layui.css"  media="all">
<title>Insert title here</title>
<style type="text/css">
	.cancelTrade{cursor: pointer;}
</style>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<%@ include file="/WEB-INF/jsp/common/top.jsp"%>
			<%@ include file="/WEB-INF/jsp/common/left.jsp"%>
		</div>
		<div class="layui-body">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			  <legend>挂单列表查询</legend>
			</fieldset>
			<form class="layui-form" action="">
			  <div class="layui-form-item">
			    <div class="layui-inline">
			      <label class="layui-form-label">搜索选择框</label>
			      <div class="layui-input-inline">
			        <select  name="modules" lay-filter="biteSelect" lay-verify="required" lay-search="">
			          <c:forEach items="${biteNames}" var="biteName">
			         	<option value="${biteName}">${biteName}</option>
			          </c:forEach>
			        </select>
			      </div>
			    </div>
			  </div>
			</form>
			<div class="demoTable">
			</div>
			 
			<table class="layui-hide" id="LAY_table_user" lay-filter="user"></table> 
		</div>
		<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	</div>
	<script src="${ctxStatic}/layui/layui.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery1_11.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery.cookie.js" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery/jquery.number.min.js" charset="utf-8"></script>
	<script>
	var nowBite = '';
	$(function(){
		var datasInit = [];
		useData(datasInit);
		$("body").on("click",".cancelTrade",function(){
			cancelTrade($(this).attr("revokeId"));
		}); 
	})
	layui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		  var table = layui.table;
		  form.on('select(biteSelect)', function(data){
			  getBiteData(data.value);
			  nowBite = data.value;
		  });  
		
		});

	function getBiteData(biteName){
		$.ajax({
			url:"/entrust/entrustTradeList",
			method:"post",
			data:{
				'biteName':biteName
			},
			success:function(data,textStatus){
				console.log(data);
				//checkErrordCode
				if(data.code == 100){
					layer.alert(data.errorMessage);
				}else{
					if(data.data.result){
						var datas = data.data.data;
						if(datas == null){
							datas=[];
						}else{
							for(var i=0;i<datas.length;i++){
								//price格式化
								console.log(getPrice(datas[i].price));
								datas[i].price = parseFloat(getPrice(datas[i].price)).toFixed(8);
								//实际交易金额计算
								var original = datas[i].amount_original;
								if(original == null){
									original = 0;
								}
								var outstanding = datas[i].amount_outstanding;
								if(outstanding == null){
									outstanding = 0;
								}
								var price = datas[i].price;
								if(datas[i].price == null){
									price = 0;	
								}
								if(original == outstanding){
									datas[i].factAmount = 0;
								}else{
									datas[i].factAmount = getPrice(price * (original-outstanding)).toFixed(8);
								}
								//买卖格式化
								var factType = datas[i].type;
								if(factType == 'buy'){
									datas[i].type = "买单";
								}else{
									datas[i].type = "卖单";
								}
								//委托状态格式化
								var factStatus = datas[i].status;
								if(factStatus == "open"){
									datas[i].status = "正在挂单";
									datas[i].revoke="<a class='cancelTrade' revokeId='"+datas[i].id+"'>点击撤销</a>"
								}else if(factStatus == "cancelled"){
									datas[i].status = "已经取消";
								}else{
									datas[i].status = "已经成交";
								}
								datas[i].dateTime = getTime(datas[i].dateTime);
							}
						}
					}else{
						checkErrordCode(data.data.code);
					}
				}
				
				
				useData(datas);
			}
		});
	}
	function getPrice(num){
		//var num = 0.000000001;
	    if(num<0.0001){
	        var num1=num*1000000;
	        var num2=parseInt(num1)/1000000;
	        if(num1 >= 1){
	        	var strNum1 = num1+"";
	        	var indexOf = strNum1.indexOf(".");
	        	var strNums = strNum1.split(".");
	        	var strLeft = strNums[0];
	        	var strRight = strNums[1];
	        	var retNum = 6-strLeft.length;
	        	var retStr="";
	        	for(var i=0;i<retNum;i++){
	        		if(i==0){
	        			retStr+="0.";
	        		}else{
	        			retStr+="0";
	        		}
	        	}
	        	retStr = retStr+strLeft+strRight;
	        	return retStr;
	        }else{
	        	var strNum1 = num1+"";
	        	var indexOf = strNum1.indexOf(".");
	        	var strNums = strNum1.split(".");
	        	var strLeft = strNums[0];
	        	var strRight = strNums[1];
	        	var retStr = '';
	        	retStr = retStr+strLeft+".000000"+strRight;
	        	return retStr;
	        }
	    }else {
	        var num2=num;
	        return num2;
	    }

	    
	    return Number(num2);
	}
	
	function getTime(date){
		var time = new Date(date);
		var y = time.getFullYear();//年
		var m = time.getMonth() + 1;//月
		var d = time.getDate();//日
		var h = time.getHours();//时
		var mm = time.getMinutes();//分
		var s = time.getSeconds();//秒
		return y+"-"+m+"-"+d+" "+h+":"+mm+":"+s;
	}
	
	function useData(codeData){
		layui.use('table', function(){
			  var table = layui.table;
			  
			  //方法级渲染
			  table.render({
			    elem: '#LAY_table_user'
			    ,data: codeData
			    ,cols: [[
			      {field:'id', title: '委托ID', width:80, sort: true, fixed: true}
			      ,{field:'price', title: '单价/btc', width:150, sort: true}
			      ,{field:'amount_original', title: '委托时数量', width:150, sort: true}
			      ,{field:'amount_outstanding', title: '当前剩余数量', width:150}
			      ,{field:'factAmount',title:'实际交易额',width:150,sort:true}
			      ,{field:'status', title: '委托状态', sort: true,width:150}
			      ,{field:'dateTime', title: '时间', sort: true, width:200, sort: true}
			      ,{field:'type', title: '买/卖', sort: true, width:80, sort: true}
			      ,{field:'revoke', title: '撤单',  width:100}
			    ]]
			    ,id: 'testReload'
			    ,page: true
			  });
			  
			  var $ = layui.$, active = {
			    reload: function(){
			      var demoReload = $('#demoReload');
			      
			      //执行重载
			      table.reload('testReload', {
			        page: {
			          curr: 1 //重新从第 1 页开始
			        }
			        ,where: {
			          key: {
			            id: demoReload.val()
			          }
			        }
			      });
			    }
			  };
			  
			  $('.demoTable .layui-btn').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  });
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
	function cancelTrade(id){
		$.ajax({
			url:"/entrust/entrustCancel",
			method:"post",
			data:{
				"id":id,
				"biteName":nowBite
			},
			success:function(data,textStatus){
				console.log(data);
				if(data.code == 200){
					if(data.data.result){
						layer.alert("撤销成功");
						getBiteData(nowBite);
					}else{
						checkErrorCode(data.data.data.code);
					}
				}else{
					layer.alert(data.errorMessage);
				}
			}
		})
	}
	</script>
</body>
</html>