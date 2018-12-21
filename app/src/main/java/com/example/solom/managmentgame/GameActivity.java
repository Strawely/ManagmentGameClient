package com.example.solom.managmentgame;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.github.nkzawa.emitter.Emitter;

public class GameActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(GameStateHandler.getGame() != null){
            Intent intent = getIntent();
            GameStateHandler.getGame().setMarketLvl(intent.getIntExtra("marketLvl", 3));
        }

        // получаем TabHost
        TabHost tabHost = getTabHost();

        // инициализация была выполнена в getTabHost
        // метод setup вызывать не нужно

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("bank");
        tabSpec.setIndicator("Банк");
        tabSpec.setContent(new Intent(this, BankActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Вкладка 2");
        tabSpec.setContent(new Intent(this, FabricActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Вкладка 3");
        tabSpec.setContent(new Intent(this, CompetitorsActivity.class));
        tabHost.addTab(tabSpec);
    }
}
