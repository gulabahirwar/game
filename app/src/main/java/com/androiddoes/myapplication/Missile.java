package com.androiddoes.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

/**
 * Created by gulab on 18/03/2017.
 */

public class Missile extends GameObject {
    int speed;
    int score;
    Bitmap spriteSheet;
    Random rand=new Random();
    Animation animation=new Animation();

    public  Missile(Bitmap res,int x,int y, int w,int h,int s, int frame){
         super.x=x;
         super.y=y;
         height=h;
          width=w;
        score=s;
        spriteSheet=res;

        speed=7+(int)(rand.nextDouble()*score/30);
        Log.e("speed1",""+speed);
        if(speed>40)speed=40;
        Log.e("speed2",""+speed);
        Bitmap[] image=new Bitmap[frame];
         for (int i=0;i<image.length;i++){
             image[i]=Bitmap.createBitmap(res,0,i*h,w,h);
         }
        animation.setFrames(image);
        animation.setDelay(100-speed);
    }
    public void update(){
       x-=speed;
        Log.e("speed3",""+speed);
        animation.update();
    }
    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch (Exception e){
            Log.e("Missile",""+e);
        }
    }

    @Override
    public int getWidth() {
        return width-10;
    }
}
