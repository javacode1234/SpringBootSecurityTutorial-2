package com.scuritydemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scuritydemo.model.MyUser;
import com.scuritydemo.repository.MyUserRepository;

@Service
public class MyUserService {

	@Autowired
	private MyUserRepository myUserRepo;
	
	public void saveMyUser(MyUser myUser) {
		myUserRepo.save(myUser);
	}
	
	public void updateMyUserById(MyUser myUser) {
		myUserRepo.save(myUser);
	}
	
	public void deleteMyUserById(Integer id) {
		myUserRepo.deleteById(id);
	}
	
	public Optional<MyUser> getMyUserById(Integer id){
		return myUserRepo.findById(id);		
	}

	public List<MyUser> findAllUsers() {
		return myUserRepo.findAll();
	}

	public MyUser getMyUserByUsername(String username) {
		return myUserRepo.findByUsername(username).get();
	}

	public boolean userExistForUpdate(String username, Integer userId) {
		if(userId!=null) {
			List<MyUser> users = myUserRepo.findByUsername(username).stream().toList();
			for(MyUser user : users) {
				if(user.getId()!=userId) {
					return true;
				}			
			}
		}
		return false;
		
	}
	
	public boolean userExistForNewUser(String username) {
		Optional<MyUser> user = myUserRepo.findByUsername(username);
		if(user.isPresent()) { return true; }
		return false;
	}

	public boolean thereAreAnyUser() {
		if(myUserRepo.findAll().isEmpty()) {
			return false;
		}
		return true;
	}
	
	
}
