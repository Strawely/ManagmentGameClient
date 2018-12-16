package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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
            final ArrayList<Game> gamesList = new ArrayList<>();
            ListView list = findViewById(R.id.gamesList);
            final GameAdapter gamesAdapter = new GameAdapter(this, gamesList);
            list.setAdapter(gamesAdapter);
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
                                    JSONArray jsonArray = jsonArgs.getJSONArray(i);
                                    gamesList.add(new Game(jsonArray.getInt(0), jsonArray.getInt(1), jsonArray.getInt(2),
                                            jsonArray.getInt(3), (jsonArray.getInt(4) != 0), jsonArray.getString(5), jsonArray.getInt(6),
                                            jsonArray.getInt(7), jsonArray.getInt(8),jsonArray.getInt(9), jsonArray.getInt(10),
                                            jsonArray.getInt(11), jsonArray.getInt(12)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gamesAdapter.notifyDataSetChanged();
                                }
                            });
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
