package com.example.solom.managmentgame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSocket();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume()");
        System.out.println(GameStateHandler.getPlayer());
        if(GameStateHandler.getPlayer() != null){
            findViewById(R.id.playBtn).setEnabled(true);
        }
        else {
            findViewById(R.id.playBtn).setEnabled(false);
        }
    }

    private void initSocket(){
        try {
            SocketConnector.initSocket("http://10.0.3.2:5000");
            SocketConnector.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEnterBtnClick(View view) {
        Intent intent = new Intent(this, AuthActivity.class);

        startActivity(intent);
    }

    public void onPlayBtnClick(View view) {
        try {
            if(!Objects.requireNonNull(SocketConnector.getSocket()).connected()) SocketConnector.connect();
            Intent intent = new Intent(this, GamesListActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
