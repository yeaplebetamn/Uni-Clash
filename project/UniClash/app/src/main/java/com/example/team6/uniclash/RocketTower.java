package com.example.team6.uniclash;


import android.content.Context;
import android.graphics.BitmapFactory;

import android.graphics.Rect;

import java.util.ArrayList;

//area of effect attack (hits multiple enemies at once)
public class RocketTower extends Tower {
    public RocketTower(Context context, int screenX, int screenY) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_tower));

        setAttack(20);
        setAttackDelay(1.2);
        setRange(150);
        setRangeDetector(getRange());

        this.setX(screenX);
        this.setY(screenY);
    }

    @Override
    public void attack(Enemy target, ArrayList<Enemy> enemies) {
        Rect aoe = new Rect(target.getX()-100, target.getY()-100, target.getX()+100, target.getY()+100);
        ArrayList<Enemy> nearbyEnemies = new ArrayList();
        for (Enemy enemy: enemies) {
            if (aoe.intersect(enemy.getCollisionDetector())) {
                nearbyEnemies.add(enemy);
            }
        }

        for (Enemy nearbyEnemy: nearbyEnemies) {
            nearbyEnemy.takeDamage(getAttack());
        }

    }
}
