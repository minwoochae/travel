package com.travel.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class})	// auditing 적용
@MappedSuperclass	// 이 엔티티를 extends로 쓸것
@Getter
@Setter
public abstract class BaseTimeEntity {

	@CreatedDate	// 엔티티가 생성되서 저장될 때 시간을 자동저장하도록
	@Column(updatable = false)	// 컬럼의 값을 수정하지 못하도록막음
	private LocalDateTime regTime;	// 등록날짜
	
	@LastModifiedDate // 수정될 때 시간을 자동으로 저장
	private LocalDateTime updateTime;
}
