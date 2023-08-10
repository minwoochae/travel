package com.travel.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

	@CreatedBy
	@Column(updatable = false)
	private String createBy;	// 등록자
	
	@LastModifiedBy
	private String modifiedBy;	// 수정자
	
}
