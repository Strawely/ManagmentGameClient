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
        if(GameStateHandler.getPlayer() != null) {
            Object[] args = new Object[]{GameStateHandler.getPlayer().getId(), price, qty};
            socket.emit("esm_order", args);
        }
    }

    public static void updatePlayerState(){
        socket.emit("get_player_state", new Object[]{GameStateHandler.getPlayer().getId()}, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket.emit(\"get_player_state\")");
                JSONArray jsonArray = (JSONArray) args[0];
                PlayerState ps = null;
                try {
                    ps = new PlayerState(jsonArray.getInt(0), jsonArray.getInt(1),
                            jsonArray.getInt(2), jsonArray.getInt(3), jsonArray.getInt(4),
                            jsonArray.getInt(5), jsonArray.getInt(6), jsonArray.getInt(7));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GameStateHandler.setPlayerState(ps);
            }
        });
    }

    public static void sendProduction(int quantity, int fabrics1, int fabrics2){
        if(GameStateHandler.getPlayer() != null){
            Object[] args = new Object[]{GameStateHandler.getPlayer().getId(), quantity, fabrics1, fabrics2};
            socket.emit("produce", args);
        }
    }

    public static void sendEgpRequest(int qty, int price){
        if(GameStateHandler.getPlayer() != null) {
            Object[] args = new Object[]{GameStateHandler.getPlayer().getId(), price, qty};
            socket.emit("egp_request", args);
        }
    }

    public static void sendCreditPayoff(){
        socket.emit("credit_payoff", GameStateHandler.getPlayer().getId());
    }

    public static void sendBuildingReqeust(boolean isAuto){
        if (GameStateHandler.getPlayer() != null) {
            Object[] args = new Object[]{GameStateHandler.getPlayer().getId(), isAuto};
            socket.emit("build_request", args);
        }
    }

    public static void sendNextTurn(){
        socket.emit("next_turn", GameStateHandler.getPlayer().getId());
    }

    public static void sendBankruptLeave(){
        socket.emit("bankrupt_leave", GameStateHandler.getPlayer().getId());
    }
}
