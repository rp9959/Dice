package com.dice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dice.model.Distribution;
import com.dice.model.Simulation;
import com.dice.repository.DistributionRepository;
import com.dice.repository.SimulationRepository;

//This Service contains entire functionality for all the end points that are exposed.

@Service
public class SimulationService {

	@Autowired
	SimulationRepository simulationRepo;

	@Autowired
	DistributionRepository distributionRepo;

	
	/* createSimulation Function is to create a simulation, 
	  return the SUM-COUNT pairs as JSON and Store all the related information in 2 Tables */
	
	@Transactional
	public ResponseEntity<?> createSimulation(int noOfDice, int noOfSides, int noOfRolls) {
		
		//Here diceNumber-diceSides combination is created as a string
		String dice_side = String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides));

		// Storing in SIMULATION Table 
		if (simulationRepo.findByDiceNumberDiceSides(dice_side).isPresent()) {
			int count = simulationRepo.findByDiceNumberDiceSides(dice_side).get().getSimulationCount() + 1;
			simulationRepo.findByDiceNumberDiceSides(dice_side).get().setSimulationCount(count);
			int totalRolls = simulationRepo.findByDiceNumberDiceSides(dice_side).get().getTotalRolls() + noOfRolls;
			simulationRepo.findByDiceNumberDiceSides(dice_side).get().setTotalRolls(totalRolls);
			simulationRepo.save(simulationRepo.findByDiceNumberDiceSides(dice_side).get());
		}

		else {
			Simulation simulation = new Simulation();
			simulation.setDiceNumber_diceSides(dice_side);
			simulation.setTotalRolls(noOfRolls);
			simulation.setSimulationCount(1);
			simulationRepo.save(simulation);
		}

		Map<Integer, Integer> sum_map = new HashMap<Integer, Integer>();

		// Here we create a HashMap of SUM-COUNT pairs and is returned as a JSON
		for (int i = 0; i < noOfRolls; i++) {
			ArrayList<Integer> dice = new ArrayList<Integer>();

			for (int j = 0; j < noOfDice; j++) {
				dice.add((int) (Math.random() * noOfSides) + 1);
			}

			int sum = dice.stream().mapToInt(Integer::intValue).sum();

			if (sum_map.containsKey(sum)) {
				sum_map.put(sum, sum_map.get(sum) + 1);
			} else {
				sum_map.put(sum, 1);
			}

			
			// Storing in DISTRIBUTION Table
			List<Distribution> distributionList = distributionRepo.findBySimulationIdAndSumOnDice(
					simulationRepo.findByDiceNumberDiceSides(dice_side).get().getSimulationId(), sum);
			if (!distributionList.isEmpty()) {
				
				int newCount = distributionList.get(0).getCount();
				distributionList.get(0).setCount(newCount + 1);
				distributionRepo.save(distributionList.get(0));
			}

			else {
				Distribution distribution = new Distribution();
				distribution.setSumOnDice(sum);
				distribution.setCount(1);
				
				try {
					long simId=simulationRepo.findByDiceNumberDiceSides(dice_side).get().getSimulationId();
					distribution.setSimulationId(simId);
					distribution.setRelativeDistribution(0);
					distributionRepo.save(distribution);
					}
				
				catch (Exception e)
				{
					return ResponseEntity.ok().body(e.toString());
				}

			}
		}
		
		//Here relative Distribution shall be updated based on the total rolls made
		distributionRepo.findAll().stream().forEach(i->
		{
			int count=i.getCount();
			int totalRolls=simulationRepo.findByDiceNumberDiceSides(dice_side).get().getTotalRolls();
			float relativeDistribution = (float) ((float) count / (float) totalRolls) * 100;
			i.setRelativeDistribution(relativeDistribution);
		});

		return  ResponseEntity.ok().body(sum_map); // returns JSON with SUM-Count pairs
	}
	
	

	/*
	 * getAllSimulationDetails Function Returns Total Simulations and Total number
	 * of rolls for all the existing diceNumber-diceSides combinations
	 */

	public ResponseEntity<ArrayList<Simulation>> getAllSimulationDetails() {	

		ArrayList<Simulation> list = new ArrayList<Simulation>();

			simulationRepo.findAll().stream().forEach(i -> {
			list.add(i);
		});
			
			return ResponseEntity.ok().body(list); 
		
	}

	
	
	
	/*
	 * getRelativeDistribution Function returns the Relative Distribution compared
	 * to Total number of rolls for a given diceNumber-diceSides combination
	 */

	public ResponseEntity<?> getRelativeDistribution(int noOfDice, int noOfSides) {

		String dice_side = String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides));

		try {
			long simulationId = simulationRepo.findByDiceNumberDiceSides(dice_side).get().getSimulationId();

			ArrayList<Distribution> list = new ArrayList<Distribution>();

			distributionRepo.findBySimulationId(simulationId).stream().forEach(i -> {
				list.add(i);
			});

			return ResponseEntity.ok().body(list);
		}

		catch (Exception e)
		{
			return ResponseEntity.ok().body(e.toString());
		}

	}
	
}
