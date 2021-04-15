package com.dice.model;

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

/* This table is to store the Sum on dice and the number of times it occurs for a 
specific diceNumber-diceSide combination. */

@Entity
@Component
public class Distribution {
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Long simulationId; /*( Id that connects with Simulation table. Basically each diceNumber-diceSide 
										combination will have a simulationId).*/
	
	private int sumOnDice; //( Represents Sum on the dice for a specific diceNumber-diceSide combination)

	private int count; //(Stores the count of a a specific Sum on the dice for a specific diceNumber-diceSide combination)
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "simulation_distribution", 
    joinColumns =  @JoinColumn(name = "simulationId")) 
    private Simulation simulation;  //Here we are trying to create a relation between two tables.

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
