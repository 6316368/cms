<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<TITLE>节点的异步记载策略</TITLE>
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
// // function refreshTree() {
// // 	mt.reAsyncChildNodes(null,"refresh");
// // }

  
</script>
<SCRIPT type="text/javascript">
//var zNodes =${treeDatas};
//alert(${treeDatas});
	var setting = {
			async:{
				enable: true,
				url: "treeAs"
				,autoParam: ["id=pid"]// 异步加载 父节点(node = {id:1, name:"test"}) 的子节点时，将提交参数 id=1
			    //otherParam: ["id", "1", "name", "test"]//进行异步加载时，将提交参数 id=1&name=test
			}
		};
//  		var zNodes =
//  			    [
//  			     //{"id":0,"name":"网站系统栏目","pid":-1},
//  			     //{"id":1,"name":"用户管理模块","pid":0}
// 					{ "id":0,  "name":"网站系统栏目", "pid":-1, open:true},
				
// 					{ "id":11,  "name":"父节点11 - 折叠" ,"pid":0,open:true},
// 					{ "id":111, "name":"叶子节点111", "pid":11},
// 					{ "id":112, "name":"叶子节点112","pid":11},
// 					{ "id":113, "name":"叶子节点113","pid":11 },
// 					{ "id":114, "name":"叶子节点114","p":11}
// //  			 {"id":17,"name":"招生管理1","pid":16}, 
// //  			 {"id":7,"name":"文章管理1","pid":6},
// //  			 {"id":2,"name":"用户管理1","pid":1},
// //  			 {"id":12,"name":"系统管理1","pid":11},
// //  			 {"id":18,"name":"招生管理2","pid":16},
// //  			 {"id":6,"name":"文章管理模块","pid":0},
// //  			 {"id":3,"name":"用户管理2","pid":1},
// //  			 {"id":8,"name":"文章管理2","pid":6},
// //  			 {"id":13,"name":"系统管理2","pid":11},
// //  			 {"id":19,"name":"招生管理3","pid":16},
// //  			 {"id":14,"name":"系统管理3","pid":11},
// //  			 {"id":11,"name":"系统管理模块","pid":0},
// //  			 {"id":9,"name":"文章管理3","pid":6},
// //  			 {"id":4,"name":"用户管理3","pid":1},
// //  			 {"id":10,"name":"文章管理4","pid":6},
// //  			 {"id":15,"name":"系统管理4","pid":11},
// //  			 {"id":16,"name":"招生管理模块","pid":0},
// //  			 {"id":5,"name":"用户管理4","pid":1},
// //  			 {"id":20,"name":"招生管理4","pid":16}
//   ];
 
 			
//  				[
// 					{ id:1,   pId:0, name:"父节点1 - 展开", open:true},
					
// 					{ id:11,  pId:1, name:"父节点11 - 折叠"},
// 					{ id:111, pId:11, name:"叶子节点111"},
// 					{ id:112, pId:11, name:"叶子节点112"},
// 					{ id:113, pId:11, name:"叶子节点113"},
// 					{ id:114, pId:11, name:"叶子节点114"},


//  					{ id:12,  pId:1, name:"父节点12 - 折叠"},
// 					{ id:121, pId:12, name:"叶子节点121"},
// 					{ id:122, pId:12, name:"叶子节点122"},
// 					{ id:123, pId:12, name:"叶子节点123"},
// 					{ id:124, pId:12, name:"叶子节点124"},
					
 					
//  				    { id:13,  pId:1, name:"父节点13 - 没有子节点", isParent:true},
					

// 					{ id:2,   pId:0, name:"父节点2 - 折叠"},
// 					{ id:21,  pId:2, name:"父节点21 - 展开", open:true},
//  					{ id:211, pId:21, name:"叶子节点211"},
//  					{ id:212, pId:21, name:"叶子节点212"},
// 					{ id:213, pId:21, name:"叶子节点213"},
// 					{ id:214, pId:21, name:"叶子节点214"},
                   
 					
//  					{ id:22,  pId:2, name:"父节点22 - 折叠"},
 					
 					
// 					{ id:221, pId:22, name:"叶子节点221"},
// 					{ id:222, pId:22, name:"叶子节点222"},
// 					{ id:223, pId:22, name:"叶子节点223"},
// 					{ id:224, pId:22, name:"叶子节点224"},

					
// 					{ id:23,  pId:2, name:"父节点23 - 折叠"},
// 					{ id:231, pId:23, name:"叶子节点231"},
// 					{ id:232, pId:23, name:"叶子节点232"},
// 					{ id:233, pId:23, name:"叶子节点233"},
// 					{ id:234, pId:23, name:"叶子节点234"},
// 					{ id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
// 				];

		$(document).ready(function(){
			$.fn.zTree.init($("#tree"), setting);
		});
	</SCRIPT>
</head>
<body>
<div id="content">
	<h3 class="admin_link_bar">
		<span>正在使用栏目管理功能</span>
	</h3>
	<TABLE border="0"  align=left height="600px">
		<TR>
			<TD width=200px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
				<ul id="tree" class="ztree" style="width:200px;height:600px; overflow:auto;"></ul>
			</TD>
			<TD width=650px align=left valign=top><IFRAME ID="cc" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=600px ></IFRAME></TD>
		</TR>
	</TABLE>
</div>
</body>
</html>