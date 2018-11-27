<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/jquery/jquery.bxslider.js"></script>
	<link href="${ctxStatic}/jquery/jquery.bxslider.css" rel="stylesheet" type="text/css">

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
			border: 1px solid #008fd7;
			margin-left: 1%;
			color: white;
			cursor: pointer;
		}
		.mr_sts_title{
			font-size: 17px;
			padding-top: 5px;
		}
		.mr_sts_amount{
			font-size: 22px;
			font-weight: bold;
			padding-top: 10px;
			color: white;
		}
		.mr_ad{
			/*border: 1px solid #008fd7;*/
			height: 300px;
			margin-left: 1%;
			width: 65%;
			float: left;
			padding-top: 10px;
		}
		.mr_notice{
			border: 1px solid #008fd7;
			height: 187px;
			margin-left: 1%;
			width: 65%;
			float: left;
			margin-top: -188px;
		}
		.mr_info{
			border: 1px solid #008fd7;
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
		.mr_ad img{
			max-height: 255px;
		}
		.mr_notice table{
			margin-left: 10px;
			width: 98%;
		}
		.mr_notice table tr td{
			font-size: 14px;
			padding-top: 12px;
			border-bottom: 1px solid #008fd7;
			cursor:pointer;
			color: black;
		}
		.mr_notice table tr td:hover{
			font-weight: bold;
		}
        .mr_notice a{
            color: black;
        }
		.mr_index .y{
			font-size: 12px;
		}
		.mr_sts_icon{
			margin-left: 10px;
			width: 50px;
			float: left;
		}

	</style>
	<script type="text/javascript">
		$(function () {

        })
		function actionGo(url) {
			location.href = url;
        }
	</script>
</head>
<body>
	<div class="mr_index">
		<div style="min-height: 85px">
			<div class="mr_sts" style="background-color: #1190D9" onclick="actionGo('${ctx}/order/orderInfo/merchantList')">
				<div class="mr_sts_icon"><img src="${ctxStatic}/images/icon-4.png" width="50px"></div>
				<div class="mr_sts_title">当日货款金额</div>
				<div class="mr_sts_amount">${stsObj.dayOrderAmountTotal}&nbsp;<span class="y">元</span></div>
			</div>
			<div class="mr_sts" style="background-color: #68B9F1" onclick="actionGo('${ctx}/account/accountFlow/merchantList')">
				<div class="mr_sts_icon"><img src="${ctxStatic}/images/icon-1.png" width="50px"></div>
				<div class="mr_sts_title">账户余额</div>
				<div class="mr_sts_amount">${member.balance}&nbsp;<span class="y">元</span></div>
			</div>
			<div class="mr_sts" style="background-color: #1190D9" onclick="actionGo('${ctx}/commission/commissionInfo/merchantList')">
				<div class="mr_sts_icon" style="    padding-top: 10px;"><img src="${ctxStatic}/images/icon-2.png" width="50px"></div>
				<div class="mr_sts_title">未提现佣金</div>
				<div class="mr_sts_amount">${member.commission}&nbsp;<span class="y">元</span></div>
			</div>
			<div class="mr_sts" style="background-color: #68B9F1">
				<div class="mr_sts_icon" style="    padding-top: 11px;"><img src="${ctxStatic}/images/icon-3.png" width="50px"></div>
				<div class="mr_sts_title">5折卡券金额</div>
				<div class="mr_sts_amount">${stsObj.fiveDiscountCoupon}&nbsp;<span class="y">元</span></div>
			</div>
		</div>
		<div class="mr_ad">
			<div class="slider6">
				<c:forEach items="${billboards}" var="b">
					<div class="slide"><img src="${b.fullImageUrl}" width="100%"></div>
				</c:forEach>
				<%--<div class="slide"><img src="${ctxStatic}/images/a2.png" width="100%"></div>--%>
				<%--<div class="slide"><img src="${ctxStatic}/images/a3.png" width="100%"></div>--%>
				<%--<div class="slide"><img src="${ctxStatic}/images/a4.png" width="100%"></div>--%>
			</div>
			<script type="text/javascript">
                $(document).ready(function(){
                    $('.slider6').bxSlider({
                        mode: 'fade',
                        // slideWidth: 100,
                        slideMargin: 10,
                        responsive: true,
                        speed: 2000
                    });
                });
			</script>
		</div>

		<div class="mr_info">
			<%--<div style="width: 100%">--%>
				<%----%>
			<%--</div>--%>
			<table >
				<tr>
					<td>头像：</td>
					<td><img style="border: 1px solid #0190d7;" src="${member.fullAvatarUrl}" width="50%" ></td>
				</tr>
				<tr>
					<td>账号：</td>
					<td>${user.mobile}</td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td>${user.name}</td>
				</tr>
				<tr>
					<td>类别：</td>
					<td>商家</td>
				</tr>
				<tr>
					<td>店铺名称：</td>
					<td>${member.nickname}</td>
				</tr>
				<tr>
					<td>月销售额：</td>
					<td>${stsObj.monthOrderAmountTotal}</td>
				</tr>
				<tr>
					<td>累计佣金：</td>
					<td>${stsObj.commissionTotal}</td>
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
		<!-- 公告 -->
		<div class="mr_notice">
			<div style="margin-left: 10px;font-weight: bold;margin-top: 20px">公告：</div>
			<table>
                <c:forEach items="${indexNotices}" var="i">
                    <tr>
                        <td  nowrap="nowrap"><a href="${ctx}/notice/indexNotice/info?id=${i.id}">${i.title}</a></td>
                    </tr>
                </c:forEach>

				<%--<tr>--%>
					<%--<td nowrap="nowrap">请在竞价前，仔细阅读公告，了解拍品请在竞价前，仔细阅读公告，了解拍品</td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td nowrap="nowrap">请在竞价前，仔细阅读公告，了解拍品请在您可以在海关拍卖频道的公告页和拍品详情页查看相应拍品的公告</td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td nowrap="nowrap">请在竞价前，仔细阅读公告，了解拍品请在您可以在海关拍卖频道的公告页和拍品详情页查看相应拍品的公告</td>--%>
				<%--</tr>--%>
			</table>
		</div>
	</div>
</body>
</html>