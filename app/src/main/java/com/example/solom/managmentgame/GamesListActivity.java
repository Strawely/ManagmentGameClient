package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.solom.managmentgame.dataLayer.Game;
import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GamesListActivity extends Activity {

    private Handler handler = new Handler();
    private Context context = this;
    private ArrayList<Game> gamesList;
    private GameAdapter gamesAdapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_games_list);
            list = findViewById(R.id.gamesList);
            gamesList = new ArrayList<>();
            gamesAdapter = new GameAdapter(this, gamesList);
            updateGamesList();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Game game=(Game)gamesAdapter.getItem(position);
                    Object arrObj[] = {game.getId(), SocketConnector.getSocket().id(), GameStateHandler.getPlayer().getId()};
                    SocketConnector.getSocket().emit("join_game", arrObj);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gamesAdapter.notifyDataSetChanged();
                        }
                    });
                    if(GameStateHandler.getGame() == null)
                        GameStateHandler.setGame(game);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gamesAdapter.notifyDataSetChanged();
                        }
                    });
                    Intent intent = new Intent(context, PlayersWaitActivity.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gamesAdapter.notifyDataSetChanged();
                        }
                    });
                    startActivity(intent);
                    finish();
                }
            });
            SocketConnector.getSocket().on("game_start", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        Object[] a = args;
                        System.out.println(a.toString());
                        Intent intent = new Intent(context, GameActivity.class);
                        intent.putExtra("marketLvl", (Integer) args[0]);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateGamesList(){
        try {
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
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
