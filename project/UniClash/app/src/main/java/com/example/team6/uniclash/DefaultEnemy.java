package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class DefaultEnemy extends Enemy {
    public DefaultEnemy(Context context, int screenX, int screenY) {
        setHealth(100);
        setSpeed(2);
        setAttack(3);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ram_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        setX(75);
        setY(50);
    }
}
