package com.example.team6.uniclash;

public class Tower {
    private int attack;
    private int range;

    public int getAttack() {

        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void applyDamage(Enemy enemy) {
        enemy.takeDamage(this.attack);
    }
}
