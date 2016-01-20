<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/login.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.validate.js"></script>
<script type="text/javascript">
boolean flag=false;
$(function(){
	$("#myForm").cmsvalidate();
	//alert($("#contextPath").val()+"/admin/logout");
	alert("11 ");
	if($("#id").val()=="1") {
		$("#id").val("value");
		alert($("#id").val());
		
	}
});
</script>
<title>后台管理登录</title> 
<script type="text/javascript">
function reCheckcode(img) {
	img.src="<%=request.getContextPath() %>/drawCheckCode?"+Math.random();
}

</script>
</head>
<body>
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
	<div id="container">
	<input type="hidden" id="id" value="${id}"/>
		<div id="top">
		
		</div>
		<div id="loginBar">
			<span id="showDate">欢迎你使用昭通学院附中网站后台管理程序，请登录</span>
		</div>
		<div id="content">
			<div id="loginForm">
			<form method="post" id="myForm" action="<%=request.getContextPath() %>/login">
					<table cellpadding="0" cellspacing="0" width="395px" border="0"  bordercolor="white" id="loginTable">
						<tr>
							<td align="right" width="90">登录用户:</td>
							<td align="left"><input type="text" name="username" size="25"/> </td>
						    <td></td>
						 </tr>
						<tr>
							<td align="right">登录密码:</td>
							<td align="left"><input type="password" name="password" size="25"/></td>
						<td></td>
						</tr>
						<tr>
							<td align="right">输入验证码:</td>
							<td >
							<input type="text" name="checkcode" id="validateCode" size="15"/></td>
							<td align="left">
							  <span style="margin-left:1px;cursor:pointer">
							   <img src="<%=request.getContextPath() %>/drawCheckCode" onclick="reCheckcode(this)"/></span>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="3">
							${error}
							</td>
						</tr>
						<tr>
							<td align="center" colspan="3">
								<input type="submit" value="登录">&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/>
							</td>
						</tr>
					</table>
					</form>
			</div>
		</div>
	</div>	
</body>
</html>