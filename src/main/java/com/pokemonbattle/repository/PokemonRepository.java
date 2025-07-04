package com.pokemonbattle.repository;

import com.pokemonbattle.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findByType(String type);
    Pokemon findByName(String name);
}