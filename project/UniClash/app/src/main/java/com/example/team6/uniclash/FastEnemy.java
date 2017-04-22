package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class FastEnemy extends Enemy {
    public FastEnemy(Context context, int screenX, int screenY, Base base, ArrayList<GridTile> path, int waveNum) {
        this.type = 3;
        setHealth(50 + (2 * waveNum));
        setSpeed(2.5 + (0.05 * waveNum));
        setAttack(3);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spider_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        this.path = path;
        setX(path.get(0).getXCenter() - path.get(0).width);
        setY(path.get(0).getYCenter());
        setCollisionDetector();
    }
}
