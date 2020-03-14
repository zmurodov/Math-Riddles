package com.example.mathriddles.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mathriddles.R;
import com.example.mathriddles.dao.LevelDAO;
import com.example.mathriddles.datasource.LevelDatasource;
import com.example.mathriddles.models.Level;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.mathriddles.database.MathRiddlessDatabase.MATHRIDDLESS_DATABASE_VERSION;

@Database(exportSchema = false,
        entities = {
                Level.class
        },
        version = MATHRIDDLESS_DATABASE_VERSION)
public abstract class MathRiddlessDatabase extends RoomDatabase {

    protected static final int MATHRIDDLESS_DATABASE_VERSION = 1;
    private static final String MATHRIDDLESS_DATABASE_NAME = "MATHRIDDLESS_database";

    public abstract LevelDAO levelDAO();

    private static MathRiddlessDatabase mInstance;


    public static MathRiddlessDatabase getInstance(final Context context) {

        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, MathRiddlessDatabase.class, MATHRIDDLESS_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            CompositeDisposable compositeDisposable = new CompositeDisposable();

                            compositeDisposable.add(createLevels(context));
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                        }

                        @Override
                        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                        }
                    })
                    .build();

        }
        return mInstance;
    }

    private static Disposable createLevels(final Context context) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                LevelDatasource datasource = LevelDatasource.getInstance(getInstance(context).levelDAO());

                Level level1 = new Level(R.drawable.savol_1, "5+7+8=20", "4");
                Level level2 = new Level(R.drawable.savol_2, "7+7+4+3=21", "17");
                Level level3 = new Level(R.drawable.savol_3, "?+12+19=32", "1");
                Level level4 = new Level(R.drawable.savol_4, "12+3+13=28;  8+14+6=28", "10");
                Level level5 = new Level(R.drawable.savol_5, "12+5+16=33;  11+18+4=33", "6");
                Level level6 = new Level(R.drawable.savol_6, "5+7+6+6=24;  8+12+1+13=34", "9");
                Level level7 = new Level(R.drawable.savol_7, "9*6=54;  54*6=324", "4");
                Level level8 = new Level(R.drawable.savol_8, "3+1+2+4+1=11;  6+8=14", "15");
                Level level9 = new Level(R.drawable.savol_9, "(63+57)/3=40", "34");
                Level level10 = new Level(R.drawable.savol_10, "26", "30");
                Level level11 = new Level(R.drawable.savol_11, "6*5-7*4=2", "3");
                Level level12 = new Level(R.drawable.savol_12, "3*9+5=32", "44");
                Level level13 = new Level(R.drawable.savol_13, "17*2=34;  17*4=68;  17*6=102", "5");
                Level level14 = new Level(R.drawable.savol_14, "25=25=25=25", "6");
                Level level15 = new Level(R.drawable.savol_15, "74=74=74=74", "0");
                Level level16 = new Level(R.drawable.savol_16, "88=88", "12");
                Level level17 = new Level(R.drawable.savol_17, "(12+24)/18", "2");
                Level level18 = new Level(R.drawable.savol_18, "25-12*2=49-12*4", "1");
                Level level19 = new Level(R.drawable.savol_19, "x/12=2;  2*x=48", "24");
                Level level20 = new Level(R.drawable.savol_20, "15", "15");
                Level level21 = new Level(R.drawable.savol_21, "2.5+2.8+3.2+1.5", "10");
                Level level22 = new Level(R.drawable.savol_22, "4.5+4.5+2.5+1.5=13", "11");
                Level level23 = new Level(R.drawable.savol_23, "5+15+3+5+4+18+5+4+6+2+9+?=99", "23");
                Level level24 = new Level(R.drawable.savol_24, "11+8+12=31;  9+14+9=32", "10");
                Level level25 = new Level(R.drawable.savol_25, "5,12,19 (+7);  6,13,20 (+7)", "25");
                Level level26 = new Level(R.drawable.savol_26, "(14+7)-(5+6)=10;  (13+5)-(4+7)=7", "10");
                Level level27 = new Level(R.drawable.savol_27, "48/2=18+6;  (18+6)/2=5+3+4", "3");
                Level level28 = new Level(R.drawable.savol_28, "2*4-4=4;  4*2-2=6;  2*3-3=3;  3*1-1=2", "14 224");
                Level level29 = new Level(R.drawable.saavol_29, "?*2-6=10", "8");
                Level level30 = new Level(R.drawable.savol_30, "(22+28)-(14+17)=19", "9");
                Level level31 = new Level(R.drawable.savol_31, "(3+9+11+5)/4=7", "15");
                Level level32 = new Level(R.drawable.savol_32, "(1*7*9)-(1+7+9)=46", "334");
                Level level33 = new Level(R.drawable.savol_33, "(11+5+9+1+4+13)*6=258", "324");
                Level level34 = new Level(R.drawable.savol_34, "40=40=40=40", "9");
                Level level35 = new Level(R.drawable.savol_35, "(3+11+23+6+9+4+7+13+14)/9=10", "18");
                Level level36 = new Level(R.drawable.savol_36, "81=81", "3");
                Level level37 = new Level(R.drawable.savol_37, "5+28*2=?", "61");
                Level level38 = new Level(R.drawable.savol_38, "(4+4)*8=64;  (?+(-8))*16=64", "12");
                Level level39 = new Level(R.drawable.savol_39, "20/5=x-17", "21");
                Level level40 = new Level(R.drawable.savol_40, "26", "26");

                datasource.insertLevel(level1, level2, level3, level4, level5,
                        level6, level7, level8, level9, level10, level11,
                        level12, level13, level14, level15, level16, level17,
                        level18, level19, level20, level21, level22, level23,
                        level24, level25, level26, level27, level28, level29,
                        level30, level31, level32, level33, level34, level35,
                        level36, level37, level38, level39, level40);

                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        return disposable;
    }

}
