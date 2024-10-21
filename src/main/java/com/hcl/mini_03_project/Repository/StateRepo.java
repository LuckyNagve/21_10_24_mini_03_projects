package com.hcl.mini_03_project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mini_03_project.model.Country;
import com.hcl.mini_03_project.model.State;



@Repository
public interface StateRepo extends JpaRepository<State, Integer>{
// salect * from  state where countryId=?
	public List<State> findByCountryId(Integer countryId);

	public List<Country> findyCountryId(Integer countryId);
}
