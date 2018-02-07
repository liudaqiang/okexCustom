<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>

 <html>
 <head>
     <title>The Matrix</title>
     <meta charset="utf-8">
     <style>
     	.lq_input{
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
            .lq_input:focus{
                    border-color: #66afe9;
                    outline: 0;
                    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
                    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
            }
            .lq_button {
			    background-color: #4CAF50; /* Green */
			    border: none;
			    color: white;
			    padding: 15px 72px;
			    text-align: center;
			    text-decoration: none;
			    display: inline-block;
			    font-size: 16px;
			}
			.lq_button:hover{
				background-color: #ffffff;
				color:green;
				cursor: pointer;
			}
			.lq_a:hover{
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
          			<input class="lq_input" id="userName" type="text" onkeyup="this.value=this.value.replace(/\s+/g,'')" value="" placeholder="请输入账号">
          		</div>
          		<div style="position:relative;margin-top:15px;">
          			<input class="lq_input" id="email" type="text" onkeyup="this.value=this.value.replace(/\s+/g,'')" value="" placeholder="请输入邮箱">
          		</div>
          		<div style="position:relative;margin-top:15px;">
          			<input class="lq_input" id="password" type="text" onkeyup="this.value=this.value.replace(/\s+/g,'')" value="" placeholder="请输入密码">
          		</div>
          		<div style="position:relative;margin-top:15px;">
          			<input class="lq_input" id="wPassword" type="text" onkeyup="this.value=this.value.replace(/\s+/g,'')" value="" placeholder="请再次输入密码">
          		</div>
          		<div style="position:relative;margin-top:15px;">
          			<button class="lq_button" onclick="save()">注册</button>
          		</div>
          		
           </div>
     </div>
     <script type="text/javascript">
     	function save(){
     		var userName = $("#userName").val();
     		var password = $("#password").val();
     		var wPassword = $("#wPassword").val();
     		var email = $("#email").val();
     		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
     		if(userName.length < 8){
     			alert("用户名长度不能低于8位");
     		}
     		else if(userName.lengt > 32){
     			alert("用户名长度不能大于32位");
     		}
     		else if(userName.indexOf(" ") != -1){
     			alert("用户名中不能有空格");
     		}
     		else if(userName.match(/^[\u4e00-\u9fa5]+$/)){
     			alert("用户名禁止输入汉字");
     		}
     		else if(password.length<8){
     			alert("密码长度不得低于8位")
     		}
     		else if(password.lengt > 32){
     			alert("密码长度不能大于32位");
     		}
     		else if(password.indexOf(" ") != -1){
     			alert("密码中不能有空格");
     		}
     		else if(password.match(/^[\u4e00-\u9fa5]+$/)){
     			alert("密码禁止输入汉字");
     		}
     		else if(password != wPassword){
     			alert("两次密码输入不一致");
     		}
     		else if(email.length == 0){
     			alert("email不能为空");
     		}
     		else if(!reg.test(email)){
     			alert("email格式不正确");
     		}
     		else {
     			console.log("校验成功");
     			$.ajax({
     				url:"/user/saveUser",
     				type:"post",
     				data:{
     					"userName":userName,
     					"password":password,
     					"email":email
     				},
     				success:function(data,textStatus){
     					 console.log(data);
     			         console.log(textStatus);
     			        if(data.code == 200){
     			        	//成功
     			        	alert("注册成功");
     			        	window.location.href = "/page/toLogin";
     			        }else{
     			        	alert(data.message);
     			        }
     				}
     			})
     		}
     	}
     </script>
 </body>
 
 </html>