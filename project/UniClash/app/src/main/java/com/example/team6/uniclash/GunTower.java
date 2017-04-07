package com.example.team6.uniclash;

import android.graphics.Rect;

public class GunTower extends Tower {
    public GunTower() {
        setAttack(10);
        setAttackDelay(0.8);
        setRange(100);
        setRangeDetector(getRange());
    }
}
