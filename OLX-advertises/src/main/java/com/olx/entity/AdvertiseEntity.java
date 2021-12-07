package com.olx.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="advertises")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdvertiseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private String title;
	private String description;
	private double price;
	//private long category;
	private int active;
	@Column(name="created_date")
	private LocalDate createdDate;
	@Column(name="modified_date")
	private LocalDate modifiedDate;
	@Column(name="username")
	private String username;
	//private String categories;
	private long categoryId;
	private int statusId;
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "category")
	//private CategoryEntity categoryEntity;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "active")
	//private AdvertiseStatusEntity advStatusEntity;
	public AdvertiseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdvertiseEntity(String title, String description, double price, long category, LocalDate createdDate,
			LocalDate modifiedDate, int active, String username) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.active = active;
		this.username = username;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int status) {
		this.statusId = status;
	}
}