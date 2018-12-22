package com.example.solom.managmentgame.dataLayer;

import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SocketConnector {

    private static Socket socket;

    public static Socket getSocket() {
        return socket;
    }

    public static void initSocket(String address){
        try {
            socket = IO.socket(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void connect(){
        socket.connect();
    }

    public static List<String> getGamesList(){
        final List<String> result = new ArrayList<>();
        socket.emit("get_games_list", null, new Ack() {
            @Override
            public void call(Object... args) {
                JSONArray jsonArgs = (JSONArray) args[0];
                for (int i = 0; i < jsonArgs.length(); i++) {
                    try {
                        result.add(jsonArgs.getJSONArray(i).getString(5));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return result;
    }

    public static void auth(String nickname, int avatar){
        Object[] args = {nickname, avatar};
        socket.emit("register_player", args, new Ack() {
            @Override
            public void call(Object... args) {
                try {
                    if(args.length == 1){
                        JSONArray playerProps = (JSONArray) args[0];
                        GameStateHandler.setPlayer(new Player(playerProps.getInt(0), playerProps.getString(1), playerProps.getInt(2)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void leaveGame(){
        Object[] params = new Object[]{GameStateHandler.getPlayer().getId(), socket.id()};
        socket.emit("leave_game", params);
    }

    public static void sendEsmRequest(int qty, int price){
        Object[] args = new Object[]{Objects.requireNonNull(GameStateHandler.getPlayer()).getId(), price, qty};
        socket.emit("esm_order", args);
    }
}
