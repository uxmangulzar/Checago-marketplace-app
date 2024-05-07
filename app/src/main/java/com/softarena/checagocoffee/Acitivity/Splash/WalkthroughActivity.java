package com.softarena.checagocoffee.Acitivity.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softarena.checagocoffee.Acitivity.Signin.LoginActivity;
import com.softarena.checagocoffee.Acitivity.Home.SliderAdapter;
import com.softarena.checagocoffee.R;

public class WalkthroughActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button btnletsGetStart,btnnext,btnskip;
    Animation animation;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        //hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        btnletsGetStart = findViewById(R.id.btn_getStarted);
        btnnext = findViewById(R.id.btn_next);
        btnskip = findViewById(R.id.btnskip);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        btnletsGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalkthroughActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalkthroughActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void next (View view)
    {
    viewPager.setCurrentItem(current+1);

    }

    private void addDots(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);

        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            current = position;
            if (position == 0) {
              btnletsGetStart.setVisibility(View.INVISIBLE);
              btnnext.setVisibility(View.VISIBLE);

            } else if (position == 1) {
                btnletsGetStart.setVisibility(View.INVISIBLE);
                btnnext.setVisibility(View.VISIBLE);

            } else {
                animation = AnimationUtils.loadAnimation(WalkthroughActivity.this,R.anim.bottom_anim);
                btnletsGetStart.setAnimation(animation);
                btnletsGetStart.setVisibility(View.VISIBLE);
                btnnext.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
