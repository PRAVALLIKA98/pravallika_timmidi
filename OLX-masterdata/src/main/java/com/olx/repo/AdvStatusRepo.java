package com.olx.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.AdvertiseStatusEntity;

public interface AdvStatusRepo extends JpaRepository<AdvertiseStatusEntity, Integer>{
	
}
