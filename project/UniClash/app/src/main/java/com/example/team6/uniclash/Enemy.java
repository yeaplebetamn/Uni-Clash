package com.example.team6.uniclash;

import com.example.team6.uniclash.Base;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

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
    public boolean dead = false;
    boolean turn[] = new boolean[10];
    private Rect collisionDetector;

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
        dead = true;
        setX(0);
        setY(0);
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

    public Rect getCollisionDetector() {
        return collisionDetector;
    }

    public void setCollisionDetector() {
        int collisionDetectorLeft = x;
        int collisionDetectorTop = y;
        int collisionDetectorRight = x + bitmap.getWidth();
        int collisionDetectorBottom = y + bitmap.getHeight();

        collisionDetector = new Rect(collisionDetectorLeft, collisionDetectorTop, collisionDetectorRight, collisionDetectorBottom);
    }

    // moves the enemy across the map, following a path and dying after hitting the base
    public void followPath1() {
        if(dead == false) {
            if (getX() >= 2200) { //TODO: change hard coded x to base's x.
                die();
            }
            // once x reaches 800, go down
            if (getX() > 800 && getY() < 800) {
                setX(800);
                currentDirection = directionDown;
            }
            // once y reaches 800, go right again
            if (getY() > 800) {
                setY(800);
                currentDirection = directionRight;
            }

            //actually start to move
            if (currentDirection == directionUp){
                this.y -= this.speed * 5;
            }
            if (currentDirection == directionDown) {
                this.y += this.speed * 5;
            }
            if (currentDirection == directionLeft) {
                this.x -= this.speed * 5;
            }
            if (currentDirection == directionRight) {
                this.x += this.speed * 5;
            }
        }
    }

    public void followPath2(){
        turn[0] = true;
        if(!dead) {
            if (getX() >= 2200) { //TODO: change hard coded x to base's x.
                die();
            }
            // once x reaches 800 and before y reaches 800, go down
            if (turn[0]) {
                if(getX() > 800){
                    turn[0] = false;
                    turn[1] = true;
                    setX(800);
                    currentDirection = directionDown;
                }
            }

            // once y reaches 400, go right again until x reaches 1500
            if (turn[1]) {
                if(getY() > 400){
                    turn[1] = false;
                    turn[2] = true;
                    setY(400);
                    currentDirection = directionRight;
                }
            }

            // once x reaches 1500, go up until y reaches 200
            if (turn[2]) {
                if(getX() > 1500){
                    turn[2] = false;
                    turn[3] = true;
                    setX(1500);
                    currentDirection = directionUp;
                }
            }

            // once y reaches 200, go right until x = 1800
            if (turn[3]) {
                if(getY() < 200){
                    turn[3] = false;
                    turn[4] = true;
                    setY(200);
                    currentDirection = directionRight;
                }
            }

            //once x reaches 1800, go down until y = 800
            if(turn[4]){
                if(getX() > 1800){
                    turn[4] = false;
                    turn[5] = true;
                    setX(1800);
                    currentDirection = directionDown;
                }
            }

            //once y reaches 800, go right until hitting the base
            if(turn[5]){
                if(getY() > 800){
                    turn[5] = false;
                    turn[6] = true;
                    setY(800);
                    currentDirection = directionRight;
                }
            }


            //actually start to move
            if (currentDirection == directionUp){
                this.y -= this.speed * 5;
            }
            if (currentDirection == directionDown) {
                this.y += this.speed * 5;
            }
            if (currentDirection == directionLeft) {
                this.x -= this.speed * 5;
            }
            if (currentDirection == directionRight) {
                this.x += this.speed * 5;
            }
        }
    }


    public void update() {
        followPath1();
    }

}
