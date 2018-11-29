<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商户信息</title>
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
        function showMerchant() {
            $('#merchantInfo').show();
            $('#promotersInfo').hide();
        }
        function showPromoters() {
            $('#merchantInfo').hide();
            $('#promotersInfo').show();
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/user/info">个人信息</a></li>
    <li class="active"><a href="${ctx}/member/memberInfo/merchantData?id=${memberInfo.id}">商户信息</a></li>
    <li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="memberInfo" action="" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="status"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">用户类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                    <c:if test="${memberInfo.merchantType == '0'}">推广者</c:if>
                    <c:if test="${memberInfo.merchantType == '1'}">商户</c:if>
                </c:when>
                <c:otherwise>
                    <form:radiobutton path="merchantType" value="0" label="推广者" checked="true" onclick="showPromoters()"/>
                    <form:radiobutton path="merchantType" value="1" label="商户" onclick="showMerchant()"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div id="merchantInfo" style="${memberInfo.merchantType == '0' ? 'display: none;' : ''}">
        <div class="control-group">
            <label class="control-label">公司名称：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.companyName}
                    </c:when>
                    <c:otherwise>
                        <form:input path="companyName" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.publicAccount}
                    </c:when>
                    <c:otherwise>
                        <form:input path="publicAccount" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户名称：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.publicAccountName}
                    </c:when>
                    <c:otherwise>
                        <form:input path="publicAccountName" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">对公账户银行：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.publicAccountBank}
                    </c:when>
                    <c:otherwise>
                        <form:input path="publicAccountBank" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">产品许可证：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.productLicense}
                    </c:when>
                    <c:otherwise>
                        <form:input path="productLicense" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业执照图片：</label>
            <div class="controls">
                <form:hidden id="businessLicenseImage" path="businessLicenseImage" htmlEscape="false" maxlength="255"
                             class="input-xlarge "/>
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        <sys:ckfinder input="businessLicenseImage" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                        <sys:ckfinder input="businessLicenseImage" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证正面：</label>
            <div class="controls">
                <form:hidden id="idcardFront" path="idcardFront" htmlEscape="false" maxlength="255"
                             class="input-xlarge "/>
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        <sys:ckfinder input="idcardFront" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                        <sys:ckfinder input="idcardFront" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">法人身份证反面：</label>
            <div class="controls">
                <form:hidden id="idcardBack" path="idcardBack" htmlEscape="false" maxlength="255"
                             class="input-xlarge "/>
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        <sys:ckfinder input="idcardBack" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                        <sys:ckfinder input="idcardBack" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">特殊资质：</label>
            <div class="controls">
                <form:hidden id="specialQualification" path="specialQualification" htmlEscape="false" maxlength="255"
                             class="input-xlarge "/>
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        <sys:ckfinder input="specialQualification" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                        <sys:ckfinder input="specialQualification" type="images" uploadPath="/merchant"
                                      selectMultiple="false" maxWidth="100" maxHeight="100"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div id="promotersInfo"  style="${memberInfo.merchantType == '1' ? 'display: none;' : ''}">
        <div class="control-group">
            <label class="control-label">个人银行账户：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.personAccount}
                    </c:when>
                    <c:otherwise>
                        <form:input path="personAccount" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户名称：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.personAccountName}
                    </c:when>
                    <c:otherwise>
                        <form:input path="personAccountName" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">个人银行账户开户行：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                        ${memberInfo.personAccountBank}
                    </c:when>
                    <c:otherwise>
                        <form:input path="personAccountBank" htmlEscape="false" class="input-xlarge "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <c:if test="${memberInfo.status == '1' || memberInfo.status == '3'}">
        <div class="control-group">
            <label class="control-label">审核状态：</label>
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
    </c:if>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${memberInfo.status == '1' || memberInfo.status == '3'}">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "
                                   readonly="true"/>
                </c:when>
                <c:otherwise>
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="form-actions">
        <c:if test="${memberInfo.status == '1'}">
            <a id="btnSubmit" class="btn btn-primary" onclick="return confirmx('确定要修改商户类型？如果您是商户，则将下架您的所有商品', this.href)" href="${ctx}/member/memberInfo/checkPayPasswordForm?id=${memberInfo.id}&failedCallbackUrl=${ctx}/member/memberInfo/merchantData?id=${memberInfo.id}&successCallbackUrl=${ctx}/member/memberInfo/modifyMerchantType?id=${memberInfo.id}">修改商户类型</a>
        </c:if>
        <c:if test="${memberInfo.status == '0' || memberInfo.status == '2'}">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="提交审核" onclick="this.form.action='${ctx}/member/memberInfo/submitMerchantData'"/>
        </c:if>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>

</body>
</html>