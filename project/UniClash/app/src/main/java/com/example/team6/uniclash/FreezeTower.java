package com.example.team6.uniclash;

import android.graphics.BitmapFactory;
import android.content.Context;

import java.util.ArrayList;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {
    public FreezeTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.freeze_tower));

        setFireRate(10);
        setAttack(0);
        setAttackDelay(0);
        setRange(600);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }

    @Override
    public void attack(Enemy target, ArrayList<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            if (getRangeDetector().intersect(enemy.getCollisionDetector())) {
                //enemy = slowed
            }
        }
    }
}
