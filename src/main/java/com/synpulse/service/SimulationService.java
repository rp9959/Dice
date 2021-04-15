package com.synpulse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.synpulse.model.Distribution;
import com.synpulse.model.Simulation;
import com.synpulse.repository.DistributionRepository;
import com.synpulse.repository.SimulationRepository;

@Service
public class SimulationService {


	
	@Autowired
	SimulationRepository simulationRepo;
	
	@Autowired
	DistributionRepository distributionRepo;
	
	
	//Function to create a simulation and store all related details
	@Transactional
    public HashMap<Integer, Integer> createSimulation(int noOfDice,int noOfSides,int noOfRolls) throws Exception {
		
		if(noOfDice<1 || noOfRolls<1||noOfSides<4)
			 throw new RuntimeException("Please Ensure- Minimum number of dice and rolls is 1 and minimum sides of a dice is 4");

    	
    	String dice_side= String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides)) ;

    	//simulation table part
		 if(simulationRepo.findByDiceSide(dice_side).isPresent())
			 { 
				 int count=simulationRepo.findByDiceSide(dice_side).get().getSimulationCount() +1;
				 simulationRepo.findByDiceSide(dice_side).get().setSimulationCount(count);
				 int totalRolls=simulationRepo.findByDiceSide(dice_side).get().getTotalRolls() +noOfRolls ;
				 simulationRepo.findByDiceSide(dice_side).get().setTotalRolls(totalRolls);
				 simulationRepo.save(simulationRepo.findByDiceSide(dice_side).get());				 
			 }
		 
		 else
		 {
			 Simulation simulation= new Simulation();
			 simulation.setDiceSide(dice_side);
			 simulation.setTotalRolls(noOfRolls);
			 simulation.setSimulationCount(1); 
			 simulationRepo.save(simulation);	 
		 }
		 
		 
	 Map<Integer, Integer> sum_map= new HashMap<Integer, Integer>();
	
	//code to return JSON 
	 for(int i=0;i<noOfRolls;i++)
	 {
		 ArrayList<Integer> dice = new ArrayList<Integer>();

		 for(int j=0;j<noOfDice;j++)
		 {
			 dice.add((int)(Math.random()*noOfSides) + 1);
		 }
		 
		 int sum= dice.stream().mapToInt(Integer::intValue).sum() ;
		 
		 
		 if(sum_map.containsKey(sum))
				{
					sum_map.put(sum, sum_map.get(sum)+1);
				}
				else
				{
					sum_map.put(sum, 1);
				}
			
		 //Storing in distribution table
		 List<Distribution> distributionList=distributionRepo.findBySimulationIdAndSumOnDice(simulationRepo.findByDiceSide(dice_side).get().getSimulationId(), sum);
		 if(!distributionList.isEmpty() )
		 {
			 int newCount=distributionList.get(0).getCount();
			 distributionList.get(0).setCount(newCount+1);
			 distributionRepo.save(distributionList.get(0));		 
		 }
		 
		 else
		 {
			 Distribution distribution = new Distribution();
			 distribution.setSumOnDice(sum);
				distribution.setCount(1);
				try{
				distribution.setSimulationId(simulationRepo.findByDiceSide(dice_side).get().getSimulationId());
				distributionRepo.save(distribution);
				}
				catch(Exception e) {System.out.println(e);}
			 
		 }	 			
		}
				 	 
	return (HashMap<Integer, Integer>) sum_map;  //returns JSON with SUM-Count pairs
 }

	
	
	
		
	//Function - Returns Total Simulations and Total rolls for all existing diceNumber-diceSide combinations	
	public ResponseEntity<String> getAllSimulationDetails()
	{
		StringBuilder result = new StringBuilder();
		result.append(("DiceNumber-DiceSides	  No-Of-Simulations   Total-Rolls"));
		result.append("\n");
		simulationRepo.findAll().stream().forEach(i-> 
		{
		result.append(i.getDiceSide());
		result.append("                       ");
		result.append(Integer.toString(i.getSimulationCount()));
		result.append("                      ");
		result.append(Integer.toString(i.getTotalRolls()));
		result.append("\n");
		}
		);		      
		return ResponseEntity.ok().body(result.toString());
	
	}
	
	
	
	//Function-gives relative distribution for a given diceNumber-diceSides combination
	public ResponseEntity<String> getRelativeDistribution(int noOfDice, int noOfSides)
	{

		String dice_side= String.valueOf(noOfDice).concat("-").concat(String.valueOf(noOfSides)) ;
		try{
			long simulationId= simulationRepo.findByDiceSide(dice_side).get().getSimulationId();
		int totalRolls=simulationRepo.findByDiceSide(dice_side).get().getTotalRolls();
		

		
		StringBuilder result = new StringBuilder();
		result.append("Total rolls for the " +dice_side+" diceNumber-diceSides combination is "+ totalRolls);
		result.append("\n\n");
		result.append(("Sum-on-Dice    Count-of-Sum    Relative-Distribution"));
		result.append("\n");	
		distributionRepo.findBySimulationId(simulationId).stream().forEach(i->		{
		result.append(i.getSumOnDice());
		result.append("                 ");
		result.append(i.getCount());
		result.append("               ");
		float relativeDistribution=(float)((float)i.getCount()/(float)totalRolls)*100;
		result.append(String.format("%.02f", relativeDistribution));
		result.append("%");
		result.append("\n");
		}
		);		      
		
		return ResponseEntity.ok().body(result.toString());

}
		
		catch(Exception e) {return ResponseEntity.ok().body(e.toString());}
		
		
	}
	
}
