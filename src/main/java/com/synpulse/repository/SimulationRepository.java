package com.synpulse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synpulse.model.Simulation;

public interface SimulationRepository  extends JpaRepository<Simulation, Long>{
	
	 Optional<Simulation> findByDiceSide(String diceSide);

	
}



