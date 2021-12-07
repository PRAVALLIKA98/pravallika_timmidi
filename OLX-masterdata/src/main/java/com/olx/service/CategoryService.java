package com.olx.service;

import java.util.List;

import com.olx.dto.Category;
import com.olx.dto.StatusDTO;

public interface CategoryService {

	public List<Category> getAllCategories();
	public Category getCategoryById(long categoryId);
	
	public String getStatusUsingId(int id);
	
	List<StatusDTO> getAllAdvertisementStatus();
}
