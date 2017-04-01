package com.example.team6.uniclash;

import com.example.team6.uniclash.Base;
import android.graphics.Bitmap;

public class Enemy {
    private int health;
    private int speed;
    private int attack;
    private int x;
    private int minX;
    private int maxX;
    private int y = 75;
    private int minY;
    private int maxY;
    private Bitmap bitmap;

    short currentDirection = 4;
    short directionUp = 1;
    short directionDown = 2;
    short directionLeft = 3;
    short directionRight = 4;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void takeDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
        if (health <= 0) {
            die();
        }
    }

    public void applyDamage(Base base) {
        base.takeDamage(this.attack);
    }

    public void die() {

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void resetX(){
        x = 0;
    }

    public void resetY(){
        y = 75;
    }

    public void update() {
        if (x > maxX) { //if enemy reaches the end of the screen, send it back to other side
            resetX();
            resetY();
        }
        // once x reaches 800, go down
        if (x > 800 && y < 800){
            x = 800;
            currentDirection = directionDown;
        }
        // once y reaches 800, go right again
        if (y > 800){
            y = 800;
            currentDirection = directionRight;
        }

        //actually start to move
        if (currentDirection == directionUp){
            this.y -= this.speed * 5;
        }
        if (currentDirection == directionDown){
            this.y += this.speed * 5;
        }
        if (currentDirection == directionLeft){
            this.x -= this.speed * 5;
        }
        if (currentDirection == directionRight){
            this.x += this.speed * 5;
        }

    }
}
