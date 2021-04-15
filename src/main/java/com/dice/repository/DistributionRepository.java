package com.dice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice.model.Distribution;

//Repository that assists in storing Distribution Table.

public interface DistributionRepository extends JpaRepository<Distribution, Long> {
	
	List<Distribution> findBySimulationId(Long simulationId);
	
	//This method lets us find a row with a combination of 
	//Simulation ID(which is basically a diceNumber-diceSides combination) and Sum On Dice 
	List<Distribution> findBySimulationIdAndSumOnDice(Long simulationId, int sumOnDice);

}
