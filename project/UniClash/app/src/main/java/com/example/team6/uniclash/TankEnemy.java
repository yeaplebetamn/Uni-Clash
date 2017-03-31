package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class TankEnemy extends Enemy {
    public TankEnemy(Context context, int screenX, int screenY) {
        setHealth(200);
        setSpeed(1);
        setAttack(6);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.richmond_spiders));
        setMaxX(screenX);
        setMaxY(screenY);
        setX(75);
        setY(100);
    }
}
