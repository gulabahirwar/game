package com.androiddoes.myapplication;

import android.graphics.Bitmap;

/**
 * Created by gulab on 16/03/2017.
 */

public class Animation {
    Bitmap [] frames;
    long delay;
    int currentFrame;
    long startTime;
    boolean oncePlayed;

    public void setFrames(Bitmap[] frames){
        this.frames=frames;
        startTime=System.nanoTime();
        currentFrame=0;
    }

    public void setDelay(long delay) {
        this.delay=delay;
    }
    public void update(){
        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>delay){
            currentFrame++;
            startTime=System.nanoTime();
        }
        if(currentFrame==frames.length){
            currentFrame=0;
            oncePlayed=true;
        }
    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public boolean isOncePlayed() {
        return oncePlayed;
    }
    public int getFrame(){
        return currentFrame;
    }
}
