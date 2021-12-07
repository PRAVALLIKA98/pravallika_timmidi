package com.olx.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olx.entity.AdvertiseEntity;

public interface AdvertiseRepo extends JpaRepository<AdvertiseEntity, Long>{
	public List<AdvertiseEntity> findByUsername(String username);
	
	//public Optional<AdvertiseEntity> findById(Long id);
	
	@Query("SELECT ae from AdvertiseEntity ae WHERE ae.userName=:userName and ae.id=:id")
	public AdvertiseEntity findByUserNameAndPostId(Long id,String userName);
	
	@Query("SELECT ae from AdvertiseEntity ae WHERE ae.id LIKE '%:searchKey%' or ae.title LIKE '%:searchKey%' or ae.description LIKE '%:searchKey%' or ae.price LIKE '%:searchKey%' or ae.created_date LIKE '%:searchKey%' or ae.modified_date LIKE '%:searchKey%' or ae.userName LIKE '%:searchKey%' or ae.category_id LIKE '%:searchKey%' or ae.status_id LIKE '%:searchKey%'")
	public List<AdvertiseEntity> findAdvertiseBySearchKey(String searchKey);
	
	@Query("SELECT ae from AdvertiseEntity ae WHERE ae.id LIKE '%:searchKey%' or ae.title LIKE '%:searchKey%' or ae.description LIKE '%:searchKey%' or ae.price<=:searchKey or ae.price>=:searchKey or ae.created_date<='searchKey' or ae.created_date>='searchKey'or ae.modified_date LIKE '%:searchKey%' or ae.userName LIKE '%:searchKey%' or ae.category_id LIKE '%:searchKey%' or ae.status_id LIKE '%:searchKey%'")
	public List<AdvertiseEntity> findAdvertiseBySearchFiler(String searchKey);
}
