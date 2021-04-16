package com.dice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice.model.Simulation;

//Repository that assists in storing Simulation Table.
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

	Optional<Simulation> findByDiceNumberDiceSides(String diceSide);

}
