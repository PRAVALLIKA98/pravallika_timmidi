package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.RegisterRequest;
import com.olx.dto.UserDTO;
import com.olx.service.UserService;
import com.olx.utils.JwtUtils;

@RestController
//@CrossOrigin("*")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@GetMapping(value="/token/validate")
	public ResponseEntity<UserDetails> isTokenValid(@RequestHeader("Authorization") String jwtToken) {
		
		//Extract the jwtToken by removing the word "Bearer " from jwtToken string. Use substring()
		jwtToken = jwtToken.substring(7, jwtToken.length());
		
		String username = jwtUtils.extractUsername(jwtToken);
		
	
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
		if(isTokenValid)
			return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
		else
			return new ResponseEntity<UserDetails>(userDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping(value="/user/authenticate", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		}
		catch(BadCredentialsException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		UserDTO userDto = userService.findByUsername(authRequest.getUsername());
		userDto.setJwtToken(jwtUtils.generateToken(authRequest.getUsername()));
		String token = userDto.getJwtToken();
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
	
	@PostMapping(value="/user", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerRequest) throws Exception {
		return new ResponseEntity<UserDTO>(userService.registerUser(registerRequest), HttpStatus.OK);
	}
	
	@GetMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> register(@RequestHeader String authToken) throws Exception {
		ResponseEntity<UserDetails> userDetails = isTokenValid(authToken);
		UserDetails userData = userDetails.getBody();
		return new ResponseEntity<UserDTO>(userService.getUserInfo(userData.getUsername()), HttpStatus.OK);
	}
	
	@GetMapping(value="/user/list/{ids}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<UserDTO> findByUsernames(@PathVariable("ids") String usernames) {
		return userService.findByUsernames(usernames);
	}
	
	@GetMapping(value="/user/{username}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> findByUsername(@PathVariable("username") String username) {
		
		//We have to pass the token like Bearer:jwtToken in @RequestHeader. Otherwise, u wont get the list/result
		//Like this Bearer:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmFuZCIsImV4cCI6MTYzNzM0OTUyMywiaWF0IjoxNjM3MzEzNTIzfQ.ONGGC1QaMoc0DOh0XxM16vg-28IiD5qGuRnin3IqL7I
		    return new ResponseEntity<UserDTO>(userService.findByUsername(username), HttpStatus.OK);
	}

}
