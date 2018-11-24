<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		body{
			font-size: 17px;
		}
		.mr_index{
			width: 100%;
			padding-top: 30px;
		}
		.mr_sts{
			float: left;
			text-align: center;
			width: 23.5%;
			min-height: 65px;
			padding-top: 7px;
			border: 1px solid red;
			margin-left: 1%;
		}
		.mr_sts_title{
			font-size: 17px;
		}
		.mr_sts_amount{
			font-size: 22px;
			font-weight: bold;
			padding-top: 10px;
		}
		.mr_ad{
			border: 1px solid red;
			height: 300px;
			margin-left: 1%;
			width: 65%;
			float: left;
		}
		.mr_notice{
			border: 1px solid red;
			height: 187px;
			margin-left: 1%;
			width: 65%;
			float: left;
			margin-top: -188px;
		}
		.mr_info{
			border: 1px solid red;
			width: 29.3%;
			margin-left: 1%;
			float: left;
			height: 478px;
			padding: 1% 1%;
		}
		.mr_info div{
			text-align: center;
		}
		.mr_info table tr td{
			padding-top: 15px;
		}
		.mr_info table tr td:first-child{
			text-align: right;
			font-size: 15px;
			font-weight: bold;
			width: 120px;
		}

	</style>
</head>
<body>
	<div class="mr_index">
		<div style="min-height: 85px">
			<div class="mr_sts">
				<div class="mr_sts_title">当日货款金额</div>
				<div class="mr_sts_amount">100.00</div>
			</div>
			<div class="mr_sts">
				<div class="mr_sts_title">账户余额</div>
				<div class="mr_sts_amount">100.00</div>
			</div>
			<div class="mr_sts">
				<div class="mr_sts_title">未提现佣金</div>
				<div class="mr_sts_amount">100.00</div>
			</div>
			<div class="mr_sts">
				<div class="mr_sts_title">5折卡券数量</div>
				<div class="mr_sts_amount">100.00</div>
			</div>
		</div>
		<div class="mr_ad">

		</div>

		<div class="mr_info">
			<%--<div style="width: 100%">--%>
				<%----%>
			<%--</div>--%>
			<table >
				<tr>
					<td>头像：</td>
					<td><img style="border: 1px solid #0190d7;" src="${ctxStatic}/images/logo.png" width="50%" ></td>
				</tr>
				<tr>
					<td>账号：</td>
					<td>13652371059</td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td>hub</td>
				</tr>
				<tr>
					<td>类别：</td>
					<td>商家</td>
				</tr>
				<tr>
					<td>店铺名称：</td>
					<td>胡汉三杂货铺</td>
				</tr>
				<tr>
					<td>月销售额：</td>
					<td>1000.00</td>
				</tr>
				<tr>
					<td>累计佣金：</td>
					<td>512300.00</td>
				</tr>
				<tr>
					<td>上次登录IP：</td>
					<td>${user.oldLoginIp}</td>
				</tr>
				<tr>
					<td>上次登录时间：</td>
					<td><fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></td>
				</tr>
			</table>
		</div>
		<div class="mr_notice">

		</div>
	</div>
</body>
</html>