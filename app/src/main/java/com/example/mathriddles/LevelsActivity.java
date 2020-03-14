package com.example.mathriddles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.mathriddles.database.MathRiddlessDatabase;
import com.example.mathriddles.datasource.LevelDatasource;
import com.example.mathriddles.models.Level;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LevelsActivity extends AppCompatActivity implements LevelsListAdapter.OnItemClickListener {
    Toolbar toolbar;
    View view;
    RecyclerView levels_recyclerview;
    List<Level> levels = new ArrayList<>();
    CompositeDisposable compositeDisposable;
    LevelDatasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setTitle("LEVELS");
        toolbar = findViewById(R.id.toolbar);
        view = findViewById(R.id.background);
        levels_recyclerview = findViewById(R.id.levels_list);

        compositeDisposable = new CompositeDisposable();
        datasource = LevelDatasource.getInstance(MathRiddlessDatabase.getInstance(this).levelDAO());


        init();

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Toolbar click.", Toast.LENGTH_SHORT).show();
            }
        });

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        open();
    }

    private void init() {
        Disposable disposable = datasource.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Level>>() {
                    @Override
                    public void accept(List<Level> l) throws Exception {
                        levels.clear();
                        levels.addAll(l);
                        LevelsListAdapter adapter = new LevelsListAdapter(LevelsActivity.this, levels, LevelsActivity.this);
                        levels_recyclerview.setLayoutManager(new GridLayoutManager(LevelsActivity.this, 5));
                        levels_recyclerview.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LevelsActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);

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
                LevelsActivity.super.onBackPressed();
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
    public void onClick(Level level) {
        Toast.makeText(this, "OnClick " + level.getId(), Toast.LENGTH_SHORT).show();
        open(PlayActivity.class, level);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "OnStart");
        open();
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

    private void open(final Class clazz, final Level level) {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), clazz);
                intent.putExtra("level_id", level.getId());
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
//            hideSystemUI();
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
