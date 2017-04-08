package com.example.team6.uniclash;

import android.graphics.BitmapFactory;
import android.content.Context;

import java.util.ArrayList;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {
    public FreezeTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.freeze_tower));

        setAttack(0);
        setAttackDelay(0);
        setRange(100);
        setRangeDetector(getRange());

        this.setX(screenX);
        this.setY(screenY);
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
