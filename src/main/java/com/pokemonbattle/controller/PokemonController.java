package com.pokemonbattle.controller;

import com.pokemonbattle.model.Pokemon;
import com.pokemonbattle.model.BattleResult;
import com.pokemonbattle.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PokemonController {
    
    @Autowired
    private PokemonService pokemonService;
    
    @GetMapping("/")
    public String home(Model model) {
        pokemonService.initializePokemon();
        List<Pokemon> pokemon = pokemonService.getAllPokemon();
        model.addAttribute("pokemon", pokemon);
        return "index";
    }
    
    @GetMapping("/battle")
    public String battlePage(@RequestParam Long pokemon1Id, @RequestParam Long pokemon2Id, Model model) {
        Pokemon pokemon1 = pokemonService.getPokemonById(pokemon1Id);
        Pokemon pokemon2 = pokemonService.getPokemonById(pokemon2Id);
        
        if (pokemon1 == null || pokemon2 == null) {
            return "redirect:/";
        }
        
        // Reset HP for battle
        pokemon1.setHp(pokemon1.getMaxHp());
        pokemon2.setHp(pokemon2.getMaxHp());
        pokemonService.savePokemon(pokemon1);
        pokemonService.savePokemon(pokemon2);
        
        model.addAttribute("pokemon1", pokemon1);
        model.addAttribute("pokemon2", pokemon2);
        
        return "battle";
    }
    
    @PostMapping("/api/battle")
    @ResponseBody
    public BattleResult performBattle(@RequestParam Long attackerId, 
                                     @RequestParam Long defenderId, 
                                     @RequestParam String move) {
        Pokemon attacker = pokemonService.getPokemonById(attackerId);
        Pokemon defender = pokemonService.getPokemonById(defenderId);
        
        if (attacker == null || defender == null) {
            return new BattleResult("Pokemon not found!", null, null, 0);
        }
        
        BattleResult result = pokemonService.battle(attacker, defender, move);
        
        // Save the updated Pokemon
        pokemonService.savePokemon(defender);
        
        return result;
    }
    
    @GetMapping("/api/pokemon/{id}")
    @ResponseBody
    public Pokemon getPokemon(@PathVariable Long id) {
        return pokemonService.getPokemonById(id);
    }
    
    @PostMapping("/api/pokemon/{id}/heal")
    @ResponseBody
    public Pokemon healPokemon(@PathVariable Long id) {
        Pokemon pokemon = pokemonService.getPokemonById(id);
        if (pokemon != null) {
            pokemon.setHp(pokemon.getMaxHp());
            pokemonService.savePokemon(pokemon);
        }
        return pokemon;
    }
}