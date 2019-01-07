<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商户审核管理</title>
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
    <li class="active"><a href="${ctx}/member/memberMerchantCheck/">商户审核列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="memberMerchantCheck" action="${ctx}/member/memberMerchantCheck/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>审核状态：</label>
            <form:select path="status" class="input-medium">
                <form:option value="" label="全部"/>
                <form:option value="0" label="未审核"/>
                <form:option value="1" label="审核通过"/>
                <form:option value="2" label="审核未通过"/>
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
        <th>商户名称</th>
        <th>商户类型</th>
        <th>手机号码</th>
        <th>注册途径</th>
        <th>申请时间</th>
        <th>商户推荐人</th>
        <th>商户推荐人账号</th>
        <th>审核状态</th>
        <th>审核人</th>
        <th>审核时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="memberMerchantCheck">
        <tr>
            <td><a href="${ctx}/member/memberMerchantCheck/form?id=${memberMerchantCheck.id}">
                    ${memberMerchantCheck.merchantName}
            </a></td>
            <td>
                <c:choose>
                    <c:when test="${memberMerchantCheck.merchantType == '0'}">
                        推广者
                    </c:when>
                    <c:when test="${memberMerchantCheck.merchantType == '1'}">
                        商户
                    </c:when>
                </c:choose>
            </td>
            <td>
                    ${memberMerchantCheck.mobile}
            </td>
            <td>
                    ${memberMerchantCheck.registerWay eq '0' ? '自主注册' : '后台添加'}
            </td>
            <td>
                <fmt:formatDate value="${memberMerchantCheck.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${memberMerchantCheck.merchantRefereeName}
            </td>
            <td>
                    ${memberMerchantCheck.merchantRefereeAccount}
            </td>
            <td>
                <c:choose>
                    <c:when test="${memberMerchantCheck.status == '0'}">
                        审核中
                    </c:when>
                    <c:when test="${memberMerchantCheck.status == '1'}">
                        审核通过
                    </c:when>
                    <c:when test="${memberMerchantCheck.status == '2'}">
                        审核未通过
                    </c:when>
                </c:choose>
            </td>
            <td>
                    ${memberMerchantCheck.checkByName}
            </td>
            <td>
                <fmt:formatDate value="${memberMerchantCheck.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="member:memberMerchantCheck:edit">
                <td>
                    <a href="${ctx}/member/memberMerchantCheck/form?id=${memberMerchantCheck.id}">${memberMerchantCheck.status == '0' ? '审核' : '详情'}</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>