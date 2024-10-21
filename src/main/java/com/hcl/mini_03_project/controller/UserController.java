package com.hcl.mini_03_project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mini_03_project.binding.LoginForm;
import com.hcl.mini_03_project.binding.UnlockAccForm;
import com.hcl.mini_03_project.binding.UserForm;
import com.hcl.mini_03_project.service.UserManagemenentService;



@RestController
public class UserController {

	@Autowired
	private UserManagemenentService userManagenmentsService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginform){
		String status=userManagenmentsService.login(loginform);
		return new ResponseEntity<String>(status,HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public Map<Integer,String> loadCountry(){
		return userManagenmentsService.getCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer,String>loadstates(@PathVariable Integer countryId){
		return userManagenmentsService.getStates(countryId);
	}
	
	@GetMapping("/cities/{stateId}")
	public Map<Integer,String>loadcities(@PathVariable Integer stateId){
		return userManagenmentsService.getCities(stateId);
	}
	
	@GetMapping("/email{email}")
	public String emailcheck(@PathVariable String email) {
		return userManagenmentsService.checkEmail(email);

	}
	
	@PostMapping("/user")
	public ResponseEntity<String> userRegister(@RequestBody UserForm userForm){
		String 	usersave=userManagenmentsService.userRegistration(userForm);
		return new ResponseEntity<String>(usersave,HttpStatus.CREATED);
	}
	
	@PostMapping("/unlockedacc")
	public ResponseEntity<String> unlockedAcc(@RequestBody UnlockAccForm unlockedAcc){
		String unlocked=userManagenmentsService.unlockedAcc(unlockedAcc);
		return new ResponseEntity<String>(unlocked,HttpStatus.OK);

	}
	
	@GetMapping("/forgetPwd")
	public ResponseEntity<String> forgetPass(@PathVariable String email ){
		String frpwd=userManagenmentsService.forgetPass(email);
		return new ResponseEntity<String>(frpwd,HttpStatus.OK);

	}
	
}
