package com.hcl.mini_03_project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mini_03_project.model.City;


@Repository
public interface CityRepo extends JpaRepository<City, Integer>{

	//salect * from state where countryId=?
	public List<City>findByStateId(Integer stateId);
}
