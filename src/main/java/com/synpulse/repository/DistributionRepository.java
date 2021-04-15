package com.synpulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synpulse.model.Distribution;


public interface DistributionRepository extends JpaRepository<Distribution, Long>{
	 List<Distribution> findBySimulationId(Long simulationId) ;
	List<Distribution> findBySimulationIdAndSumOnDice(Long simulationId, int sumOnDice);


}
