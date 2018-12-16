package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

public class AuthActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void onLoginBtnClick(View view) {
        try {
            String nickname = ((EditText)findViewById(R.id.loginEditText)).getText().toString();
            Object[] args = {nickname, 0};
            final Context context = this;
            SocketConnector.getSocket().emit("register_player", args, new Ack() {
                @Override
                public void call(Object... args) {
                    try {
                        if((Integer)args[0] == 0){
                            JSONArray playerProps = (JSONArray) args[1];
                            Player newPlayer = new Player(playerProps.getInt(0), playerProps.getString(1), playerProps.getInt(2));
                            GameStateHandler.setPlayer(newPlayer);
                        }
                        else {
                            if ((Integer)args[1] == 1){
                                JSONArray playerProps = (JSONArray) args[1];
                                Player newPlayer = new Player(playerProps.getInt(0), playerProps.getString(1), playerProps.getInt(2));
                                GameStateHandler.setPlayer(newPlayer);
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
}
