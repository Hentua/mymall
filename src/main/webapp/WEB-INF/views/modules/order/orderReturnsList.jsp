<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>售后申请管理</title>
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
    <li class="active"><a href="${ctx}/order/orderReturns/">售后申请列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="orderReturns" action="${ctx}/order/orderReturns/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>售后单号：</label>
            <form:input path="returnsNo" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>
        <li><label>原订单号：</label>
            <form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>
        <li><label>快递单号：</label>
            <form:input path="expressNo" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li><label>处理方式：</label>
            <form:select path="handlingWay" class="input-medium">
                <form:option value="" label="全部"/>
                <form:option value="0" label="退货退款"/>
                <form:option value="1" label="换新"/>
            </form:select>
        </li>
        <li><label>申请时间：</label>
            <input name="returnSubmitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orderReturns.returnSubmitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>售后状态：</label>
            <form:select path="status" class="input-medium">
                <form:option value="" label="全部"/>
                <form:option value="0" label="待处理"/>
                <form:option value="1" label="审核通过"/>
                <form:option value="2" label="等待退款"/>
                <form:option value="3" label="已完成"/>
                <form:option value="4" label="审核未通过"/>
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
        <th>售后单号</th>
        <th>原订单号</th>
        <th>申请人</th>
        <th>申请人账号</th>
        <th>处理方式</th>
        <th>退款金额</th>
        <th>售后申请时间</th>
        <th>处理时间</th>
        <th>售后状态</th>
        <shiro:hasPermission name="order:orderReturns:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="orderReturns">
        <tr>
            <td>
                    ${orderReturns.returnsNo}
            </td>
            <td><a href="${ctx}/order/orderInfo/form?id=${orderReturns.orderId}">
                    ${orderReturns.orderNo}
            </a></td>
            <td>
                    ${orderReturns.customerName}
            </td>
            <td>
                    ${orderReturns.customerMobile}
            </td>
            <td>
                <c:choose>
                    <c:when test="${orderReturns.handlingWay == '0'}">退货退款</c:when>
                    <c:when test="${orderReturns.handlingWay == '1'}">换新</c:when>
                </c:choose>
            </td>
            <td>
                    ${orderReturns.returnsAmount}
            </td>
            <td>
                <fmt:formatDate value="${orderReturns.returnSubmitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${orderReturns.handlingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${orderReturns.status == '0'}">待审核</c:when>
                    <c:when test="${orderReturns.status == '1'}">待处理</c:when>
                    <c:when test="${orderReturns.status == '3'}">已完成</c:when>
                    <c:when test="${orderReturns.status == '4'}">审核未通过</c:when>
                </c:choose>
            </td>
            <shiro:hasPermission name="order:orderReturns:edit">
                <td>
                    <c:choose>
                        <c:when test="${orderReturns.status == '0'}">
                            <shiro:hasPermission name="order:orderReturns:check">
                                <a href="${ctx}/order/orderReturns/form?id=${orderReturns.id}">审核</a>
                            </shiro:hasPermission>
                        </c:when>
                        <c:when test="${orderReturns.status == '1' && orderReturns.handlingWay == '1'}">
                            <shiro:hasPermission name="order:orderReturns:send">
                                <a href="${ctx}/order/orderReturns/form?id=${orderReturns.id}">发货</a>
                            </shiro:hasPermission>
                        </c:when>
                        <c:when test="${orderReturns.status == '1' && orderReturns.handlingWay == '0'}">
                            <shiro:hasPermission name="order:orderReturns:send">
                                <a href="${ctx}/order/orderReturns/form?id=${orderReturns.id}">退款</a>
                            </shiro:hasPermission>
                        </c:when>
                        <c:otherwise><a href="${ctx}/order/orderReturns/form?id=${orderReturns.id}">详情</a></c:otherwise>
                    </c:choose>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>