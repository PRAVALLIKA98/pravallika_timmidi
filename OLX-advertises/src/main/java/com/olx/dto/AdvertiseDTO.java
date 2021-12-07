package com.olx.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AdvertiseDTO {
	private static final String response = null;
	private long id;
	private String title;
	private String description;
	private double price;
	private long categories;
	private String category;
	private LocalDate createdDate;
	private LocalDate modifiedDate;
	private int active;
	private String username;
	private String postedBy;
	private long categoryId;
	private int statusId;
	private String status;
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatus(int status) {
		this.statusId = status;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	//The constructor AdvertiseDTO(long, String, String, double, String) is undefined
	public AdvertiseDTO(long id2, String title2, String description2, double price2, String categories2,
			LocalDate createdDate2, LocalDate modifiedDate2, int active2,String username2, String status) {
		// TODO Auto-generated constructor stub
		
		this.id = id2;
		this.title = title2;
		this.description = description2;
		this.price = price2;
		//this.categories = categories2;
		this.category = categories2;
		this.createdDate = createdDate2;
		this.modifiedDate = modifiedDate2;
		this.active = active2;
		this.username = username2;
		//this.postedBy = postedBy2;
		this.status=status;
	}
	public AdvertiseDTO(long id2, String title2, String description2, double price2, Object forEach,
			LocalDate createdDate2, LocalDate modifiedDate2, String username2) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.title = title2;
		this.description = description2;
		this.price = price2;
		//this.category = object;
		this.createdDate = createdDate2;
		this.modifiedDate = modifiedDate2;
		this.username = username2;
	}
	public AdvertiseDTO() {
		// TODO Auto-generated constructor stub
	}
	public AdvertiseDTO(int id2, String title2, double price2, String category,String description2, String username2,
			LocalDate createdDate2, LocalDate modifiedDate2, String response) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.title=title2;
		this.price=price2;
		this.category=category;
		this.description = description2;
		this.username=username2;
		this.createdDate=createdDate2;
		this.modifiedDate = modifiedDate2;
		this.status=response;
	}
	public AdvertiseDTO(long id2, String title2, double price2, String name, String description2,
			LocalDate createdDate2, LocalDate modifiedDate2, String response2) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.title=title2;
		this.price=price2;
		this.category=name;
		this.description = description2;
		this.createdDate=createdDate2;
		this.modifiedDate = modifiedDate2;
		this.status=response2;
	}
	public AdvertiseDTO(long id2, String title2, double price2, String name, String description2,
			LocalDate createdDate2, LocalDate modifiedDate2, String response2, String postedBy) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.title=title2;
		this.price=price2;
		this.category=name;
		this.description = description2;
		this.createdDate=createdDate2;
		this.modifiedDate = modifiedDate2;
		this.status=response2;
		this.postedBy=postedBy;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getCategories() {
		return categories;
	}
	public void setCategories(long categories) {
		this.categories = categories;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
}
