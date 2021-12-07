package com.olx.service;

import java.util.List;
import java.util.Map;

import com.olx.dto.AdvertiseDTO;
import com.olx.dto.Category;
import com.olx.dto.UserDTO;

public interface UserServiceDelegate {
	public List<Map> findByUsernames(String usernames);
	public Map findByUsername(String username);
	public Map isLoggedInUser(String authToken);
	public Category getCategoryTypeUsingId(long id);
	public String getAdvertisementStatusUsingId(long id);
}
