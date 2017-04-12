package com.example.team6.uniclash;

import android.graphics.BitmapFactory;
import android.content.Context;

import java.util.ArrayList;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {

    private double slowPercent = 0.5;

    public FreezeTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.freeze_tower));

        setFireRate(0);
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
            if (getRangeDetector().intersects(getRangeDetector(), enemy.getCollisionDetector()) && !enemy.slowed) {
                enemy.setSpeed(enemy.getSpeed() * 0.1);
                enemy.slowed = true;
            } else if (enemy.slowed){
                enemy.setSpeed(enemy.getSpeed() / 0.1);
                enemy.slowed = false;
            }
        }
    }
}
