package edu.vcu.team6.uniclash;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class Tower {
    private int attack;
    private double attackDelay;
    private int range;
    private int x;
    private int y;
    public int frameTimer = 0;
    public int fireRate;
    private Bitmap bitmap;
    private Rect rangeDetector;
    private Enemy target;
    private boolean hasTarget;
    public int level;
    private String name;
    private int cost;

    public int getLevel(){return level;   }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void applyDamage(Enemy enemy) {
        enemy.takeDamage(this.attack);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLevel(int level){this.level = level;}

    public void increaseLevel() {
        this.level++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFireRate(int fireRate){
        this.fireRate = fireRate;
    }

    public double getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(double attackDelay) {
        this.attackDelay = attackDelay;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getRangeDetector() {
        return rangeDetector;
    }

    public void setRangeDetector(int range) {
        int rangeDetectorLeft = this.x - (range/2);
        int rangeDetectorTop = this.y - (range/2);
        int rangeDetectorRight = this.x + (range/2);
        int rangeDetectorBottom = this.y + (range/2);
        
        rangeDetector = new Rect(rangeDetectorLeft, rangeDetectorTop, rangeDetectorRight, rangeDetectorBottom);
    }
    
    public void update(ArrayList<Enemy> enemies) {
        frameTimer++;
        if (hasTarget && frameTimer > fireRate) {
            if (Rect.intersects(getRangeDetector(), target.getCollisionDetector())) {
                attack(target, enemies);
                frameTimer = 0;
            } else {
                target = null;
                hasTarget = false;
                frameTimer++;
            }
        } else {
            for (Enemy enemy : enemies) {
                if (Rect.intersects(getRangeDetector(), enemy.getCollisionDetector())) {
                    if (enemy.inBounds() && !enemy.waiting) {
                        target = enemy;
                        hasTarget = true;
                        frameTimer++;
                        break;
                    }
                }
            }
        }
    }

    //to be overridden by certain subclasses
    public void attack(Enemy target, ArrayList<Enemy> enemies) {
        target.takeDamage(getAttack());
    }


    @Override
    public String toString(){
        return name + ", level " + level;
    }
}
