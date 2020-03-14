package com.example.mathriddles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView play_cv;
    CardView levels_cv;
    CardView sound_cv;
    CardView restart_cv;
    CardView exit_cv;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "OnCreate");
        play_cv = findViewById(R.id.play_cv);
        levels_cv = findViewById(R.id.levels_cv);
        sound_cv = findViewById(R.id.sound_cv);
        restart_cv = findViewById(R.id.restart_cv);
        exit_cv = findViewById(R.id.exit_cv);
        view = findViewById(R.id.background);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        open();

        play_cv.setOnClickListener(this);
        levels_cv.setOnClickListener(this);
        sound_cv.setOnClickListener(this);
        restart_cv.setOnClickListener(this);
        exit_cv.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "OnStart");
        open();
    }

    @Override
    public void onBackPressed() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MainActivity.super.onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.clearAnimation();
        view.setAnimation(anim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_cv) {
            open(PlayActivity.class);
        } else if (v.getId() == R.id.levels_cv) {
            open(LevelsActivity.class);
        } else if (v.getId() == R.id.sound_cv) {

        } else if (v.getId() == R.id.restart_cv) {

        } else if (v.getId() == R.id.exit_cv) {
            exit();
        }
    }

    private void exit() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.clearAnimation();
        view.setAnimation(anim);
    }

    private void open(final Class clazz) {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), clazz);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//                        view.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.clearAnimation();
        view.startAnimation(anim);
    }

    private void open() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.clearAnimation();
        view.startAnimation(anim);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("TAG", Boolean.toString(hasFocus));
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
