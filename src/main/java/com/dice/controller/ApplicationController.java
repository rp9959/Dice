package com.dice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dice.exception.DiceException;
import com.dice.service.SimulationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")

//All the REST end points are exposed from here.
public class ApplicationController {

	@Autowired
	SimulationService simulationService;
	
	@Autowired
	DiceException diceException;
	
	

	/*
	 * Returns a JSON of SUM-COUNT pairs for the given combination of diceNumber,
	 * diceSides, number of rolls.
	 */
	@GetMapping(value="/simulate" ,produces = "application/json" )
	public ResponseEntity<?> simulate(@RequestParam(value="dice", required=true) int noOfDice,
			@RequestParam(value="sides", required=true) int noOfSides,
			@RequestParam(value="rolls", required=true) int noOfRolls)  {
		
		//Validation for number of dice, sides and number of rolls
		if (noOfDice < 1 || noOfRolls < 1 || noOfSides < 4 )		
		return diceException.validateSimulation(noOfDice, noOfSides, noOfRolls);
		
		return simulationService.createSimulation(noOfDice, noOfSides, noOfRolls);
		
		
	}
	


	
	/* getAllSimulationDetails function Returns the total number of simulations and total rolls made, grouped by all
	 existing dice number–dice sides combinations */
	
	@GetMapping(value="/totaldetails",produces = "application/json" )
	public ResponseEntity<?> getAllSimulationDetails() {
		return simulationService.getAllSimulationDetails();
	}

	
	
	/*relativeDistribution function Returns the Relative Distribution, compared to the total rolls, for a given
	  dice number–dice sides combination */
	
	@GetMapping(value="/relativedistribution",produces = "application/json" )
	public ResponseEntity<?> relativeDistribution(@RequestParam(value="dice", required=true) int noOfDice, @RequestParam(value="sides", required=true) int noOfSides) {
		return simulationService.getRelativeDistribution(noOfDice, noOfSides);
	}

}
