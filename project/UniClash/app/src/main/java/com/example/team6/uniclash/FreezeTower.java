package com.example.team6.uniclash;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {
    public FreezeTower() {
        setAttack(0);
        setAttackDelay(0);
        setRange(100);
        setRangeDetector(getRange());
    }
}
