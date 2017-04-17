package com.example.team6.uniclash;

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
    int width;
    int height;
    Rect tile;
    boolean occupied;

    public GridTile(int maxX, int maxY, int x, int y){
        this.width = maxX/16;
        this.height = maxY/9;


        this.tile = new Rect(x*width, y*height, (x*width)+width, (y*height)+height);
    }

    public Rect getGridTile(){return tile;}
    public boolean getOccupied(){return occupied;}

    public void setOccupied(boolean taken){
        this.occupied = taken;
    }

}
