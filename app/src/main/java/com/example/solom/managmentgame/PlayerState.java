package com.example.solom.managmentgame;

import com.github.nkzawa.socketio.client.Ack;

public class PlayerState {
    private int id;
    private int esm;
    private int egp;
    private int fabrics1;
    private int fabrics2;
    private int gameId;
    private int money;
    private int rang;

    public PlayerState(int id, int esm, int egp, int fabrics1, int fabrics2, int gameId, int money, int rang) {
        this.id = id;
        this.esm = esm;
        this.egp = egp;
        this.fabrics1 = fabrics1;
        this.fabrics2 = fabrics2;
        this.gameId = gameId;
        this.money = money;
        this.rang = rang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEsm() {
        return esm;
    }

    public void setEsm(int esm) {
        this.esm = esm;
    }

    public int getEgp() {
        return egp;
    }

    public void setEgp(int egp) {
        this.egp = egp;
    }

    public int getFabrics1() {
        return fabrics1;
    }

    public void setFabrics1(int fabrics1) {
        this.fabrics1 = fabrics1;
    }

    public int getFabrics2() {
        return fabrics2;
    }

    public void setFabrics2(int fabrics2) {
        this.fabrics2 = fabrics2;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    public void takeCredit(int amount){
        Object arrObj[]={id,amount};
        SocketConnector.getSocket().emit("take_credit",arrObj);
    }
    public void creditPayoff(){

    }
    public static PlayerState getPlayerState(int id){
        Object sendObj[]={id};
        Object arrObj[]=new Object[8];
        PlayerState playerState = new PlayerState(0,0,0,0,0,0,0,0);
        SocketConnector.getSocket().emit("get_player_state", sendObj, new Ack() {
            @Override
            public void call(Object... args) {
                if (args.length >= 8) {
                    playerState.id=(Integer)args[0];
                    playerState.esm=(Integer)args[1];
                    playerState.egp=(Integer)args[2];
                    playerState.fabrics1=(Integer)args[3];
                    playerState.fabrics2=(Integer)args[4];
                    playerState.gameId=(Integer)args[5];
                    playerState.money=(Integer)args[6];
                    playerState.rang=(Integer)args[7];
                }
            }
        });
return playerState;
    }

}
