package com.androiddoes.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by gulab on 17/03/2017.
 */

public class SmokeStuff extends GameObject {
  int r;
    public SmokeStuff(int x, int y){
        r=5;
      super.x=x;
      super.y=y;
    }
    public void update(){
       x-=10;

    }
    public void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x-r,y-r,r,paint);
        canvas.drawCircle(x-r+2,y-r-2,r,paint);
        canvas.drawCircle(x-r+4,y-r-4,r,paint);
    }
}
