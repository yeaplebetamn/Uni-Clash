package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class SniperTower extends Tower {
    public SniperTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sniper_tower));

        setAttack(60);
        setAttackDelay(1.5);
        setRange(300);
        setRangeDetector(getRange());

        this.setX(screenX);
        this.setY(screenY);
    }
}
