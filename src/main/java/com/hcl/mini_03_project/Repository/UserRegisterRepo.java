package com.hcl.mini_03_project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mini_03_project.model.UserRegistration;


@Repository
public interface UserRegisterRepo extends JpaRepository<UserRegistration, Integer>{
//salect * from UserRegistration where email=?
	UserRegistration findByEmail(String email);

	//salect * from UserRegistration where email=? and pwd=?
	public UserRegistration FindByEmailAndUserPwd(String email,String pwd);
}

