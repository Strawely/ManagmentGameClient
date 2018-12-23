package com.example.solom.managmentgame;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

public class GameActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private Handler handler = new Handler();
    private Context context = this;

    private EsmFragment esmFragment = new EsmFragment();
    private ProductionFragment productionFragment = new ProductionFragment();
    private EgpFragment egpFragment = new EgpFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        initSocketCallbacks();

        if(GameStateHandler.getGame() != null){
            Intent intent = getIntent();
            GameStateHandler.getGame().setMarketLvl(intent.getIntExtra("marketLvl", 3));
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.esm);
        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);

        SocketConnector.updatePlayerState();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EsmFragment(), "Банк");
        adapter.addFragment(new Fragment(), "Фабрики");
        adapter.addFragment(new Fragment(), "Соперники");
        viewPager.setAdapter(adapter);
    }

    private void initSocketCallbacks(){
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        SocketConnector.getSocket().on("wait_production", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_production===");
                Fragment productionFragment = new ProductionFragment();
                adapter.changeFragment(0, productionFragment, "Банк");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.bank);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("produced", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===produced===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format("Произведено %d ЕГП за $%d", (Integer) args[0], (Integer) args[1]), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_egp_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_egp===");
                adapter.changeFragment(0, egpFragment, "Банк");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.egp);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });
    }
}
