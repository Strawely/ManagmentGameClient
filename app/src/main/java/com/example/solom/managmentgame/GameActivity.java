package com.example.solom.managmentgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;

public class GameActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        if(GameStateHandler.getGame() != null){
            Intent intent = getIntent();
            GameStateHandler.getGame().setMarketLvl(intent.getIntExtra("marketLvl", 3));
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.bank);
        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EsmFragment(), "ЕСМ");
        adapter.addFragment(new FactoryFragment(), "Фабрики");
        viewPager.setAdapter(adapter);
    }
}
