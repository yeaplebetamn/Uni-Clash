package com.example.team6.uniclash;

public class SniperTower extends Tower {
    public SniperTower() {
        setAttack(60);
        setAttackDelay(1.5);
        setRange(300);
        setRangeDetector(getRange());
    }
}
