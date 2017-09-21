package edu.vcu.team6.uniclash;


import android.content.Context;
import android.graphics.BitmapFactory;

import android.graphics.Rect;

import com.vcu.team6.uniclash.R;

import java.util.ArrayList;

//area of effect attack (hits multiple enemies at once)
public class RocketTower extends Tower {
    private int aoeRange;

    public RocketTower(Context context, int screenX, int screenY) {
        setName("Rocket Tower");
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_tower_icon));

        aoeRange = 150;
        setFireRate(15);
        setAttack(20);
        setAttackDelay(1.2);
        setRange(600);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }

    @Override
    public void attack(Enemy target, ArrayList<Enemy> enemies) {
        Rect aoe = new Rect(target.getX()-aoeRange, target.getY()-aoeRange, target.getX()+aoeRange, target.getY()+aoeRange);
        ArrayList<Enemy> nearbyEnemies = new ArrayList();
        for (Enemy enemy: enemies) {
            if (Rect.intersects(aoe, enemy.getCollisionDetector())) {
                nearbyEnemies.add(enemy);
            }
        }

        for (Enemy nearbyEnemy: nearbyEnemies) {
            nearbyEnemy.takeDamage(getAttack());
        }
    }


    @Override
    public void increaseLevel() {
        this.level++;
        //level     0   1   2   3
        //aoeRange 150 200 250 300
        aoeRange += 50;

        //level     0   1   2   3
        //attack    20  24  28  32
        setAttack(this.getAttack() + 4);
    }
}
