package com.dice.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dice.service.SimulationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")

//All the REST end points are exposed from here.
public class ApplicationController {

	@Autowired
	SimulationService simulationService;
	
	

	/*
	 * Returns a JSON of SUM-COUNT pairs for the given combination of diceNumber,
	 * diceSides, number of rolls.
	 */
	@GetMapping("/simulate/{noOfDice}/{noOfSides}/{noOfRolls}")
	public HashMap<Integer, Integer> simulate(@PathVariable int noOfDice, @PathVariable int noOfSides,
			@PathVariable int noOfRolls) throws Exception {
		
		//Validation for number of dice, sides and number of rolls
		if (noOfDice < 1 || noOfRolls < 1 || noOfSides < 4)
			throw new 
			RuntimeException("Please Ensure- Minimum number of dice and rolls is 1 and minimum sides of a dice is 4");
		
		return simulationService.createSimulation(noOfDice, noOfSides, noOfRolls);
	}
	
	

	
	/* getAllSimulationDetails function Returns the total number of simulations and total rolls made, grouped by all
	 existing dice number–dice sides combinations */
	
	@GetMapping("/totaldetails")
	public ResponseEntity<String> getAllSimulationDetails() {
		return simulationService.getAllSimulationDetails();
	}

	
	
	/*relativeDistribution function Returns the Relative Distribution, compared to the total rolls, for a given
	  dice number–dice sides combination */
	
	@GetMapping("/relativedistribution/{noOfDice}/{noOfSides}")
	public ResponseEntity<String> relativeDistribution(@PathVariable int noOfDice, @PathVariable int noOfSides) {
		return simulationService.getRelativeDistribution(noOfDice, noOfSides);
	}

}
