package com.example.solom.managmentgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;

public class UpgradeFragment extends Fragment {
    public UpgradeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upgrade, container, false);
        Button button = view.findViewById(R.id.buttonUp);
        Switch auto = view.findViewById(R.id.switchUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PlayerState ps = GameStateHandler.getPlayerState();
//                if (ps.getMoney() >= 3500) {
//                    System.out.println("===UpgradeOrderClick===");
                    button.setEnabled(false);
                    SocketConnector.sendUpgradeRequest(auto.isChecked());
//                }
            }
        });
        return view;
    }
}
