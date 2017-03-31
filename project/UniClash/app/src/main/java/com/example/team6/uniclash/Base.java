package com.example.team6.uniclash;

public class Base {
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
    }

    public void heal(int amount) {
        this.setHealth(this.getHealth() + amount);
    }
}
