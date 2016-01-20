<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/base/jquery-ui.css"/>
<link rel="stylesheet" href=""/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/inc.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.spinner.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrService.js"></script>
<script type="text/javascript">
$(function(){
 $(".setPos").click(setpos)	;
 function setpos(event){
	 event.preventDefault();
	 var  id=$(this).attr("picid");
	 var  pos=$(this).attr("pos");
     $(this).after("<span><input type='text'  value='"+pos+"'  size='3'><input  type='hidden' id='pos"+id+"'  value='"+pos+"'/><a href='#' class='list_opg confirmPos'>确定</a>&nbsp;<a href='' class='list_opg canclePos'>取消</a></span>");
	 $(this).next("span").children("input:text").spinner({
		 min:$("#minPos").val(),
	     max:$("#maxPos").val(),
	     spin:function(event,ui){
	    	 $("#pos"+id).val(ui.value);
	     }
	 });
	 $(this).off("click");
 };
 $(".posCon").on("click",".canclePos",function(event){
	 event.preventDefault();
	 $(this).parent("span").prev("a").on("click",setpos);
	 $(this).parent("span").remove();
 });
 $(".posCon").on("click",".confirmPos",function(event){
	 event.preventDefault();
	 var  id=$(this).parent("span").prev("a").attr("picid");
	 var  oldpos=$(this).parent("span").prev("a").attr("pos");
	 //var newpos=$(this).prev("span").children("input").val();
	 var newpos=$(this).prev("input").val();
	// alert(id+","+pos+","+newpos);
	if(oldpos!=newpos){
		//通过dwr进行更新位置
		dwrService.updatePicPos(id,oldpos,newpos,function(){
			window.location.reload();
		});
	}
	$(this).parent("span").prev("a").on("click",setpos);
	$(this).parent("span").remove();
 });
 
});
</script>

</head>
<body>
<div id="content">
<input  type="hidden"  readonly="readonly" id="maxPos" value="${max }">
<input  type="hidden"   readonly="readonly" id="minPos" value="${min }">
	<h3 class="admin_link_bar">
		<jsp:include page="inc.jsp"></jsp:include>
	</h3>
	<table width="800" cellspacing="0" cellPadding="0" id="listTable">
		<thead>
		<tr>
			<td>缩略图</td>
			<td width="100">图片标题</td>
			<td width="100">状态</td>
			<td>链接类型</td>
			<td>位置</td>
			<td>用户操作</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${datas.datas }" var="pic">
			<tr>
				<td><img alt="" src='<%=request.getContextPath() %>/resources/indexPic/thumbnail/${pic.newName}'>&nbsp;</td>
				<td><a href="javascript:openWin('<%=request.getContextPath() %>/admin/pic/shwoindexPic/${pic.id }','addIndexPic')" class="list_link">${pic.title}</a></td>
				<td>
                 <c:if test="${pic.status eq 0 }">未启用<a href="updateStatus/${pic.id }">启用</a></c:if>
                 <c:if test="${pic.status eq 1 }">启用<a href="updateStatus/${pic.id }">停用</a></c:if>
                </td>
                <td>
                 <c:if test="${pic.linkType eq 0 }">站内链接</c:if>
                 <c:if test="${pic.linkType eq 1 }"><a href='${pic.linkUrl }' class="list_link">站外连接</a></c:if>
                </td>
                <td  class="posCon">
                 ${pic.pos}<a  href="#" class="list_opg setPos" pos="${pic.pos}"  picid="${pic.id}">排序</a>
                </td>
				<td>
					<a href="deleteIndexpic/${pic.id }" class="list_op delete">删除</a>
					<a href="javascript:openWin('<%=request.getContextPath() %>/admin/pic/updateIndexpic/${pic.id }','addIndexPic')" class="list_op">更新</a>
				&nbsp;
				</td>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot>
		<tr>
			<td colspan="6" style="text-align:right;margin-right:10px;">
			<jsp:include page="/jsp/pager.jsp">
				<jsp:param value="${datas.total }" name="totalRecord"/>
				<jsp:param value="indexPics" name="url"/>
			</jsp:include>
			</td>
		</tr>
		</tfoot>
	</table>
</div>
</body>
</html>