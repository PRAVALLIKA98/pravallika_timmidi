package com.olx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olx.dto.AdvertiseDTO;
import com.olx.dto.Category;
import com.olx.dto.UserDTO;

@Service
public class UserServiceDelegateImpl implements UserServiceDelegate {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CircuitBreakerFactory circuitBreakerFactory;

	@Override
	public Map findByUsername(String username) {
		return this.restTemplate.getForObject("http://auth-service/user/" + username, Map.class);
	}
/*	
	@Override
	public Map findByUsername(String username) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userCircuitBreaker");
		Map mapUser = circuitBreaker.run(
			()->this.restTemplate.getForObject("http://localhost:9000/user/" + username, Map.class),
			throwable -> callbackFindByUsername(username)
		);
		return mapUser;
	}
*/	
	public Map callbackFindByUsername(String username) {
		System.out.println("Circuit Breaker: Failure in findByUsername - " + username);
		return null;
	}
	
	@Override
	public List<Map> findByUsernames(String usernames) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userListCircuitBreaker");
		List<Map> listMapUsers = circuitBreaker.run(
			()->this.restTemplate.getForObject("http://localhost:9000/user/list/" + usernames, List.class),
			throwable -> callbackFindByUsernames(usernames)
		);
		return listMapUsers;
	}
	public List<Map> callbackFindByUsernames(String usernames) {
		System.out.println("Circuit Breaker: Failure in findByUsernames - " + usernames);
		return null;
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		return new RestTemplate();
	}
	@Override
	public Map isLoggedInUser(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authToken);
		HttpEntity entity = new HttpEntity(headers);
		try {
			Boolean value=false;
			ResponseEntity<Map> response = 
					this.restTemplate.exchange("http://auth-service/token/validate",  HttpMethod.GET, entity, Map.class);
			//This auth-service is the application-name of the olx-loginA project
			if(response.getStatusCode() == HttpStatus.OK)
			{
				return response.getBody();
			}
			else
				return null;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public Category getCategoryTypeUsingId(long id) {
		String strId = String.valueOf(id);
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Content-Type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Category> response = this.restTemplate.exchange("http://masterdata-service/category/"+strId,HttpMethod.GET,entity, Category.class);
		
		if(response.getStatusCode()==HttpStatus.OK) {
			return response.getBody();
		}
		return null;
	}
	
	public String getAdvertisementStatusUsingId(long id) {
		String strId = String.valueOf(id);
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Content-Type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<String> response = this.restTemplate.exchange("http://masterdata-service/status/"+strId, HttpMethod.GET,entity,String.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		return null;
	}
}
