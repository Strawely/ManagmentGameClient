package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GamesListActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_games_list);
            updateGamesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateGamesList(){
        try {
            final ArrayList<String> gamesList = new ArrayList<>();
            ListView list = findViewById(R.id.gamesList);
            final ArrayAdapter<String> gamesNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gamesList);
            list.setAdapter(gamesNamesAdapter);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gamesList.clear();
                    SocketConnector.getSocket().emit("get_games_list", null, new Ack() {
                        @Override
                        public void call(Object... args) {
                            JSONArray jsonArgs = (JSONArray) args[0];
                            for (int i = 0; i < jsonArgs.length(); i++) {
                                try {
                                    gamesList.add(jsonArgs.getJSONArray(i).getString(5));
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gamesNamesAdapter.notifyDataSetChanged();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    handler.postDelayed(this, 2000);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void  onCreateGameClick(View view){
        try {
            Intent intent = new Intent(this, CreateGameActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
