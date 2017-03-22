package com.androiddoes.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    long startSmoke;
    long startMissile;
    private MainThread thread;
    private Background bg;
    private Player player;
    ArrayList<SmokeStuff> arrayListSmoke;
    ArrayList<Missile> arrayListMissile;
    boolean collesion;
    Random random=new Random();

    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter=0;
        while(retry&& counter<1000)
        {
            try{thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){e.printStackTrace();}
            counter++;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        arrayListSmoke=new ArrayList<>();
        arrayListMissile=new ArrayList<>();
         startSmoke=System.nanoTime();
        startMissile=System.nanoTime();


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {
        long elapsedSmoke=(System.nanoTime()-startSmoke)/1000000;
        long elapsedMissile=(System.nanoTime()-startMissile)/1000000;
        if(elapsedMissile>(2000-player.getScore()/4)){
               if(arrayListMissile.size()==0){
                   arrayListMissile.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),WIDTH+10,HEIGHT/2,42,15,player.getScore(),13));
               }else{
                   arrayListMissile.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),WIDTH+10,(int)((random.nextDouble()*(HEIGHT))),42,15,player.getScore(),13));
               }
            startMissile=System.nanoTime();
        }
        for(int i=0;i<arrayListMissile.size()&&collesion==false;i++){
            arrayListMissile.get(i).update();
            if(collision(arrayListMissile.get(i),player)){
                arrayListMissile.remove(i);
                player.setPlaying(false);
                break;
            }
            if(arrayListMissile.get(i).getX()<-50){
                arrayListMissile.remove(i);
            }
        }
        if(elapsedSmoke>120){
            arrayListSmoke.add(new SmokeStuff(player.getX(),player.getY()+10));
            startSmoke=System.nanoTime();
        }
        for (int i=0;i<arrayListSmoke.size()&&collesion==false;i++){
            arrayListSmoke.get(i).update();
            if (arrayListSmoke.get(i).getX()<-10){
                arrayListSmoke.remove(i);

            }
        }
        if(player.getPlaying()) {
            bg.update();
            player.update();
        }
    }

    private boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(),b.getRectangle())){
            collesion=true;
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            for(SmokeStuff stuff:arrayListSmoke){
                stuff.draw(canvas);
            }
            for(Missile missile:arrayListMissile){
                missile.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }


}