package com.mall.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * 短信发送记录Entity
 * @author wankang
 * @version 2018-10-11
 */
public class SysSmsLog extends DataEntity<SysSmsLog> {
	
	private static final long serialVersionUID = 1L;
	private String phone;		// 接收手机号
	private String signName;		// 短信签名
	private String templateCode;		// 短信模板ID
	private String templateParam;		// 模板参数
	private String requestId;		// 请求阿里云短信ID
	private String code;		// 短信发送返回码
	private String message;		// 返回状态码描述
	private String bizId;		// 发送回执ID,可根据该ID查询具体的发送状态
	
	public SysSmsLog() {
		super();
	}

	public SysSmsLog(String id){
		super(id);
	}

	@Length(min=0, max=20, message="接收手机号长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=20, message="短信签名长度必须介于 0 和 20 之间")
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
	
	@Length(min=0, max=20, message="短信模板ID长度必须介于 0 和 20 之间")
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	@Length(min=0, max=200, message="模板参数长度必须介于 0 和 200 之间")
	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}
	
	@Length(min=0, max=100, message="请求阿里云短信ID长度必须介于 0 和 100 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Length(min=0, max=20, message="短信发送返回码长度必须介于 0 和 20 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="返回状态码描述长度必须介于 0 和 100 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Length(min=0, max=10, message="发送回执ID,可根据该ID查询具体的发送状态长度必须介于 0 和 10 之间")
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
}