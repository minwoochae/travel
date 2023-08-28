package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.AskResponseBoard;

public interface AskResponseRepository extends JpaRepository<AskResponseBoard, Long>, AskResponseCustom{

}
