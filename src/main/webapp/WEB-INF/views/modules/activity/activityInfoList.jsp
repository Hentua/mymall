<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>活动配置管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/activity/activityInfo/">活动配置列表</a></li>
    <shiro:hasPermission name="activity:activityInfo:edit">
        <li><a href="${ctx}/activity/activityInfo/form">活动配置添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="activityInfo" action="${ctx}/activity/activityInfo/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>活动名称：</label>
            <form:input path="activityName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>活动状态：</label>
            <form:select path="status" class="input-medium">
                <form:option value="" label="全部"/>
                <form:option value="0" label="下线"/>
                <form:option value="1" label="上线"/>
                <form:option value="2" label="预上线"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>活动名称</th>
        <th>活动开始时间</th>
        <th>活动结束时间</th>
        <th>活动状态</th>
        <th>折扣比例</th>
        <th>是否可使用优惠券</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>备注</th>
        <shiro:hasPermission name="activity:activityInfo:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="activityInfo">
        <tr>
            <td><a href="${ctx}/activity/activityInfo/form?id=${activityInfo.id}">
                    ${activityInfo.activityName}
            </a></td>
            <td>
                <fmt:formatDate value="${activityInfo.startDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${activityInfo.endDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${activityInfo.status == '0'}">下线</c:when>
                    <c:when test="${activityInfo.status == '1'}">上线</c:when>
                    <c:when test="${activityInfo.status == '2'}">预上线</c:when>
                </c:choose>
            </td>
            <td>
                    ${activityInfo.discountRate}
            </td>
            <td>
                <c:choose>
                    <c:when test="${activityInfo.couponFlag == '0'}">否</c:when>
                    <c:when test="${activityInfo.couponFlag == '1'}">是</c:when>
                </c:choose>
            </td>
            <td>
                <fmt:formatDate value="${activityInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${activityInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${activityInfo.remarks}
            </td>
            <shiro:hasPermission name="activity:activityInfo:edit">
                <td>
                    <a href="${ctx}/activity/activityInfo/form?id=${activityInfo.id}">修改</a>
                    <a href="${ctx}/activity/activityInfo/delete?id=${activityInfo.id}"
                       onclick="return confirmx('确认要删除该活动配置吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>