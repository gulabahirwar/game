package com.androiddoes.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by gulab on 16/03/2017.
 */
public class Background {
    Bitmap image;
    int x,y,dx;
    GamePanel gamePanel;

    public Background(Bitmap res){
        image=res;
        dx=GamePanel.MOVESPEED;
    }
    public  void draw(Canvas canvas){
          canvas.drawBitmap(image,x,y,null);
        if(x<0){
            canvas.drawBitmap(image,x+GamePanel.WIDTH,y,null);
        }
    }
    public void update(){
         x+=dx;
        if(x<-gamePanel.WIDTH){
            x=0;
        }
    }


}
