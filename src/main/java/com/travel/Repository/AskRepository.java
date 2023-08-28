package com.travel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.AskBoard;

public interface AskRepository extends JpaRepository<AskBoard, Long>, AskRepositoryCustom {

	Optional<AskBoard> findById(Long askBoardId);
}
