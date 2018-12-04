package com.mall.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.mall.common.persistence.DataEntity;

/**
 * app升级配置Entity
 * @author hub
 * @version 2018-12-04
 */
public class SysUpgrade extends DataEntity<SysUpgrade> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 升级标题
	private String version;		// 版本号
	private String url;		// 下载地址
	private String content;		// 内容
	private String os;		// 1：Android 2：ios
	private String isForce;		// 1：强制 2：不强制
	
	public SysUpgrade() {
		super();
	}

	public SysUpgrade(String id){
		super(id);
	}

	@Length(min=0, max=200, message="升级标题长度必须介于 0 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="版本号长度必须介于 0 和 255 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=500, message="下载地址长度必须介于 0 和 500 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=500, message="内容长度必须介于 0 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=10, message="1：Android 2：ios长度必须介于 0 和 10 之间")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	@Length(min=0, max=10, message="1：强制 2：不强制长度必须介于 0 和 10 之间")
	public String getIsForce() {
		return isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	
}