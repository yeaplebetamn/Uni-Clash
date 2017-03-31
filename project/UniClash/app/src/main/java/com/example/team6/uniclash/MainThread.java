package com.example.team6.uniclash;

/**
 * Thread for game loop
 * source: https://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
 */

public class MainThread extends Thread{
    private boolean running;

    //flag holds game state
    public void setRunning(boolean playing){
        this.running=playing;
    }

    @Override
    public void run(){
        while(running){
            //update game state
            //render state to screen
        }
    }

}
