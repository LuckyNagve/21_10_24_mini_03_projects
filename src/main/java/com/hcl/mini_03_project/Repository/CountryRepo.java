package com.hcl.mini_03_project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mini_03_project.model.Country;



@Repository
public interface CountryRepo extends JpaRepository<Country, Integer>{

}
