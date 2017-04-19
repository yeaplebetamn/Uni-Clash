package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class GunTower extends Tower {
    public GunTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_basic_tower_icon));

        setFireRate(3);
        setAttack(10);
        setAttackDelay(0.8);
        setRange(600);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }
}
