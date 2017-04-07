package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class GunTower extends Tower {
    public GunTower(Context context, int x, int y) {
        setX(x);
        setY(y);
        setAttack(10);
        setAttackDelay(0.8);
        setRange(100);
        setRangeDetector(getRange());
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ram_icon));
    }
}
