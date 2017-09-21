package edu.vcu.team6.uniclash;


import android.content.Context;
import android.graphics.BitmapFactory;

import com.vcu.team6.uniclash.R;


public class SniperTower extends Tower {
    public SniperTower(Context context, int screenX, int screenY) {
        setName("Sniper Tower");
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sniper_tower_icon));

        setFireRate(12);
        setAttack(60);
        setAttackDelay(1.5);
        setRange(1000);
        this.setX(screenX);
        this.setY(screenY);
        setRangeDetector(getRange());
    }

    @Override
    public void increaseLevel() {
        this.level++;
        //level     0   1   2   3
        //attack    60  75  90  100
        setAttack(getAttack() + 15);

        //level     0       1       2       3
        //range     1000    1100    1200    1300
        setRange(getRange() + 100);
        setRangeDetector(getRange());
    }
}
