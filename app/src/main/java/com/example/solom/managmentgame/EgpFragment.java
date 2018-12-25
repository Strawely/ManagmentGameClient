package com.example.solom.managmentgame;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EgpFragment extends Fragment {

    private EditText egpPriceEditText;
    private EditText egpQtyEditText;


    public EgpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_egp, container, false);
        Handler handler = new Handler();
        int maxQty = 0;
        int minPrice = 0;

        if(GameStateHandler.getGame() != null) {
            maxQty = GameStateHandler.getGame().getMarket()[GameStateHandler.getGame().getMarketLvl() - 1][2];
            minPrice = GameStateHandler.getGame().getMarket()[GameStateHandler.getGame().getMarketLvl() - 1][3];
        }

        ((TextView)view.findViewById(R.id.egpQtyTextView)).setText(Integer.toString(maxQty));
        ((TextView)view.findViewById(R.id.egpPriceTextView)).setText(Integer.toString(minPrice) + " $");

        Button button = view.findViewById(R.id.egpSendRequestBtn);
        egpPriceEditText = view.findViewById(R.id.egpPriceEditText);
        egpQtyEditText = view.findViewById(R.id.egpQtyEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerState ps=GameStateHandler.getPlayerState();
                if(Integer.parseInt(egpQtyEditText.getText().toString())<=ps.getEgp()) {
                    System.out.println("===EgpOrderClick===");
                    button.setEnabled(false);
                    SocketConnector.sendEgpRequest(Integer.parseInt(egpQtyEditText.getText().toString()),
                            Integer.parseInt(egpPriceEditText.getText().toString()));
                }
            }
        });



        return view;
    }

}
