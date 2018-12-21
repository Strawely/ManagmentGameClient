package com.example.solom.managmentgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

import java.util.Objects;

public class BankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GameStateHandler.getGame() == null) return;
        switch (GameStateHandler.getGame().getTurnStage()){
            case 1:
                setContentView(R.layout.esm_auction_layout);
                break;
        }
    }
}
