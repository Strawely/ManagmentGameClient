package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.solom.managmentgame.dataLayer.SocketConnector;

public class PlayersWaitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_wait);
    }

    public void onLeaveBtnClick(View view) {
        SocketConnector.leaveGame();
        startActivity(new Intent(this, GamesListActivity.class));
        finish();
    }
}
