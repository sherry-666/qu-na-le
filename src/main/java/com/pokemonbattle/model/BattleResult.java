package com.pokemonbattle.model;

public class BattleResult {
    private String message;
    private Pokemon attacker;
    private Pokemon defender;
    private int damage;
    private boolean battleEnded;
    private String winner;
    
    public BattleResult() {}
    
    public BattleResult(String message, Pokemon attacker, Pokemon defender, int damage) {
        this.message = message;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.battleEnded = !defender.isAlive();
        if (battleEnded) {
            this.winner = attacker.getName();
        }
    }
    
    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Pokemon getAttacker() { return attacker; }
    public void setAttacker(Pokemon attacker) { this.attacker = attacker; }
    
    public Pokemon getDefender() { return defender; }
    public void setDefender(Pokemon defender) { this.defender = defender; }
    
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    
    public boolean isBattleEnded() { return battleEnded; }
    public void setBattleEnded(boolean battleEnded) { this.battleEnded = battleEnded; }
    
    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }
}