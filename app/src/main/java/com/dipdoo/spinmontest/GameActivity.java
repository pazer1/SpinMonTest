package com.dipdoo.spinmontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    GameView gv;
    TextView tvScore,tvCoin,tvGem,tvBomb,tvChampion;

    View dialog = null;
    Animation ani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gv = findViewById(R.id.gv);

        tvScore= findViewById(R.id.tv_score);
        tvCoin= findViewById(R.id.tv_coin);
        tvGem= findViewById(R.id.tv_gem);
        tvBomb= findViewById(R.id.tv_bomb);
        tvChampion= findViewById(R.id.tv_champion);

    }

    @Override
    protected void onPause() {
        gv.pauseGame();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        gv.stopGame();//GameView 종료!!
        super.onDestroy();
    }

    public void clickPause(View view){
        if (dialog != null)return;

        gv.pauseGame();

        dialog= findViewById(R.id.dialog_pause);
        dialog.setVisibility(View.VISIBLE);
    }

}
