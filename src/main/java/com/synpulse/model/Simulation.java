package com.synpulse.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Simulation {
	
	@Id
	@Column(name="SimulationId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long simulationId;
	
	String diceSide;
	int simulationCount;
	int totalRolls;

	public Long getSimulationId() {
		return simulationId;
	}

	public String getDiceSide() {
		return diceSide;
	}
	public void setDiceSide(String diceSide) {
		this.diceSide = diceSide;
	}
	public int getSimulationCount() {
		return simulationCount;
	}
	public void setSimulationCount(int simulationCount) {
		this.simulationCount = simulationCount;
	}
	public int getTotalRolls() {
		return totalRolls;
	}
	public void setTotalRolls(int totalRolls) {
		this.totalRolls = totalRolls;
	}
	
	

}
