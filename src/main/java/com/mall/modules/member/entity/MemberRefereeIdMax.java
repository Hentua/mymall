package com.mall.modules.member.entity;

import javax.validation.constraints.NotNull;

import com.mall.common.persistence.DataEntity;

/**
 * 最大推荐ID编号Entity
 * @author wankang
 * @version 2018-11-25
 */
public class MemberRefereeIdMax extends DataEntity<MemberRefereeIdMax> {
	
	private static final long serialVersionUID = 1L;
	private Integer maxRefereeId;		// max_referee_id
	
	public MemberRefereeIdMax() {
		super();
	}

	public MemberRefereeIdMax(String id){
		super(id);
	}

	@NotNull(message="max_referee_id不能为空")
	public Integer getMaxRefereeId() {
		return maxRefereeId;
	}

	public void setMaxRefereeId(Integer maxRefereeId) {
		this.maxRefereeId = maxRefereeId;
	}
	
}