package com.example.solom.managmentgame;

import android.app.Activity;
import android.os.Bundle;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;

public class FabricActivity extends Activity {
private PlayerState playerState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric);
        playerState=PlayerState.getPlayerState(GameStateHandler.getPlayer().getId());

    }
}
