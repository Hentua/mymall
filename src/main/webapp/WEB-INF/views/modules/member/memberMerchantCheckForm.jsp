<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商户审核管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });

        function showImage(imageUrl) {
            window.open(imageUrl);
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/member/memberMerchantCheck/">商户审核列表</a></li>
    <li class="active"><a href="${ctx}/member/memberMerchantCheck/form?id=${memberMerchantCheck.id}">商户审核</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="memberMerchantCheck"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">商户名称：</label>
        <div class="controls">
                ${memberMerchantCheck.memberInfo.nickname}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机号码：</label>
        <div class="controls">
                ${memberMerchantCheck.memberInfo.mobile}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.memberInfo.userType == '0'}">
                    普通会员
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.userType == '1'}">
                    商户
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.userType == '2'}">
                    运营
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注册途径：</label>
        <div class="controls">
                ${memberMerchantCheck.memberInfo.registerWay eq '0' ? '自主注册' : '后台添加'}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注册时间：</label>
        <div class="controls">
            <fmt:formatDate value="${memberMerchantCheck.memberInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.memberInfo.merchantType == '0'}">
                    推广者
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.merchantType == '1'}">
                    商户
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户状态：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.memberInfo.status == '0'}">
                    未提交
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.status == '1'}">
                    已生效
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.status == '2'}">
                    审核未通过
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.status == '3'}">
                    审核中
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">此次申请审核状态：</label>
        <div class="controls">
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
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录状态：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.memberInfo.loginFlag == '0'}">
                    禁止登录
                </c:when>
                <c:when test="${memberMerchantCheck.memberInfo.loginFlag == '1'}">
                    允许登录
                </c:when>
            </c:choose>
        </div>
    </div>
    <c:if test="${memberMerchantCheck.memberInfo.merchantType == '1'}">
        <div class="control-group">
            <label class="control-label">公司名称：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.companyName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.publicAccount}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户名称：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.publicAccountName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户银行：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.publicAccountBank}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">产品许可证：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.productLicense}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业执照图片：</label>
            <div class="controls">
                <img src="${memberMerchantCheck.memberInfo.businessLicenseImage}" alt="营业执照图片" width="400" onclick="showImage('${memberMerchantCheck.memberInfo.businessLicenseImage}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证正面：</label>
            <div class="controls">
                <img src="${memberMerchantCheck.memberInfo.idcardFront}" alt="法人身份证正面" width="400" onclick="showImage('${memberMerchantCheck.memberInfo.idcardFront}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证反面：</label>
            <div class="controls">
                <img src="${memberMerchantCheck.memberInfo.idcardBack}" alt="法人身份证反面" width="400" onclick="showImage('${memberMerchantCheck.memberInfo.idcardBack}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">特殊资质：</label>
            <div class="controls">
                <img src="${memberMerchantCheck.memberInfo.specialQualification}" alt="特殊资质" width="400" onclick="showImage('${memberMerchantCheck.memberInfo.specialQualification}')"/>
            </div>
        </div>
    </c:if>
    <c:if test="${memberMerchantCheck.memberInfo.merchantType == '0'}">
        <div class="control-group">
            <label class="control-label">个人银行账户：</label>
            <div class="controls">
                ${memberMerchantCheck.memberInfo.personAccount}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户名称：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.personAccountName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户开户行：</label>
            <div class="controls">
                    ${memberMerchantCheck.memberInfo.personAccountBank}
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">归属督导经理：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.status == '0'}">
                    <sys:treeselect id="operatorCode" name="operatorCode" value="${memberMerchantCheck.memberInfo.operatorCode}" labelName="operatorName" labelValue="${memberMerchantCheck.memberInfo.operatorName}"
                                    title="归属督导经理" url="/sys/office/treeData?type=4" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
                </c:when>
                <c:otherwise>
                    ${memberMerchantCheck.memberInfo.operatorName}
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户备注：</label>
        <div class="controls">
            <form:textarea path="memberInfo.remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "
                           readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberMerchantCheck.status == '0'}">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
                </c:when>
                <c:otherwise>
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="form-actions">
        <c:if test="${memberMerchantCheck.status == '0'}">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="审核通过" onclick="this.form.action='${ctx}/member/memberMerchantCheck/checkPass';"/>&nbsp;
            <input id="notPassSubmit" class="btn btn-primary" type="submit" value="审核不通过" onclick="this.form.action='${ctx}/member/memberMerchantCheck/checkReject'"/>&nbsp;
        </c:if>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>