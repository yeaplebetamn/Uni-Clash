package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DefaultEnemy extends Enemy {

    public DefaultEnemy(Context context, int screenX, int screenY, Base base, ArrayList<GridTile> path, int waveNum) {
        this.type = 2;
        setHealth(100 + (5 * waveNum));
        setSpeed(2 + (0.02 * waveNum));
        setAttack(5);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ram_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        this.path = path;
        setX(path.get(0).getXCenter() - path.get(0).width);
        setY(path.get(0).getYCenter());
        setCollisionDetector();
    }
}
