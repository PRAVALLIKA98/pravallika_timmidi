package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.RegisterRequest;
import com.olx.dto.UserDTO;
import com.olx.entity.UserEntity;
import com.olx.exception.UsernameAlreadyExistsException;
import com.olx.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDTO findByUsername(String username) {
		List<UserEntity> userEntityList = userRepository.findByUserName(username);
		if(userEntityList==null || userEntityList.size()==0) {
			return null;
		}
		//UserEntity userEntity = opUserEntity.isPresent() ? opUserEntity.get() : null;
		UserEntity userEntity = userEntityList.get(0);
		UserDTO userDto = new UserDTO();
		userDto.setFirstName(userEntity.getFirstName());
		userDto.setLastName(userEntity.getLastName());
		userDto.setUsername(userEntity.getUserName());
		return userDto;
	}

	@Override
	public UserDTO registerUser(RegisterRequest registerRequest) {
		List<UserEntity> userEntityList = userRepository.findByUserName(registerRequest.getUsername());
		
		if(userEntityList!=null && userEntityList.size()>0) {
			throw new UsernameAlreadyExistsException(registerRequest.getUsername());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setActive(1);
		userEntity.setFirstName(registerRequest.getFirstname());
		userEntity.setLastName(registerRequest.getLastname());
		userEntity.setPassword(registerRequest.getPassword());
		userEntity.setRoles("USER");
		userEntity.setUserName(registerRequest.getUsername());
		userEntity = userRepository.save(userEntity);
		UserDTO userDto = new UserDTO();
		userDto.setFirstName(userEntity.getFirstName());
		userDto.setLastName(userEntity.getLastName());
		userDto.setUsername(userEntity.getUserName());
		return userDto;
	}

	@Override
	public List<UserDTO> findByUsernames(String usernames) {
		String usernameArray[] = usernames.split(",");
		List<UserDTO> userList = new ArrayList<>();
		for(int i=0; i<usernameArray.length; i++) {
			if(usernameArray[i]!=null && usernameArray[i].trim().length()>0) {
				userList.add(this.findByUsername(usernameArray[i]));
			}
		}
		return userList;
	}
	
	public UserDTO getUserInfo(String userName){
		List<UserEntity> userEntityList = userRepository.findByUserName(userName);
		
		if(userEntityList!=null && userEntityList.size()>0) {
			UserEntity  userEntity = userEntityList.get(0);
			UserDTO userData = new UserDTO();
			userData.setId(userEntity.getId());
			userData.setFirstName(userEntity.getFirstName());
			userData.setLastName(userEntity.getLastName());
			userData.setUsername(userEntity.getUserName());
			userData.setPassword(userEntity.getPassword());
			userData.setEmail(userEntity.getEmail());
			userData.setPhone(userEntity.getPhoneNumber());
			
			return userData;
		}else {
			return null;
		}
	}

}
