package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.StatusDTO;
import com.olx.entity.AdvertiseStatusEntity;
import com.olx.entity.CategoryEntity;
import com.olx.repo.AdvStatusRepo;
import com.olx.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private AdvStatusRepo advStatusRepo;
	
	@Override
	public List<Category> getAllCategories() {
		List<CategoryEntity> categoryEntities = categoryRepo.findAll();
		List<Category> categories = new ArrayList<>();
		for(CategoryEntity categoryEntity: categoryEntities) {
			Category category = new Category();
			category.setId(categoryEntity.getId());
			category.setCategory(categoryEntity.getName());
			category.setDescription(categoryEntity.getDescription());
			
			categories.add(category);
		}
		return categories;
	}
	
	public List<StatusDTO> getAllAdvertisementStatus() {
		List<AdvertiseStatusEntity>	advStatusEntityList = advStatusRepo.findAll();
		
		List<StatusDTO> statusList = new ArrayList<>();
		for(AdvertiseStatusEntity statusEntity: advStatusEntityList) {
			StatusDTO status = new StatusDTO();
			
			status.setId(statusEntity.getId());
			status.setStatus(statusEntity.getStatus());
			
			statusList.add(status);
		}
		return statusList;
	}

	@Override
	public Category getCategoryById(long categoryId) {
		Optional<CategoryEntity> opCategoryEntity = categoryRepo.findById(categoryId);
		if(opCategoryEntity.isPresent()) {
			CategoryEntity categoryEntity = opCategoryEntity.get();
			return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription());
		}
		return null;
	}
	
	public String getStatusUsingId(int id) {
		Optional<AdvertiseStatusEntity> opStatusEntity = advStatusRepo.findById(id);
		if(opStatusEntity.isPresent()) {
			AdvertiseStatusEntity advEntity = opStatusEntity.get();
			return advEntity.getStatus();
		}
		return null;
	}

}
