package com.hcl.mini_03_project.service;

import java.util.Map;

import com.hcl.mini_03_project.binding.LoginForm;
import com.hcl.mini_03_project.binding.UnlockAccForm;
import com.hcl.mini_03_project.binding.UserForm;



public interface UserManagemenentService {
	
public String checkEmail(String email);

public Map<Integer,String>getCountries();

public Map<Integer,String>getStates(Integer countryId);

public Map<Integer,String>getCities(Integer stateId);

public String userRegistration(UserForm userForm );

public String unlockedAcc(UnlockAccForm unlockedacc);

public String forgetPass(String email);

  public String login(LoginForm loginform);

}
