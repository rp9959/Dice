package com.dice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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

	private Long simulationId; 				/*( Id that is taken from Simulation table. Basically each diceNumber-diceSide 
												//		combination will have a simulationId).*/
	
	private int sumOnDice; 					//( Represents Sum on the dice for a specific diceNumber-diceSide combination)

	private int count; 						//(Stores the count of a a specific Sum on the dice for a 
												//specific diceNumber-diceSide combination)

	private float relativeDistribution;		 //(stores the relativeDistribution compared to totalRolls 
												//for the specific diceNumber-diceSides combination)
	
	public float getRelativeDistribution() {
		return relativeDistribution;
	}
	public void setRelativeDistribution(float relativeDistribution) {
		this.relativeDistribution = relativeDistribution;
	}
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
