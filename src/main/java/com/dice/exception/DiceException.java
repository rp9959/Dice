package com.dice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

//Required Validations will be done here
@ControllerAdvice
public class DiceException {
	
	public ResponseEntity<String> validateSimulation(int noOfDice, int noOfSides, int noOfRolls){
	
		return new ResponseEntity<String>("Minimum number of dice must be 1, minimum number of sides must be 4, minimum number of rolls msut be 1!", HttpStatus.BAD_REQUEST);
		
}
	
}
