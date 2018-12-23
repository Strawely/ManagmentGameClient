package com.example.solom.managmentgame;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private TextView esmTextView;
    private TextView egpTextView;
    private TextView fabrics1TextView;
    private TextView fabrics2TextView;
    private TextView moneyTextView;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        esmTextView = view.findViewById(R.id.playerEsmTextView);
        egpTextView = view.findViewById(R.id.playerEgpTextView);
        fabrics1TextView = view.findViewById(R.id.playerFabrics1TextView);
        fabrics2TextView = view.findViewById(R.id.playerFabrics2TextView);
        moneyTextView = view.findViewById(R.id.playerMoneyTextView);

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                SocketConnector.updatePlayerState();
                PlayerState ps = GameStateHandler.getPlayerState();
                esmTextView.setText(Integer.toString(ps.getEsm()));
                egpTextView.setText(Integer.toString(ps.getEgp()));
                fabrics1TextView.setText(Integer.toString(ps.getFabrics1()));
                fabrics2TextView.setText(Integer.toString(ps.getFabrics2()));
                moneyTextView.setText(Integer.toString(ps.getMoney()));
                handler.postDelayed(this, 3000);
            }
        });

        return view;
    }

}
