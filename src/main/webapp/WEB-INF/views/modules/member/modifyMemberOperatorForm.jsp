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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/member/memberInfo/merchantInfoListByPower">商户列表</a></li>
    <li class="active"><a href="${ctx}/member/memberInfo/allListForm?id=${id}">商户详情</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/modifyMemberOperator"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">商户名称：</label>
        <div class="controls">
                ${memberInfo.nickname}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户账号：</label>
        <div class="controls">
                ${memberInfo.mobile}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.userType == '0'}">
                    普通会员
                </c:when>
                <c:when test="${memberInfo.userType == '1'}">
                    商户
                </c:when>
                <c:when test="${memberInfo.userType == '2'}">
                    运营
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注册途径：</label>
        <div class="controls">
                ${memberInfo.registerWay eq '0' ? '自主注册' : '后台添加'}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注册时间：</label>
        <div class="controls">
            <fmt:formatDate value="${memberInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.merchantType == '0'}">
                    推广者
                </c:when>
                <c:when test="${memberInfo.merchantType == '1'}">
                    商户
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户状态：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.status == '0'}">
                    未提交
                </c:when>
                <c:when test="${memberInfo.status == '1'}">
                    已生效
                </c:when>
                <c:when test="${memberInfo.status == '2'}">
                    审核未通过
                </c:when>
                <c:when test="${memberInfo.status == '3'}">
                    审核中
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录状态：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.loginFlag == '0'}">
                    禁止登录
                </c:when>
                <c:when test="${memberInfo.loginFlag == '1'}">
                    允许登录
                </c:when>
            </c:choose>
        </div>
    </div>
    <c:if test="${memberInfo.merchantType == '1'}">
        <div class="control-group">
            <label class="control-label">公司名称：</label>
            <div class="controls">
                    ${memberInfo.companyName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业执照注册号：</label>
            <div class="controls">
                    ${memberInfo.productLicense}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户：</label>
            <div class="controls">
                    ${memberInfo.publicAccount}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户名称：</label>
            <div class="controls">
                    ${memberInfo.publicAccountName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户银行：</label>
            <div class="controls">
                    ${memberInfo.publicAccountBank}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">开户许可证：</label>
            <div class="controls">
                <img src="${memberInfo.permit}" alt="开户许可证" width="400" onclick="showImage('${memberInfo.permit}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业执照图片：</label>
            <div class="controls">
                <img src="${memberInfo.businessLicenseImage}" alt="营业执照图片" width="400" onclick="showImage('${memberInfo.businessLicenseImage}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证正面：</label>
            <div class="controls">
                <img src="${memberInfo.idcardFront}" alt="法人身份证正面" width="400" onclick="showImage('${memberInfo.idcardFront}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证反面：</label>
            <div class="controls">
                <img src="${memberInfo.idcardBack}" alt="法人身份证反面" width="400" onclick="showImage('${memberInfo.idcardBack}')"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">特殊资质：</label>
            <div class="controls">
                <img src="${memberInfo.specialQualification}" alt="特殊资质" width="400" onclick="showImage('${memberInfo.specialQualification}')"/>
            </div>
        </div>
    </c:if>
    <c:if test="${memberInfo.merchantType == '0'}">
        <div class="control-group">
            <label class="control-label">个人银行账户：</label>
            <div class="controls">
                    ${memberInfo.personAccount}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户名称：</label>
            <div class="controls">
                    ${memberInfo.personAccountName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户开户行：</label>
            <div class="controls">
                    ${memberInfo.personAccountBank}
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">归属督导经理：</label>
        <div class="controls">
            <sys:treeselect id="operatorCode" name="operatorCode" value="${memberInfo.operatorCode}" labelName="operatorName" labelValue="${memberInfo.operatorName}"
                            title="归属督导经理" url="/sys/office/treeData?type=4" cssClass="input-small required" allowClear="true" notAllowSelectParent="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="history.go(-1)"/>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>