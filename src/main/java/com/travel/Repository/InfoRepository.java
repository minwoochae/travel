package com.travel.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.InfoBoard;

public interface InfoRepository extends JpaRepository<InfoBoard, Long> {

	//List<InfoBoard> findByTitle(String infoTitle);
	
}
