package com.example.team6.uniclash;

import android.graphics.Bitmap;

public class Tower {
    private int attack;
    private double attackDelay;
    private int range;
    private int x;
    private int y;
    private Bitmap bitmap;



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
}
