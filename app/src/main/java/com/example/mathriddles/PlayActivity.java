package com.example.mathriddles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mathriddles.database.MathRiddlessDatabase;
import com.example.mathriddles.datasource.LevelDatasource;
import com.example.mathriddles.models.Level;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    View view;
    View next_level_root;
    Level level;
    LinearLayout enter_btn;
    ImageView image;
    View btn1;
    View btn2;
    View btn3;
    View btn4;
    View btn5;
    View btn6;
    View btn7;
    View btn8;
    View btn9;
    View btn0;
    View hint;
    View clear;
    TextView answer;
    View wrongtxt;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        view = findViewById(R.id.background);
        next_level_root = findViewById(R.id.next_level_root);
        enter_btn = findViewById(R.id.enter_ll);
        wrongtxt = findViewById(R.id.wrongtxt);
        enter_btn.setOnClickListener(this);

        compositeDisposable = new CompositeDisposable();

        image = findViewById(R.id.image);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        hint = findViewById(R.id.hint);
        clear = findViewById(R.id.clear);
        answer = findViewById(R.id.answer);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        clear.setOnClickListener(this);
        hint.setOnClickListener(this);

        if (getIntent() != null) {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                int level_id = extra.getInt("level_id");
                initLevel(level_id);
            }
        }

        if (level != null)
            setTitle("LEVEL " + level.getId());
        else
            setTitle("LEVEL");
        toolbar = findViewById(R.id.toolbar);
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

    private void initLevel(int level_id) {
        LevelDatasource levelDatasource = LevelDatasource.getInstance(MathRiddlessDatabase.getInstance(this).levelDAO());

        Disposable disposable = levelDatasource.getLevelById(level_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Level>() {
                    @Override
                    public void accept(Level l) throws Exception {
                        level = l;
                        setTitle("LEVEL " + level.getId());
                        image.setImageDrawable(getResources().getDrawable(level.getQuestionImageId()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(PlayActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                        createProduct(product1);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onBackPressed() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PlayActivity.super.onBackPressed();
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

    @Override
    protected void onStart() {
        super.onStart();
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
    public void onClick(View v) {
        if (v.getId() == R.id.enter_ll) {

            String answerTxt = answer.getText().toString().trim();
            if (answerTxt.equals(level.getAnswer().trim())) {
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        next_level_root.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                next_level_root.clearAnimation();
                next_level_root.startAnimation(anim);
            } else {
                wrongtxt.setVisibility(View.VISIBLE);
                answer.setText("");
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        wrongtxt.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }


        } else if (v.getId() == R.id.hint) {

//            final MaterialDialog dialog = new MaterialDialog(this, MaterialDialog.getDEFAULT_BEHAVIOR());
//
//
//            dialog.setContentView(R.layout.hint_dialog);
//            dialog.getView().findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();

           View customView = LayoutInflater.from(this).inflate(R.layout.hint_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(R.layout.hint_dialog);
            AlertDialog dialog = builder.create();

            dialog.show();


        } else if (v.getId() == R.id.clear) {
            answer.setText("");
        } else if (v.getId() == R.id.btn0) {
            answer.setText(answer.getText().toString() + "0");
        } else if (v.getId() == R.id.btn1) {
            answer.setText(answer.getText().toString() + "1");
        } else if (v.getId() == R.id.btn2) {
            answer.setText(answer.getText().toString() + "2");
        } else if (v.getId() == R.id.btn3) {
            answer.setText(answer.getText().toString() + "3");
        } else if (v.getId() == R.id.btn4) {
            answer.setText(answer.getText().toString() + "4");
        } else if (v.getId() == R.id.btn5) {
            answer.setText(answer.getText().toString() + "5");
        } else if (v.getId() == R.id.btn6) {
            answer.setText(answer.getText().toString() + "6");
        } else if (v.getId() == R.id.btn7) {
            answer.setText(answer.getText().toString() + "7");
        } else if (v.getId() == R.id.btn8) {
            answer.setText(answer.getText().toString() + "8");
        } else if (v.getId() == R.id.btn9) {
            answer.setText(answer.getText().toString() + "9");
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
