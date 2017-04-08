package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

//area of effect attack (hits multiple enemies at once)
public class RocketTower extends Tower {
    public RocketTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_tower));

        setAttack(20);
        setAttackDelay(1.2);
        setRange(150);
        setRangeDetector(getRange());

        this.setX(screenX);
        this.setY(screenY);
    }
}
