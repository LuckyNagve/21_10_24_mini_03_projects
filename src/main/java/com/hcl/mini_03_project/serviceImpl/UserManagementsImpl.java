package com.hcl.mini_03_project.serviceImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.mini_03_project.Repository.CityRepo;
import com.hcl.mini_03_project.Repository.CountryRepo;
import com.hcl.mini_03_project.Repository.StateRepo;
import com.hcl.mini_03_project.Repository.UserRegisterRepo;
import com.hcl.mini_03_project.Utils.EmailUtils;
import com.hcl.mini_03_project.binding.LoginForm;
import com.hcl.mini_03_project.binding.UnlockAccForm;
import com.hcl.mini_03_project.binding.UserForm;
import com.hcl.mini_03_project.model.City;
import com.hcl.mini_03_project.model.Country;
import com.hcl.mini_03_project.model.State;
import com.hcl.mini_03_project.model.UserRegistration;
import com.hcl.mini_03_project.service.UserManagemenentService;


@Service
public class UserManagementsImpl implements UserManagemenentService{
@Autowired
private UserRegisterRepo userRepo;

@Autowired
private CountryRepo countryRepo;

@Autowired
private CityRepo cityRepo;

@Autowired
private StateRepo stateRepo;

@Autowired
private EmailUtils emailutl;
	
	@Override
	public String checkEmail(String email) {
		UserRegistration user =userRepo.findByEmail(email);
		if(user==null) {
			return "UNIQUE";
		}
		
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer,String>coutrymap=new HashMap<Integer, String>();
		List<Country> countries=countryRepo.findAll();
		countries.forEach(country->{
			coutrymap.put(country.getCountryId(),country.getCountryName());
		});
		return coutrymap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<State> states=stateRepo.findByCountryId(countryId);
		Map<Integer,String>statemap=new HashMap();
		states.forEach(state->{
			statemap.put(state.getStateId(), state.getStateName());
		});
		return statemap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<City> cities=cityRepo.findByStateId(stateId);
		Map<Integer,String>citymap=new HashMap();
		cities.forEach(citylist->{
			citymap.put(citylist.getCityId(), citylist.getCityName());
		});
		return citymap;
	}

	@Override
	public String userRegistration(UserForm userForm) {
		//copy binding obj to entity 
		
		UserRegistration userregister=new UserRegistration();
		BeanUtils.copyProperties(userForm, userregister);
		
		//generate & set random pwd
		userregister.setUserPwd(generateRandompwd());
		
		//set Acc status as Locked
		userregister.setAccStatus("LOCKED");
		
		
		//send email to unlocked Acc
		userRepo.save(userregister);
		
		String to=userForm.getEmail();
		String subject="Registartion Email";
		String body=readEmailBody("REG_EMAIL_BODY.txt",userregister);
	
		emailutl.sendEmail(to, subject,body);
		
		return "User Account Created";
	}

	@Override
	public String unlockedAcc(UnlockAccForm unlockedacc) {
		String email=unlockedacc.getEmail();
            UserRegistration user=userRepo.findByEmail(email);
            
            if(user!=null && user.getUserPwd().equals(unlockedacc.getTempPwd())) {
            	user.setUserPwd(unlockedacc.getNewPwd());
            	user.setAccStatus("UNLOCKED");
            	userRepo.save(user);
            	return "Account Unlocked";		
            }
		return "invalid Temporary Password";
	}

	@Override
	public String forgetPass(String email) {
		
		UserRegistration user=userRepo.findByEmail(email);
		if(user==null) {
			return "No Account Found";
		}
		
		String subject="Recover password";
		String body=readEmailBody("FORGET_PWD_EMAIL_BODY.txt",user);
		
		emailutl.sendEmail(email,subject,body);
		return "password send to register email";
	}

	@Override
	public String login(LoginForm loginform) {
		
		UserRegistration user=userRepo.FindByEmailAndUserPwd(loginform.getEmail(),loginform.getPwd() );
		if(user==null) {
			return "Invalid credentials";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "SUCCESS";
	}

	private String generateRandompwd() {
		String text="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	StringBuilder sb=new StringBuilder();
	Random random=new Random();
	int pwdlength=6;
	for(int i=1;i<=pwdlength;i++) {
		int index=random.nextInt(text.length());
		sb.append(text.charAt(index));
	}
	return sb.toString();
	}
	
	private String readEmailBody(String filename,UserRegistration user) {
		StringBuffer sb=new StringBuffer();
		try(Stream<String> lines=Files.lines(Paths.get(filename)))
		{
			lines.forEach(line->{
				line=line.replace("${FNAME}", user.getFname());
				line=line.replace("${LNAME}", user.getLname());
				line=line.replace("${TEMP_PWD}", user.getUserPwd());
				line=line.replace("${EMAIL}", user.getEmail());
				line=line.replace("${PWD}", user.getUserPwd());
				
				sb.append(line);
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
