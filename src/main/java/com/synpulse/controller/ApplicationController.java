package com.synpulse.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synpulse.service.SimulationService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class ApplicationController {
	
	@Autowired
	SimulationService simulationService;
	
	
	 @GetMapping("/simulate/{noOfDice}/{noOfSides}/{noOfRolls}")
	 public HashMap<Integer,Integer> simulate(@PathVariable int noOfDice, @PathVariable int noOfSides,@PathVariable int noOfRolls) throws Exception
	 {	 
		return  simulationService.createSimulation(noOfDice,noOfSides,noOfRolls); 
	 }
	
	@GetMapping("/totaldetails")
	 public ResponseEntity<String> getAllSimulationDetails()
	 {
		//return 
		return simulationService.getAllSimulationDetails();
	 }
	
	 @GetMapping("/relativedistribution/{noOfDice}/{noOfSides}")
	public ResponseEntity<String> relativeDistribution(@PathVariable int noOfDice, @PathVariable int noOfSides)
	{
		 return simulationService.getRelativeDistribution(noOfDice, noOfSides);
	}
	



}
