package com.dice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

/*This table is to store the total number of simulations and total number of rolls for 
a specific diceNumber-diceSide combination.*/

@Entity
@Component
public class Simulation {

	@Id
	@Column(name = "SimulationId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long simulationId; 			// (Id given for a specific DiceNumber- diceSide combination

	String diceNumberDiceSides; 		// (This is a string which is specific DiceNumber- diceSide combination).

	int simulationCount; 				// (This is total number of simulations for the specific dice_side combination)

	int totalRolls; 					// (Total number of rolls for the specific dice_side combination).

	public Long getSimulationId() {
		return simulationId;
	}


	public String getDiceNumber_diceSides() {
		return diceNumberDiceSides;
	}

	public void setDiceNumber_diceSides(String diceNumber_diceSides) {
		this.diceNumberDiceSides = diceNumber_diceSides;
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
