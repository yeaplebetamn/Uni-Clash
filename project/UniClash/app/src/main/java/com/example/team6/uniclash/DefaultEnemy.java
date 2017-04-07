package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class DefaultEnemy extends Enemy {
    public DefaultEnemy(Context context, int screenX, int screenY, Base base) {
        setHealth(100);
        setSpeed(2);
        setAttack(5);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ram_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        setY(50);
    }
}
