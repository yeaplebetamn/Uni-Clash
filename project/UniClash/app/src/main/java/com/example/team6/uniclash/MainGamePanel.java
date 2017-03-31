package com.example.team6.uniclash;

/**
 * handles events on screen
 */

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private MainThread thread;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        //create game loop
        thread = new MainThread();

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);    //htread has started
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //shuts down thread and waits for it to finish
        //clean shutdown
        boolean retry = true;
        while(retry){
            try{
                thread.join();
                retry=false;
            }catch(InterruptedException e){
                //try again to shut down thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}