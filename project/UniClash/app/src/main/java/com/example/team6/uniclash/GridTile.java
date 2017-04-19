package com.example.team6.uniclash;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Holds x y coordinates of game map
 *
 * Rect(int left, int top, int right, int bottom)
 * Reference for Rect class:
 * left=x coordinate of left wall
 * top=y coordinate of top wall
 * right=x coordinate of right wall
 * bottom=y coordinate of bottom wall
 */

 public class GridTile{
    int x;
    int y;
    int width;
    int height;
    Rect tile;
    boolean occupied;
    Bitmap bitmap;

    public GridTile(int maxX, int maxY, int col, int row){
        this.width = maxX/16;
        this.height = maxY/9;
        this.x = col * this.width;
        this.y = row * this.height;


        this.tile = new Rect(x, y, x+width, y+height);
    }

    public Rect getGridTile(){return tile;}
    public boolean getOccupied(){return occupied;}
    public int getXCenter() {
        return (x + (width/2));
    }

    public int getYCenter() {
        return (y + (height/2));
    }


    public void setOccupied(boolean taken){
        this.occupied = taken;
    }

}
