package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.AdvertiseDTO;
import com.olx.dto.Category;
import com.olx.dto.UserDTO;
import com.olx.entity.AdvertiseEntity;
import com.olx.entity.AdvertiseStatusEntity;
import com.olx.exception.InvalidAuthorizationTokenException;
import com.olx.repo.AdvertiseRepo;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

	@Autowired
	private AdvertiseRepo advertiseRepo;
	
	@Autowired
	private CategoryServiceDelegate categoryServiceDelegate;
	@Autowired
	private UserServiceDelegate userServiceDelegate;
	
	@Override
	public List<AdvertiseDTO> getAllAdvertises() {
		List<AdvertiseEntity> advertiseEntities = advertiseRepo.findAll();
		/*
		String usernames = 
				advertiseEntities.stream().map((advertiseEntity)->advertiseEntity.getUsername()).collect(Collectors.joining(","));
		List<Map> listMapUsers = userServiceDelegate.findByUsernames(usernames);
		*/
		List<Map> listMapUsers = new ArrayList<>();
		advertiseEntities.stream().forEach((advertiseEntity)-> {
			listMapUsers.add(userServiceDelegate.findByUsername(advertiseEntity.getUsername()));
			//System.out.println(advertiseEntity.getUsername());
		});
		List<AdvertiseDTO> advertises = new ArrayList<AdvertiseDTO>();
		List<Map> categories = categoryServiceDelegate.getAllCategories();
		System.out.println(categories.toString());
		Map<Long, String> hmapCategories = new HashMap<>();
		categories.stream().forEach((categoryMap)-> {
			System.out.println("categoryMap: " + categoryMap);
			hmapCategories.put(new Long((Integer)categoryMap.get("id")), (String)categoryMap.get("name"));
		});
		
		//categoryMap.ge
		
		advertiseEntities.stream().forEach((advertiseEntity)-> {
			Map tempMap = listMapUsers.stream().filter((mapUser)-> {
				return mapUser.get("username").equals(advertiseEntity.getUsername());
		}).findFirst().get();
			String postedBy = tempMap.get("firstName") + " " + tempMap.get("lastName");
			for(Map map:categories) {
				Object obj = map.get("id");
			}
			//Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(categories.listIterator());
			advertises.add(new AdvertiseDTO(advertiseEntity.getId(), advertiseEntity.getTitle(), advertiseEntity.getDescription(), advertiseEntity.getPrice(), advertiseEntity.getCategoryId(), advertiseEntity.getCreatedDate(), advertiseEntity.getModifiedDate(), advertiseEntity.getUsername()));
		});
		return advertises;
	}

	@Override
	public AdvertiseDTO createNewAdvertise(AdvertiseDTO advertiseDto, String authToken) {
		Map mapUser = userServiceDelegate.isLoggedInUser(authToken);
		if(mapUser==null)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertiseEntity advertiseEntity = new AdvertiseEntity(advertiseDto.getTitle(), advertiseDto.getDescription(), advertiseDto.getPrice(), advertiseDto.getCategories(), LocalDate.now(), LocalDate.now(), 1, advertiseDto.getUsername());
		advertiseEntity.setUsername((String) mapUser.get("username"));
		
		Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseDto.getCategoryId());
		System.out.println(categoryDto.getName());
		String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseDto.getStatusId());
		System.out.println(response);
		advertiseEntity.setCategoryId(advertiseDto.getCategoryId());
		advertiseEntity.setStatusId(advertiseDto.getStatusId());
		advertiseEntity = advertiseRepo.save(advertiseEntity);
		
		return new AdvertiseDTO(advertiseEntity.getId(), advertiseEntity.getTitle(), advertiseEntity.getDescription(), advertiseEntity.getPrice(), categoryDto.getName(), advertiseEntity.getCreatedDate(), advertiseEntity.getModifiedDate(), advertiseEntity.getActive(),advertiseEntity.getUsername(),response);
	}
	
	public AdvertiseDTO updateAdvertise(int id,AdvertiseDTO advertise,String authToken) {
		Map mapUser = userServiceDelegate.isLoggedInUser(authToken);
		if(mapUser==null)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertiseEntity advertiseEntity = new AdvertiseEntity(advertise.getTitle(), advertise.getDescription(), advertise.getPrice(), advertise.getCategories(), LocalDate.now(), LocalDate.now(), 1, advertise.getUsername());
		advertiseEntity.setUsername((String) mapUser.get("username"));
		
		Optional<AdvertiseEntity> opAdvertiseEntity = advertiseRepo.findById((long) id);
		AdvertiseEntity advEntity = opAdvertiseEntity.get();
		if(opAdvertiseEntity!=null) {
			advertiseEntity.setId(id);
			advertiseRepo.save(advertiseEntity);
		}
		String response = userServiceDelegate.getAdvertisementStatusUsingId(advEntity.getStatusId());
		Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advEntity.getCategoryId());
		advertiseEntity.setStatusId(advEntity.getStatusId());
		AdvertiseDTO advertiseDto = new AdvertiseDTO(id,advertiseEntity.getTitle(),advertiseEntity.getPrice(), categoryDto.getName(), advertiseEntity.getDescription(),advertiseEntity.getUsername(),advertiseEntity.getCreatedDate(),
				advertiseEntity.getModifiedDate(), response);
		return advertiseDto;
	}

	@Override
	public List<AdvertiseDTO> getAllAdvertisesByUsername(String authToken) {
		Map userData = userServiceDelegate.isLoggedInUser(authToken);
		String username = (String) userData.get("userName");
		//String username = userData.get(0);
		List<AdvertiseEntity> advertiseEntities = advertiseRepo.findByUsername(username);
	//	Map mapUser = userServiceDelegate.findByUsername(username);
		List<AdvertiseDTO> advertises = new ArrayList<AdvertiseDTO>();
		for (AdvertiseEntity advertiseEntity : advertiseEntities) {
			String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseEntity.getStatusId());
			Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseEntity.getCategoryId());
			
			AdvertiseDTO advertiseDTO = new AdvertiseDTO(advertiseEntity.getId(),advertiseEntity.getTitle(),advertiseEntity.getPrice(),categoryDto.getName(),advertiseEntity.getDescription(),
					advertiseEntity.getCreatedDate(),advertiseEntity.getModifiedDate(),response);
			advertises.add(advertiseDTO);
		}
		
		return advertises;
	}
	public AdvertiseDTO getAdvertisePostedByUser(long id, String authToken) {
		Map userData = userServiceDelegate.isLoggedInUser(authToken);
		String username = (String) userData.get("userName");
		AdvertiseEntity advertiseEntity = advertiseRepo.findByUserNameAndPostId(id, username);
		AdvertiseDTO advertiseDTO = null;
		if(advertiseEntity!=null) {
			String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseEntity.getStatusId());
			Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseEntity.getCategoryId());
		    advertiseDTO = new AdvertiseDTO(advertiseEntity.getId(),advertiseEntity.getTitle(),advertiseEntity.getPrice(),categoryDto.getName(),advertiseEntity.getDescription(),
					advertiseEntity.getCreatedDate(),advertiseEntity.getModifiedDate(),response);
		}
		return advertiseDTO;
	}
	
	public Boolean deleteAdvertisePostedByUser(long id, String authToken) {
		Map userData = userServiceDelegate.isLoggedInUser(authToken);
		String username = (String) userData.get("userName");
		AdvertiseEntity advertiseEntity = advertiseRepo.findByUserNameAndPostId(id, username);
		if(advertiseEntity!=null) {
			advertiseRepo.delete(advertiseEntity);
			return true;
		}
		return false;
	}
	
	public List<AdvertiseDTO> searchAdvertisements(String searchKey){
		List<AdvertiseEntity> advertiseEntityList = advertiseRepo.findAdvertiseBySearchKey(searchKey);
		AdvertiseDTO advertiseDTO = null;
		List<AdvertiseDTO> advertiselist = new ArrayList<>();
		if(advertiseEntityList!=null) {
			for(AdvertiseEntity advertiseEntity : advertiseEntityList) {
			String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseEntity.getStatusId());
			Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseEntity.getCategoryId());
		    advertiseDTO = new AdvertiseDTO(advertiseEntity.getId(),advertiseEntity.getTitle(),advertiseEntity.getPrice(),categoryDto.getName(),advertiseEntity.getDescription(),
					advertiseEntity.getCreatedDate(),advertiseEntity.getModifiedDate(),response);
		    advertiselist.add(advertiseDTO);
			}
		}
		return advertiselist;	
	}
	
	public List<AdvertiseDTO> searchAdvertisementsUsingFilter(String searchKey){
		List<AdvertiseEntity> advertiseEntityList = advertiseRepo.findAdvertiseBySearchFiler(searchKey);
		AdvertiseDTO advertiseDTO = null;
		List<AdvertiseDTO> advertiselist = new ArrayList<>();
		if(advertiseEntityList!=null) {
			for(AdvertiseEntity advertiseEntity : advertiseEntityList) {
			String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseEntity.getStatusId());
			Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseEntity.getCategoryId());
		    advertiseDTO = new AdvertiseDTO(advertiseEntity.getId(),advertiseEntity.getTitle(),advertiseEntity.getPrice(),categoryDto.getName(),advertiseEntity.getDescription(),
					advertiseEntity.getCreatedDate(),advertiseEntity.getModifiedDate(),response);
		    advertiselist.add(advertiseDTO);
			}
		}
		return advertiselist;	
	}
	
	public AdvertiseDTO getSpecificAdvertiseUsingId(long id, String authToken) {
		Map userData = userServiceDelegate.isLoggedInUser(authToken);
		String username = (String) userData.get("userName");
		if(username!=null) {
		Optional<AdvertiseEntity> opAdvertiseEntity = advertiseRepo.findById(id);
		AdvertiseEntity advertiseEntity = opAdvertiseEntity.get();
		AdvertiseDTO advertiseDTO = null;
		if(advertiseEntity!=null) {
			String response = userServiceDelegate.getAdvertisementStatusUsingId(advertiseEntity.getStatusId());
			Category categoryDto = userServiceDelegate.getCategoryTypeUsingId(advertiseEntity.getCategoryId());
		    advertiseDTO = new AdvertiseDTO(advertiseEntity.getId(),advertiseEntity.getTitle(),advertiseEntity.getPrice(),categoryDto.getName(),advertiseEntity.getDescription(),
					advertiseEntity.getCreatedDate(),advertiseEntity.getModifiedDate(),response,advertiseEntity.getUsername());
		}
		return advertiseDTO;
		}
		return null;
	}

}
