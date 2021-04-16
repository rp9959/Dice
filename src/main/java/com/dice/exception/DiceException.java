package com.dice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

//Required Validations will be done here
@ControllerAdvice
public class DiceException {
	
	public ResponseEntity<String> validateSimulation(int noOfDice, int noOfSides, int noOfRolls){
	
	if (noOfDice < 1 || noOfRolls < 1 || noOfSides < 4)		
		return new ResponseEntity<String>("Enter minimum dice, sides, rolls!", HttpStatus.BAD_REQUEST);
		
	else
		return null;

}
	
}
