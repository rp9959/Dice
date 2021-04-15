package com.synpulse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class Distribution {
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Long simulationId;
	
	private int sumOnDice;
	private int count;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "simulation_distribution", 
    joinColumns =  @JoinColumn(name = "simulationId")) 
    private Simulation simulation;

	public Long getSimulationId() {
		return simulationId;
	}
	public void setSimulationId(Long simulationId) {
		this.simulationId = simulationId;
	}
	public int getSumOnDice() {
		return sumOnDice;
	}
	public void setSumOnDice(int sumOnDice) {
		this.sumOnDice = sumOnDice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
