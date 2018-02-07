<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>

 <html>
 <head>
     <title>The Matrix</title>
     <meta charset="utf-8">
     <style>
     	input{
                border: 1px solid #ccc;
                padding: 7px 0px;
                border-radius: 3px;
                padding-left:5px;
                -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
                box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
                -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
                -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
                transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s
            }
            input:focus{
                    border-color: #66afe9;
                    outline: 0;
                    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
                    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
            }
            button {
			    background-color: #4CAF50; /* Green */
			    border: none;
			    color: white;
			    padding: 15px 28px;
			    text-align: center;
			    text-decoration: none;
			    display: inline-block;
			    font-size: 16px;
			}
			button:hover{
				background-color: #ffffff;
				color:green;
				cursor: pointer;
			}
			a:hover{
				cursor: pointer;
			}
     </style>
     <script src="${ctxStatic}/jquery/jquery1_11.js" charset="utf-8"></script>
     <script>
     /*
          ① 用setInterval(draw, 33)设定刷新间隔
     
          ② 用String.fromCharCode(1e2+Math.random()*33)随机生成字母
     
          ③ 用ctx.fillStyle=’rgba(0,0,0,.05)’; ctx.fillRect(0,0,width,height); ctx.fillStyle=’#0F0′; 反复生成opacity为0.5的半透明黑色背景
     
          ④ 用x = (index * 10)+10;和yPositions[index] = y + 10;顺序确定显示字母的位置
     
         ⑤ 用fillText(text, x, y); 在指定位置显示一个字母 以上步骤循环进行，就会产生《黑客帝国》的片头效果。
      */
         $(document).ready(function() {
                //设备宽度
                var s = window.screen;
                 var width = q.width = s.width;
                var height = q.height;
                var yPositions = Array(300).join(0).split('');
                 var ctx = q.getContext('2d');
                var draw = function() {
                         ctx.fillStyle = 'rgba(0,0,0,.05)';
                         ctx.fillRect(0, 0, width, height);
                         ctx.fillStyle = 'green';/*代码颜色*/
                         ctx.font = '10pt Georgia';
                         yPositions.map(function(y, index) {
                                 text = String.fromCharCode(1e2 + Math.random() * 330);
                                 x = (index * 10) + 10;
                                 q.getContext('2d').fillText(text, x, y);
                                 if (y > Math.random() * 1e4) {
                                        yPositions[index] = 0;
                                     } else {
                                        yPositions[index] = y + 10;
                                     }
                            });
                     };
                RunMatrix();
               function RunMatrix() {
                        Game_Interval = setInterval(draw,30);
                     }
             });
     </script>
 </head>
 <body>
 	 <div align="center" style=" position:fixed; left:0; top:0px; width:100%; height:100%;">
           <canvas id="q" width="1440" height="900">
           </canvas>
           <div style="position:absolute;left:50%;top:150px;width:500px;margin-left:-250px;">
           		<div  style="position:relative;">
           			<img alt="" height="100" width="100" style="" src="${ctxStatic}/img/bite.png">
           		</div>
          		<div style="position:relative;margin-top:30px;">
          			<input type="text" id="userName" value="" placeholder="请输入账号">
          		</div>
          		<div style="position:relative;margin-top:15px;">
          			<input type="text" value="" id="password" placeholder="请输入密码">
          		</div>
          		
          		<div style="position:relative;margin-top:15px;">
          			<button onclick="register()">注册</button>
          			<button onclick="login()">登陆</button>
          			<div style="position:relative;margin-top:5px;margin-left:110px;"><a><font color=white>忘记密码</font></a></div>
          		</div>
          		
           </div>
     </div>
     <script type="text/javascript">
     	function register(){
     		window.location.href = "/page/toRegister";
     	}
     	function login(){
     		$.ajax({
     			url : "/user/login",
     			type : "post",
     			data :{
     				"userName":$("#userName").val(),
     				"password":$("#password").val()
     			},success:function(data,textStatus){
					 console.log(data);
 			         console.log(textStatus);
 			        if(data.code == 200){
 			        	//成功
 			        	window.location.href = "/page/toIndex";		        	
 			        }else{
 			        	alert(data.errorMessage);
 			        	console.log("失败");
 			        }
 				}
     		})
     	}
     </script>
 </body>
 
 </html>