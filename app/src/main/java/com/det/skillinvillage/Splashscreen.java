package com.det.skillinvillage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Random;


public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        setAnimations();
        int[] ids = new int[]{R.drawable.skillinvillage1,R.drawable.skillinvillage1, R.drawable.skillinvillage1};
        Random randomGenerator = new Random();
        int r= randomGenerator.nextInt(ids.length);
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();
    }

    private void setAnimations() {

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        LinearLayout l= findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        Animation anim1= AnimationUtils.loadAnimation(this,R.anim.translate);
        imageView.clearAnimation();
        imageView.startAnimation(anim1);

    }

}