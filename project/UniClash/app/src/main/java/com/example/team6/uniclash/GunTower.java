package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class GunTower extends Tower {
    public GunTower(Context context, int screenX, int screenY) {
        setName("Gun Tower");
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_basic_tower_icon));

        setFireRate(5);
        setAttack(10);
        setAttackDelay(0.8);
        setRange(600);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }


    @Override
    public void increaseLevel() {
        this.level++;
        //level     0   1   2   3
        //firerate  5   4   3   2
        setFireRate(this.fireRate--);

        //level     0   1   2   3
        //attack    10  12  14  16
        setAttack(this.getAttack() + 2);
    }
}
