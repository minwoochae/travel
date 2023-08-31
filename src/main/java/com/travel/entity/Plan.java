package com.travel.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name="plan") 
@Getter
@Setter
@ToString
public class Plan {
	
	@Id
	@Column(name="plan_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "plan_title")
	private String planTitle;
	
	@Column(name = "plan_date")
	private String planDate;
	
	@ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

	
	@Column(name = "reg_date")
	private String regDate;
	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, 
			orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PlanContent> planContent = new ArrayList<>();
	

	
	
	public void addPlanContent(PlanContent planContent) {
		this.planContent.add(planContent);
		planContent.setPlan(this);
	}
	
	public static Plan createPlan(Member member, List<PlanContent> planContentList) {
		Plan plan = new Plan();
		plan.setMember(member);
		
		for(PlanContent planContent : planContentList) {
			plan.addPlanContent(planContent);
		}
		
		
		return plan;
	}
	
}
