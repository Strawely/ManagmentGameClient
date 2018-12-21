package com.example.solom.managmentgame;

public class Game {
    private int id;
    private int turnNum;
    private int turnStage;
    private int marketLvl;
    private boolean isOpened;
    private String name;
    private int sEsm;
    private int sEgp;
    private int sMoney;
    private int sFabrics1;
    private int sFabrics2;
    private int maxPlayers;
    private int progress;

    public Game(int id, int turnNum, int turnStage, int marketLvl, boolean isOpened, String name,
                int sEsm, int sEgp, int sMoney, int sFabrics1, int sFabrics2, int maxPlayers, int progress) {
        this.id = id;
        this.turnNum = turnNum;
        this.turnStage = turnStage;
        this.marketLvl = marketLvl;
        this.isOpened = isOpened;
        this.name = name;
        this.sEsm = sEsm;
        this.sEgp = sEgp;
        this.sMoney = sMoney;
        this.sFabrics1 = sFabrics1;
        this.sFabrics2 = sFabrics2;
        this.maxPlayers = maxPlayers;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public int getTurnStage() {
        return turnStage;
    }

    public int getMarketLvl() {
        return marketLvl;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public String getName() {
        return name;
    }

    public int getsEsm() {
        return sEsm;
    }

    public int getsEgp() {
        return sEgp;
    }

    public int getsMoney() {
        return sMoney;
    }

    public int getsFabrics1() {
        return sFabrics1;
    }

    public int getsFabrics2() {
        return sFabrics2;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getProgress() {
        return progress;
    }

    public void setMarketLvl(int marketLvl) {
        this.marketLvl = marketLvl;
    }
}
