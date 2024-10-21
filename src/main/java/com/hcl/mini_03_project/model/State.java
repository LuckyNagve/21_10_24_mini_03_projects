package com.hcl.mini_03_project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="STATENAME")

public class State {

	@Id
	private Integer stateId;
	private String stateName;
	private Integer countryId;
}
