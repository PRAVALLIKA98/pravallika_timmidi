package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AdvertiseDTO;
import com.olx.service.AdvertiseService;

@RestController
@CrossOrigin
public class AdvertiseController {

	@Autowired
	private AdvertiseService advertiseService;
	
	@GetMapping(value="/advertise", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AdvertiseDTO> getAllAdvertises() {
		return advertiseService.getAllAdvertises();
	}

	@GetMapping(value="/advertise/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AdvertiseDTO> getAdvertisesById(@PathVariable("id") String username) {
		return advertiseService.getAllAdvertisesByUsername(username);
	}

	@PostMapping(value="/advertise", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertiseDTO> createNewAdvertise(@RequestBody AdvertiseDTO advertiseDto, @RequestHeader("Authorization") String authToken) {
		AdvertiseDTO advertiseDTO = advertiseService.createNewAdvertise(advertiseDto, authToken);
		return new ResponseEntity<AdvertiseDTO>(advertiseDTO, HttpStatus.OK);
	}
	
	@PutMapping(value="/advertise/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertiseDTO> updateAdvertise(@PathVariable("id") int id,@RequestBody AdvertiseDTO advertiseDto, @RequestHeader("Authorization") String authToken) {
		AdvertiseDTO advertiseDTO = advertiseService.updateAdvertise(id, advertiseDto, authToken);
		return new ResponseEntity<AdvertiseDTO>(advertiseDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/user/advertise", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AdvertiseDTO> getAllAdvertisementsByUser(@RequestHeader String authToken) {
		return advertiseService.getAllAdvertisesByUsername(authToken);
	}
	@GetMapping(value="/user/advertise/{postId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public AdvertiseDTO getAdvertisementsPostedByUser(@PathVariable("postId") long id, @RequestHeader String authToken) {
		return advertiseService.getAdvertisePostedByUser(id, authToken);
	}
	
	@DeleteMapping(value="/user/advertise/{postId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Boolean deleteAdvertisementsPostedByUser(@PathVariable("postId") long id, @RequestHeader String authToken) {
		return advertiseService.deleteAdvertisePostedByUser(id, authToken);
	}
	
	@GetMapping(value="/advertise/search/{searchText}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AdvertiseDTO> searchAdvertisements(@PathVariable("searchText") String searchKey) {
		return advertiseService.searchAdvertisements(searchKey);
	}
	
	@GetMapping(value="/advertise/search/filtercriteria/{filterText}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AdvertiseDTO> searchAdvertisementsUsingFilter(@PathVariable("filterText") String filterText) {
		return advertiseService.searchAdvertisementsUsingFilter(filterText);
	}
	
	@GetMapping(value="/advertise/{postId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public AdvertiseDTO getSpecificAdvertise(@PathVariable("postId") long postId, @RequestHeader String authToken) {
		return advertiseService.getSpecificAdvertiseUsingId(postId, authToken);
	}
	
}
