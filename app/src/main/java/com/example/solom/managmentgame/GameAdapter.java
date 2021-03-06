package com.example.solom.managmentgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.Game;

import java.util.ArrayList;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Game> objects;

    public GameAdapter(Context context, ArrayList<Game> objects) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.game_item, viewGroup, false);
        }
        Game game = getGame(i);

        ((TextView)v.findViewById(R.id.gameNameTextView)).setText("Игра " + game.getName());
        ((TextView)v.findViewById(R.id.startEsmTextView)).setText(Integer.toString(game.getsEsm()));
        ((TextView)v.findViewById(R.id.playersTextView)).setText(String.format("Игроков %d / %d", game.getProgress(), game.getMaxPlayers()));
        ((TextView)v.findViewById(R.id.startEgpTextView)).setText(String.format("%d", game.getsEgp()));
        ((TextView)v.findViewById(R.id.startFabrics1TextView)).setText(String.format("%d", game.getsFabrics1()));
        ((TextView)v.findViewById(R.id.startFabrics2TextView)).setText(String.format("%d", game.getsFabrics2()));
        ((TextView)v.findViewById(R.id.startMoneyTextView)).setText(String.format("%d", game.getsMoney()));


        return v;
    }

    private Game getGame(int i){
        return objects.get(i);
    }
}
