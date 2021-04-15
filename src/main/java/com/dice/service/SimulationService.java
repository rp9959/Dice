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
	public HashMap<Integer, Integer> createSimulation(int noOfDice, int noOfSides, int noOfRolls) {
		
		//Here diceNumber-diceSides combination is created as a string
		String dice_side = String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides));

		// Storing in SIMULATION Table 
		if (simulationRepo.findByDiceSide(dice_side).isPresent()) {
			int count = simulationRepo.findByDiceSide(dice_side).get().getSimulationCount() + 1;
			simulationRepo.findByDiceSide(dice_side).get().setSimulationCount(count);
			int totalRolls = simulationRepo.findByDiceSide(dice_side).get().getTotalRolls() + noOfRolls;
			simulationRepo.findByDiceSide(dice_side).get().setTotalRolls(totalRolls);
			simulationRepo.save(simulationRepo.findByDiceSide(dice_side).get());
		}

		else {
			Simulation simulation = new Simulation();
			simulation.setDiceSide(dice_side);
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
					simulationRepo.findByDiceSide(dice_side).get().getSimulationId(), sum);
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
					distribution.setSimulationId(simulationRepo.findByDiceSide(dice_side).get().getSimulationId());
					distributionRepo.save(distribution);
					}
				
				catch (Exception e)
				{
					System.out.println(e);
				}

			}
		}

		return (HashMap<Integer, Integer>) sum_map; // returns JSON with SUM-Count pairs
	}
	
	

	/*
	 * getAllSimulationDetails Function Returns Total Simulations and Total number
	 * of rolls for all the existing diceNumber-diceSides combinations
	 */

	public ResponseEntity<String> getAllSimulationDetails() {
		
		StringBuilder result = new StringBuilder();
		result.append(("DiceNumber-DiceSides	  No-Of-Simulations   Total-Rolls"));
		result.append("\n");
		
		try
		{
			simulationRepo.findAll().stream().forEach(i -> {
			result.append(i.getDiceSide());
			result.append("                       ");
			result.append(Integer.toString(i.getSimulationCount()));
			result.append("                      ");
			result.append(Integer.toString(i.getTotalRolls()));
			result.append("\n");
		});
		
		return ResponseEntity.ok().body(result.toString());
		}
		
		catch (Exception e)
		{
			return ResponseEntity.ok().body(e.toString());
		}
	}

	
	
	
	/*
	 * getRelativeDistribution Function returns the Relative Distribution compared
	 * to Total number of rolls for a given diceNumber-diceSides combination
	 */

	public ResponseEntity<String> getRelativeDistribution(int noOfDice, int noOfSides) {

		String dice_side = String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides));

		try {
			long simulationId = simulationRepo.findByDiceSide(dice_side).get().getSimulationId();
			int totalRolls = simulationRepo.findByDiceSide(dice_side).get().getTotalRolls();

			StringBuilder result = new StringBuilder();
			result.append("Total rolls for the " + dice_side + " diceNumber-diceSides combination is " + totalRolls);
			result.append("\n\n");
			result.append(("Sum-on-Dice    Count-of-Sum    Relative-Distribution"));
			result.append("\n");
			
			distributionRepo.findBySimulationId(simulationId).stream().forEach(i -> {
				result.append(i.getSumOnDice());
				result.append("                 ");
				result.append(i.getCount());
				result.append("               ");
				float relativeDistribution = (float) ((float) i.getCount() / (float) totalRolls) * 100;
				result.append(String.format("%.02f", relativeDistribution));
				result.append("%");
				result.append("\n");
			});

			return ResponseEntity.ok().body(result.toString());
		}

		catch (Exception e)
		{
			return ResponseEntity.ok().body(e.toString());
		}

	}
	
}
