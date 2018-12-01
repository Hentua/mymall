<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>订单结算管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function exportData() {
            window.open('${ctx}/order/orderSettlement/exportOrderSettlement?' + $('#searchForm').serialize());
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/order/orderSettlement/frozenList">货款结算列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="orderSettlement" action="${ctx}/order/orderSettlement/frozenList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <form:hidden path="status" value="1"/>
    <ul class="ul-form">
        <li><label>商家账号：</label>
            <form:input path="merchantAccount" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>订单时间：</label>
            <input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orderSettlement.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orderSettlement.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>订单编号：</label>
            <form:input path="orderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>下单人账号：</label>
            <form:input path="customerAccount" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>商家名称</th>
        <th>商家账号</th>
        <th>订单时间 </th>
        <th>订单编号</th>
        <th>下单人</th>
        <th>下单人账号</th>
        <th>订单金额</th>
        <th>结算金额</th>
        <th>状态</th>
        <shiro:hasPermission name="order:orderSettlement:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="orderSettlement">
        <tr>
            <td>
                    ${orderSettlement.merchantName}
            </td>
            <td>
                    ${orderSettlement.merchantAccount}
            </td>
            <td>
                <fmt:formatDate value="${orderSettlement.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <a href="${ctx}/order/orderInfo/form?id=${orderSettlement.orderId}">${orderSettlement.orderNo}</a>
            </td>
            <td>
                    ${orderSettlement.customerName}
            </td>
            <td>
                    ${orderSettlement.customerAccount}
            </td>
            <td>
                    ${orderSettlement.orderAmount}
            </td>
            <td>
                    ${orderSettlement.settlementAmount}
            </td>
            <td>
                <c:if test="${orderSettlement.status == '1'}">
                    未清算
                </c:if>
                <c:if test="${orderSettlement.status == '2'}">
                    已清算
                </c:if>
                <c:if test="${orderSettlement.status == '3'}">
                    已结算
                </c:if>
            </td>
            <shiro:hasPermission name="order:orderSettlement:edit"><td>
                <c:if test="${orderSettlement.orderStatus == '3' || orderSettlement.orderStatus == '6'}">
                    <a href="${ctx}/order/orderSettlement/updateStatus?id=${orderSettlement.id}" onclick="return confirmx('确认要结算该笔信息吗？', this.href)">删除</a>
                </c:if>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="6"  style="text-align: center;font-weight: bolder;">合计</td>
        <td>${total.orderAmountTotal}</td>
        <td>${total.settlementAmountTotal}</td>
        <td></td>
        <td></td>
    </tr>
    </tfoot>
</table>
<div class="pagination">${page}</div>
</body>
</html>