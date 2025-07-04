package com.pokemonbattle.service;

import com.pokemonbattle.model.Pokemon;
import com.pokemonbattle.model.BattleResult;
import com.pokemonbattle.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PokemonService {
    
    @Autowired
    private PokemonRepository pokemonRepository;
    
    private Random random = new Random();
    
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }
    
    public Pokemon getPokemonById(Long id) {
        return pokemonRepository.findById(id).orElse(null);
    }
    
    public Pokemon savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }
    
    public BattleResult battle(Pokemon attacker, Pokemon defender, String move) {
        if (!attacker.isAlive() || !defender.isAlive()) {
            return new BattleResult("One of the Pokemon is already defeated!", attacker, defender, 0);
        }
        
        // Simple damage calculation
        int baseDamage = calculateDamage(attacker, defender, move);
        int damage = Math.max(1, baseDamage); // Minimum 1 damage
        
        defender.takeDamage(damage);
        
        String message = String.format("%s used %s! It dealt %d damage to %s!", 
            attacker.getName(), move, damage, defender.getName());
        
        if (!defender.isAlive()) {
            message += String.format(" %s fainted! %s wins!", defender.getName(), attacker.getName());
        }
        
        return new BattleResult(message, attacker, defender, damage);
    }
    
    private int calculateDamage(Pokemon attacker, Pokemon defender, String move) {
        // Basic damage formula: (Attack / Defense) * Move Power * Random Factor
        double baseDamage = (double) attacker.getAttack() / defender.getDefense() * 20;
        double randomFactor = 0.8 + (random.nextDouble() * 0.4); // 0.8 to 1.2
        
        // Type effectiveness (simplified)
        double typeEffectiveness = getTypeEffectiveness(attacker.getType(), defender.getType());
        
        return (int) (baseDamage * randomFactor * typeEffectiveness);
    }
    
    private double getTypeEffectiveness(String attackerType, String defenderType) {
        // Simplified type effectiveness chart
        if (attackerType.equals("Fire") && defenderType.equals("Grass")) return 2.0;
        if (attackerType.equals("Water") && defenderType.equals("Fire")) return 2.0;
        if (attackerType.equals("Grass") && defenderType.equals("Water")) return 2.0;
        if (attackerType.equals("Electric") && defenderType.equals("Water")) return 2.0;
        
        if (attackerType.equals("Fire") && defenderType.equals("Water")) return 0.5;
        if (attackerType.equals("Water") && defenderType.equals("Grass")) return 0.5;
        if (attackerType.equals("Grass") && defenderType.equals("Fire")) return 0.5;
        
        return 1.0; // Normal effectiveness
    }
    
    public void initializePokemon() {
        if (pokemonRepository.count() == 0) {
            // Create some default Pokemon
            Pokemon pikachu = new Pokemon("Pikachu", 100, 55, 40, 90, "Electric");
            pikachu.getMoves().addAll(List.of("Thunderbolt", "Quick Attack", "Iron Tail", "Agility"));
            pikachu.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
            
            Pokemon charizard = new Pokemon("Charizard", 120, 84, 78, 100, "Fire");
            charizard.getMoves().addAll(List.of("Flamethrower", "Dragon Claw", "Wing Attack", "Fire Blast"));
            charizard.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png");
            
            Pokemon blastoise = new Pokemon("Blastoise", 130, 83, 100, 78, "Water");
            blastoise.getMoves().addAll(List.of("Hydro Pump", "Surf", "Ice Beam", "Skull Bash"));
            blastoise.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png");
            
            Pokemon venusaur = new Pokemon("Venusaur", 125, 82, 83, 80, "Grass");
            venusaur.getMoves().addAll(List.of("Solar Beam", "Vine Whip", "Petal Dance", "Sleep Powder"));
            venusaur.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png");
            
            pokemonRepository.saveAll(List.of(pikachu, charizard, blastoise, venusaur));
        }
    }
}