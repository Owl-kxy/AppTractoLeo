package com.ronald.apptractoleo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView txtvTitle1, txtvTitle2;
    ImageView imgvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Animation varAnimation1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation varAnimation2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        txtvTitle1 = findViewById(R.id.txtTitleImportaciones);
        txtvTitle2 = findViewById(R.id.txtTitleTractoLeo);

        imgvLogo = findViewById(R.id.ImgviewLogo);

        txtvTitle1.setAnimation(varAnimation2);
        txtvTitle2.setAnimation(varAnimation2);

        imgvLogo.setAnimation(varAnimation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intsplash = new Intent(SplashActivity.this,SelectOptionUser.class);
                startActivity(intsplash);
                finish();
            }
        },5000);
    }
}