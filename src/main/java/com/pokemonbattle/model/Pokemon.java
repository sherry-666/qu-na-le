package com.pokemonbattle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int hp;
    
    @Column(nullable = false)
    private int maxHp;
    
    @Column(nullable = false)
    private int attack;
    
    @Column(nullable = false)
    private int defense;
    
    @Column(nullable = false)
    private int speed;
    
    @Column(nullable = false)
    private String type;
    
    @ElementCollection
    @CollectionTable(name = "pokemon_moves", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "move_name")
    private List<String> moves = new ArrayList<>();
    
    private String imageUrl;
    
    public Pokemon() {}
    
    public Pokemon(String name, int hp, int attack, int defense, int speed, String type) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    
    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public List<String> getMoves() { return moves; }
    public void setMoves(List<String> moves) { this.moves = moves; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public boolean isAlive() {
        return hp > 0;
    }
    
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }
    
    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }
}