package com.example.solom.managmentgame;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

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

        tabLayout.getTabAt(0).setIcon(R.drawable.esm);
        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);

        SocketConnector.getSocket().on("wait_production", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_production===");
                Fragment productionFragment = new ProductionFragment();
                adapter.changeFragment(0, productionFragment, "Банк", getResources().getDrawable(R.drawable.bank));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.bank);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                    }
                });
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EsmFragment(), "Банк", getResources().getDrawable(R.drawable.esm));
        adapter.addFragment(new FactoryFragment(), "Фабрики", getResources().getDrawable(R.drawable.fabric));
        viewPager.setAdapter(adapter);
    }
}
