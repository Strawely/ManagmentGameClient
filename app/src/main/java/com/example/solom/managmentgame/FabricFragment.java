package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;

public class FabricFragment extends Fragment {
    private PlayerState playerState;
    ImageView fabric;
    ImageView fabricA;
    private Context context = getContext();
    Handler handler = new Handler();
    TextView eSM;
    TextView eGP;
    TextView money;
TextView round;
    LinearLayout liner;


    public FabricFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fabric, container, false);
        liner = view.findViewById(R.id.linerFabric);
        fabric=new ImageView(context);
        fabric.setBackgroundResource(R.drawable.fabric);
        fabricA=new ImageView(context);
        fabricA.setBackgroundResource(R.drawable.auto_fabric);
        eSM=view.findViewById(R.id.textViewEsm);
        eGP=view.findViewById(R.id.textViewEgp);
        money=view.findViewById(R.id.textViewMoney);
        round=view.findViewById(R.id.textViewRound);
        update();


        return view;
    }
    protected void update(){
        SocketConnector.updatePlayerState();
        playerState = GameStateHandler.getPlayerState();
        for(int i=0;i<playerState.getFabrics1();i++){
            liner.addView(fabric);
        }
        for(int i=0;i<playerState.getFabrics2();i++){
            liner.addView(fabricA);
        }
        eGP.setText(playerState.getEgp());
        eSM.setText(playerState.getEsm());
        String m=Integer.toString(playerState.getMoney())+"$";
        money.setText(m);
        round.setText(GameStateHandler.getGame().getTurnNum());
    }
}




