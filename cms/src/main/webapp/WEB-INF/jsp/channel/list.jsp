<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>节点的一次加载功能</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/zTree/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/tree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript">
var mt;
	$(function(){
		mt = $("#tree").mytree();
	});
 function refreshTree() {
 	mt.reAsyncChildNodes(null,"refresh");
 }
</script>
</head>
<body>
<div id="content">
	<h3 class="admin_link_bar">
		<span>正在使用栏目管理功能</span>
	</h3>
	<table align=left border="0" width="96%" height="480px">
	    <tr>
	       <TD width=160px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
	         <ul id="tree" class="ztree" style="width:150px;height:450px; overflow:auto;"></ul>
	       </td>
	       <TD width=650px align=left valign=top>
	         <IFRAME ID="cc" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=99% height="450px">
	       </IFRAME>
	       
	       </TD>
	    </tr>
	</table>
</div>
</body>
</html>