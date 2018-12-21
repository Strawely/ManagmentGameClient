package com.example.solom.managmentgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FabricActivity extends Activity {
private PlayerState playerState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric);
        playerState=PlayerState.getPlayerState(GameStateHandler.getPlayer().getId());
    }
}
