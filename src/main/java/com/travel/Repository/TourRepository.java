package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Tourist;

public interface TourRepository extends JpaRepository<Tourist, Long>, TourRepositoryCustom{
}
