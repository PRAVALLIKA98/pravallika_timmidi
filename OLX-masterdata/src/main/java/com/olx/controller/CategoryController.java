package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Category;
import com.olx.dto.StatusDTO;
import com.olx.service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value="/advertise/category", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	@GetMapping(value="/advertise/status", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StatusDTO> getAllAdvertisementStatus() {
		return categoryService.getAllAdvertisementStatus();
	}

	@GetMapping(value="/category/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Category getCategoryById(@PathVariable("id") String strCategoryId) {
		return categoryService.getCategoryById(Long.parseLong(strCategoryId));
	}
	
	@GetMapping(value="/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStatusUsingId(@PathVariable("id") String id) {
		return categoryService.getStatusUsingId(Integer.parseInt(id));
	}
}
