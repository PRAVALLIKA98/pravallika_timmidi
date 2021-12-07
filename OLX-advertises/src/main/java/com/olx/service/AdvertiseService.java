package com.olx.service;

import java.util.List;

import com.olx.dto.AdvertiseDTO;

public interface AdvertiseService {
	
	List<AdvertiseDTO> getAllAdvertises();
	
	List<AdvertiseDTO> getAllAdvertisesByUsername(String username);
	
	AdvertiseDTO createNewAdvertise(AdvertiseDTO advertiseDto, String authToken);
	
	AdvertiseDTO updateAdvertise(int id,AdvertiseDTO advertise,String authToken);
	
	AdvertiseDTO getAdvertisePostedByUser(long id, String authToken);
	
	Boolean deleteAdvertisePostedByUser(long id, String authToken);
	
	List<AdvertiseDTO> searchAdvertisements(String searchKey);
	
	List<AdvertiseDTO> searchAdvertisementsUsingFilter(String searchKey);
	
	AdvertiseDTO getSpecificAdvertiseUsingId(long id, String authToken);
}
