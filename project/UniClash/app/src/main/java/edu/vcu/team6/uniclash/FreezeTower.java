package edu.vcu.team6.uniclash;

import android.graphics.BitmapFactory;
import android.content.Context;

import com.vcu.team6.uniclash.R;

import java.util.ArrayList;

//slows enemies in range, no damage.
public class FreezeTower extends Tower {

    private double slowPercent;

    public FreezeTower(Context context, int screenX, int screenY) {
        setName("Freeze Tower");
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.freeze_tower_icon));

        slowPercent = 0.5;
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
                enemy.setSpeed(enemy.getSpeed() * this.slowPercent);
                enemy.slowed = true;
            } else if (enemy.slowed){
                enemy.setSpeed(enemy.getSpeed() / this.slowPercent);
                enemy.slowed = false;
            }
        }
    }

    @Override
    public void increaseLevel() {
        this.level++;
        //level     0   1   2   3
        //slow     0.5 0.4 0.3 0.2
        this.slowPercent -= 0.1;
    }
}
