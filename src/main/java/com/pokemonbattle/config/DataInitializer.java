package com.pokemonbattle.config;

import com.pokemonbattle.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PokemonService pokemonService;

    @Override
    public void run(String... args) throws Exception {
        pokemonService.initializePokemon();
    }
}