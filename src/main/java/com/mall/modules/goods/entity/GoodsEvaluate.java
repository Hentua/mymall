package com.mall.modules.goods.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.mall.common.persistence.DataEntity;

/**
 * 商品评价Entity
 * @author hub
 * @version 2018-11-06
 */
public class GoodsEvaluate extends DataEntity<GoodsEvaluate> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品ID
	private String evaluate;		// 评价
	private String level;		// 级别
	private String title;		// 标题
	private String goodsName;		// 商品名称
	private String evaluateUserId;		// 评价用户
	private Date evaluateDate;		// 评价时间
	private String evaluateUserName;		// 评价用户名称
	
	public GoodsEvaluate() {
		super();
	}

	public GoodsEvaluate(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=500, message="评价长度必须介于 0 和 500 之间")
	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	
	@Length(min=0, max=10, message="级别长度必须介于 0 和 10 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=100, message="商品名称长度必须介于 0 和 100 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="评价用户长度必须介于 0 和 64 之间")
	public String getEvaluateUserId() {
		return evaluateUserId;
	}

	public void setEvaluateUserId(String evaluateUserId) {
		this.evaluateUserId = evaluateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
	
	@Length(min=0, max=100, message="评价用户名称长度必须介于 0 和 100 之间")
	public String getEvaluateUserName() {
		return evaluateUserName;
	}

	public void setEvaluateUserName(String evaluateUserName) {
		this.evaluateUserName = evaluateUserName;
	}
	
}