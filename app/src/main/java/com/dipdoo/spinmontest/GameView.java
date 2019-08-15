package com.dipdoo.spinmontest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AsyncPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    SurfaceHolder holder;

    int width,height;

    GameThread gameThread;

    public GameView(Context context) {
        super(context);

        this.context = context;
        holder = getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        this.width = getWidth();
        this.height = getHeight();

        if(gameThread == null){
            gameThread = new GameThread();
            gameThread.start();
        }else{

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    //게임종료 기능메소드
    void stopGame(){
        gameThread.stopThread();
    }

    //게임일시정지 기능 메소드
    void pauseGame(){
        gameThread.pauseThread();
    }

    //게임이어하기 기능 메소드
    void resumeGame(){
        gameThread.resumeThread();
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                gameThread.touchDown(x,y);
                break;
            case MotionEvent.ACTION_UP:
                gameThread.touchUp(x,y);
                break;

            case MotionEvent.ACTION_MOVE:
                gameThread.touchMode(x.y);
                break;
        }
        return true;
    }

    class GameThread extends Thread{

        boolean isRun = true;
        boolean isWait = false;

        Bitmap imgBack;
        Bitmap[] imgMissile= new Bitmap[3];
        Bitmap[][] imgPlayer= new Bitmap[3][4];
        Bitmap[][] imgEnemy= new Bitmap[3][4];
        Bitmap[][] imgGauge= new Bitmap[2][];
        Bitmap imgJoypad;
        Bitmap[] imgDust = new Bitmap[6];
        Bitmap[] imgItem = new Bitmap[7];
        Bitmap imgProtect;
        Bitmap imgStrong;
        Bitmap imgBtnBomb;

        boolean isBomb = false;
        Rect rectBomb;

        int protectRad;
        int protectAngle = 0;

        int jpx,jpy;
        int jpr;
        boolean isJoyPad = false;

        Paint alphaPaint = new Paint();
        int backPos = 0;

        Player me;
        int playerKind = 0;

        Random rnd = new Random();

        ArrayList<Missile> missiles= new ArrayList<>();
        ArrayList<Enemy> enemies= new ArrayList<>();
        ArrayList<Dust> dusts= new ArrayList<>();
        ArrayList<Item> items= new ArrayList<>();

        int missileGap = 3;
        int level = 1;
        int fastItem = 0;
        int protectItem=0;
        int magnetItem=0;
        int strongItem=0;

        int score = 0;
        int coin = 0;
        int bomb = 3;

        void init(){

            makeBitmaps();

            me = new Player(width,height,imgPlayer,playerKind);

            jpx = jpr;
            jpy = height - jpr;

            setTextUI();
        }

        void setTextUI(){

            post(new Runnable() {
                @Override
                public void run() {
                    GameActivity ga = (GameActivity)context;

                    String s = null;

                    s= String.format("%07d", score);
                    ga.tvScore.setText(s);

                    s= String.format("%04d", coin);
                    ga.tvCoin.setText(s);

                    s= String.format("%04d", G.gem);
                    ga.tvGem.setText(s);

                    s= String.format("%04d", bomb);
                    ga.tvBomb.setText(s);

                    s= String.format("%07d", G.champion);
                    ga.tvChampion.setText(s);

                }
            });
        }

        void makeBitmaps(){
            Resources res = context.getResources();
            int size = 0;

            size = height/5;
            imgBtnBomb = BitmapFactory.decodeResource(res,R.drawable.btn_bomb);
            imgBtnBomb = Bitmap.createScaledBitmap(imgBtnBomb,size,size,true);

            rectBomb = new Rect(width-size, height -size,width,height);

            size = height/10;
            imgStrong = BitmapFactory.decodeResource(res,R.drawable.bullet_04);
            imgStrong = Bitmap.createScaledBitmap(imgStrong,size,size,true);


            size= height/16;
            for(int i=0; i<imgItem.length; i++){
                imgItem[i]= BitmapFactory.decodeResource(res, R.drawable.item_0_coin+i);
                imgItem[i]= Bitmap.createScaledBitmap(imgItem[i], size, size, true);
            }

            Bitmap img = BitmapFactory.decodeResource(res,R.drawable.dust);
            float[] ratio = new float[]{1.0f, 1.4f, 1.7f, 0.7f, 0.4f, 2.0f};
            for (int i =0; i<6; i++){
                size = (int)(height/9 * ratio[i]);
                imgDust[i] = Bitmap.createScaledBitmap(img, size, size, true);
            }

            imgJoypad = BitmapFactory.decodeResource(res, R.drawable.img_joypad);
            imgJoypad = Bitmap.createScaledBitmap(imgJoypad, height/2, height/2, true);
            jpr = imgJoypad.getWidth()/2;

            imgGauge[0] = new Bitmap[5];
            for(int i=0; i<imgGauge[0].length; i++){

            }

        }

    }

}
