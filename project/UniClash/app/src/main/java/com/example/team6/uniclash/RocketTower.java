package com.example.team6.uniclash;

//area of effect attack (hits multiple enemies at once)
public class RocketTower extends Tower {
    public RocketTower() {
        setAttack(20);
        setAttackDelay(1.2);
        setRange(150);
    }
}
