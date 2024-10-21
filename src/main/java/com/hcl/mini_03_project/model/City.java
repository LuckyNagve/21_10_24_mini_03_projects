package com.hcl.mini_03_project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="CITY_MASTER")
@Data
public class City {

	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;
}