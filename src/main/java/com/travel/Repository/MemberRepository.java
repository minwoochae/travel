package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByEmail(String email);
	
	
	Member findByNameAndPhoneNumber(String name, String  phoneNumber);
	
	Member findByNameAndPhoneNumberAndEmail(String name, String  phoneNumber, String email);
	Member findByNameAndPhoneNumberAndEmailAndPassword(String name, String  phoneNumber, String email , String password);
	
	Member findByRole(String role);
	
	Page<Member> findAll(Pageable pageable);
	

	
	

}
