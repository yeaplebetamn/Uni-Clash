package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class TankEnemy extends Enemy {
    public TankEnemy(Context context, int screenX, int screenY, Base base, ArrayList<GridTile> path) {
        this.type = 1;
        setHealth(200);
        setSpeed(1);
        setAttack(7);
        setBase(base);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.turkey_icon));
        setMaxX(screenX);
        setMaxY(screenY);
        this.path = path;
        setX(path.get(0).getXCenter() - path.get(0).width);
        setY(path.get(0).getYCenter());
        setCollisionDetector();
    }
}
