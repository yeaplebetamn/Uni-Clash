package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class FastEnemy extends Enemy {
    public FastEnemy(Context context, int screenX, int screenY, Base base) {
        setHealth(50);
        setSpeed(3);
        setAttack(3);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.richmond_spiders));
        setMaxX(screenX);
        setMaxY(screenY);
        setY(150);
    }
}
