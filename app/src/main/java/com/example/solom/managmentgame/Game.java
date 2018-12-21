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

    private double[][] market;

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
        market = new double[][]{{1.0*maxPlayers, 800, 3.0*maxPlayers, 6500},
                                {1.5*maxPlayers, 650, 2.5*maxPlayers, 6000},
                                {2.0*maxPlayers, 500, 2.0*maxPlayers, 5500},
                                {2.5*maxPlayers, 400, 1.5*maxPlayers, 5000},
                                {3.0*maxPlayers, 300, 1.0*maxPlayers, 4500}};
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

    public double[][] getMarket() {
        return market;
    }

    public void setMarket(double[][] market) {
        this.market = market;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public void setTurnStage(int turnStage) {
        this.turnStage = turnStage;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setsEsm(int sEsm) {
        this.sEsm = sEsm;
    }

    public void setsEgp(int sEgp) {
        this.sEgp = sEgp;
    }

    public void setsMoney(int sMoney) {
        this.sMoney = sMoney;
    }

    public void setsFabrics1(int sFabrics1) {
        this.sFabrics1 = sFabrics1;
    }

    public void setsFabrics2(int sFabrics2) {
        this.sFabrics2 = sFabrics2;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
