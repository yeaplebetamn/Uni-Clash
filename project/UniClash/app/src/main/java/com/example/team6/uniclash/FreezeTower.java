package com.example.team6.uniclash;

import java.util.ArrayList;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {
    public FreezeTower() {
        setAttack(0);
        setAttackDelay(0);
        setRange(100);
        setRangeDetector(getRange());
    }

    @Override
    public void attack(Enemy target, ArrayList<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            if (getRangeDetector().intersect(enemy.getCollisionDetector())) {
                //enemy = slowed
            }
        }
    }
}
