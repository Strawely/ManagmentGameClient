package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.Player;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONArray;

public class AuthActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAvatar();
    }

    private void viewAvatar(){
        ImageView avatarImage = findViewById(R.id.authAvatarImageView);
        if(GameStateHandler.getPlayer() == null) return;
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
                avatarImage.setImageResource(R.drawable.avatar_0);
        }
    }

    public void onLoginBtnClick(View view) {
        try {
            String nickname = ((EditText)findViewById(R.id.loginEditText)).getText().toString();

            if(nickname.isEmpty()) Toast.makeText(this, "Введены некорректные данные", Toast.LENGTH_SHORT).show();
            Object[] args = new Object[2];
            args[0] = nickname;
            if(GameStateHandler.getPlayer() != null) {
                args[1] = GameStateHandler.getPlayer().getAvatar();
            }
            else {
                args[1] = 0;
            }
            final Context context = this;

            SocketConnector.getSocket().emit("register_player", args, new Ack() {
                @Override
                public void call(Object... args) {
                    try {
                        if((Integer)args[0] == 0){
                            JSONArray playerProps = (JSONArray) args[1];
                            Player newPlayer = new Player(playerProps.getInt(0), playerProps.getString(1), playerProps.getInt(2));
                            GameStateHandler.setPlayer(newPlayer);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Зарегистрирован новый игрок", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            if ((Integer)args[0] == 1){
                                JSONArray playerProps = (JSONArray) args[1];
                                Player newPlayer = new Player(playerProps.getInt(0), playerProps.getString(1), playerProps.getInt(2));
                                GameStateHandler.setPlayer(newPlayer);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Успешная авторизация", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackClick(View view) {
        this.finish();
    }

    public void onAvatarClick(View view) {
        Intent intent = new Intent(this, AvatarChoiceActivity.class);
        startActivity(intent);
    }
}
