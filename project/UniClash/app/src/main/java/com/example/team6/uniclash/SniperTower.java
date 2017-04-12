package com.example.team6.uniclash;


import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;


public class SniperTower extends Tower {
    public SniperTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sniper_tower));

        setFireRate(15);
        setAttack(60);
        setAttackDelay(1.5);
        setRange(1000);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }
}
