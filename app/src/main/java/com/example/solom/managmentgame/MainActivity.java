package com.example.solom.managmentgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSocket();
        GameStateHandler.setPlayerState(null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("onResume()");
        System.out.println(GameStateHandler.getPlayer());
        if(GameStateHandler.getPlayer() != null && !GameStateHandler.getPlayer().getNickname().isEmpty()){
            findViewById(R.id.playBtn).setEnabled(true);
            viewAvatar();
            setWelcomeText();
        }
        else {
            findViewById(R.id.playBtn).setEnabled(false);
        }
        SocketConnector.getSocket().on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                TextView textView=findViewById(R.id.textViewCon);
                textView.setText("Связь установлена");
            }
        });

    }

    private void viewAvatar(){
        ImageView avatarImage = findViewById(R.id.avatarImageView);
        if(GameStateHandler.getPlayer() == null)
            avatarImage.setImageResource(R.drawable.default_avatar);
        switch (GameStateHandler.getPlayer().getAvatar()){
            case 0:
                avatarImage.setImageResource(R.drawable.avatar_0);
                break;
            case 1:
                avatarImage.setImageResource(R.drawable.avatar_1);
                break;
            case 2:
                avatarImage.setImageResource(R.drawable.avatar_2);
                break;
            case 3:
                avatarImage.setImageResource(R.drawable.avatar_3);
                break;
            case 4:
                avatarImage.setImageResource(R.drawable.avatar_4);
                break;
            case 5:
                avatarImage.setImageResource(R.drawable.avatar_5);
                break;
            case 6:
                avatarImage.setImageResource(R.drawable.avatar_6);
                break;
            case 7:
                avatarImage.setImageResource(R.drawable.avatar_7);
                break;
            default:
                avatarImage.setImageResource(R.drawable.default_avatar);
        }
    }

    @SuppressLint("DefaultLocale")
    private void setWelcomeText(){
        if (GameStateHandler.getPlayer() != null) {
            ((TextView)findViewById(R.id.welcomeTextView)).setText(String.format("Добро пожаловать, господин %s", GameStateHandler.getPlayer().getNickname()));
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

    public void onHelpClicked(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
