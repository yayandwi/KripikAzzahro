package com.yayan.kripikazzahro;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


import java.util.Timer;

import java.util.TimerTask;

public class home extends AppCompatActivity {
    ViewPager viewPager;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        TextView txtTentang;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        txtTentang =(TextView)findViewById(R.id.text);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Questrial-Regular.ttf");
        txtTentang.setTypeface(face);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);



        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }



    public class MyTimerTask extends TimerTask{

        @Override

        public void run() {

            home.this.runOnUiThread(new Runnable() {

                @Override

                public void run() {

                    if (viewPager.getCurrentItem() == 0) {

                        viewPager.setCurrentItem(1);

                    }else if (viewPager.getCurrentItem() == 1) {

                        viewPager.setCurrentItem(2);

                    }else {

                        viewPager.setCurrentItem(0);

                    }

                }

            });

        }

    }

}