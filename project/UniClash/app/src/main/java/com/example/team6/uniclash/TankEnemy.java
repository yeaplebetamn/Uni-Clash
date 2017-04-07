package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class TankEnemy extends Enemy {
    public TankEnemy(Context context, int screenX, int screenY, Base base) {
        setHealth(200);
        setSpeed(1);
        setAttack(6);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.turkey_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        setY(100);
    }
}
