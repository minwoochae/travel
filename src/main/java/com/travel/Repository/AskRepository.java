package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.AskBoard;

public interface AskRepository extends JpaRepository<AskBoard, Long>, AskRepositoryCustom {

}
