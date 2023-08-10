package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByNameAndPhoneNumber(String name, String  phoneNumber);
}
