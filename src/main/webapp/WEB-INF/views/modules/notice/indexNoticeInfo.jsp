<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>
	<title>首页公告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            console.log('------------------')
			<%--var x = "${indexNotice.content}";--%>
            <%--console.log(x)--%>
			$("#content_div").append($("#content_div_val").text())
        });
	</script>
</head>
<body>
<%--${indexNotice.content}--%>
	<form:form id="inputForm" modelAttribute="indexNotice" action="${ctx}/notice/indexNotice/save" method="post" class="form-horizontal">
		<%--<form:textarea id="content" htmlEscape="false" disabled="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>--%>
		<%--<sys:ckeditor replace="content" uploadPath="/content" />--%>
		<div id="content_div"></div>
		<%--<form:textarea path="content" htmlEscape="true"></form:textarea>--%>
		<%--<form:textarea id="content" htmlEscape="false" disabled="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>--%>
		<%--<sys:ckeditor replace="content" uploadPath="/content" />--%>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div style="display: none" id="content_div_val">${indexNotice.content}</div>
	</form:form>
</body>
</html>