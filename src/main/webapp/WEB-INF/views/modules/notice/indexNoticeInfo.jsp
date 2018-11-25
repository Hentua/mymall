<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页公告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function () {
            $("#content").append('${indexNotice.content}')
        })
	</script>
</head>
<body>
<div id="content" style="width: 100%;"></div>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/notice/indexNotice/">首页公告列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/notice/indexNotice/form?id=${indexNotice.id}">首页公告<shiro:hasPermission name="notice:indexNotice:edit">${not empty indexNotice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="notice:indexNotice:edit">查看</shiro:lacksPermission></a></li>--%>
	<%--</ul><br/>--%>
	<form:form id="inputForm" modelAttribute="indexNotice" action="${ctx}/notice/indexNotice/save" method="post" class="form-horizontal">


		<form:textarea id="content" htmlEscape="false" disabled="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
		<sys:ckeditor replace="content" uploadPath="/content" />
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>