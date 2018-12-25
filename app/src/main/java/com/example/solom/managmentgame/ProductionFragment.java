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
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

public class ProductionFragment extends Fragment {

    private EditText fabrics1EditText;
    private EditText fabrics2EditText;
    private EditText qtyEditText;
    private Button sendProductionBtn;

    public ProductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_production, container, false);
        Handler handler = new Handler();
        fabrics1EditText = view.findViewById(R.id.productionFabrics1EditText);
        fabrics2EditText = view.findViewById(R.id.productionFabrics2EditText);
        qtyEditText = view.findViewById(R.id.productionQtyEditText);
        sendProductionBtn = view.findViewById(R.id.sendProductionBtn);
        sendProductionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerState ps=GameStateHandler.getPlayerState();
                if (Integer.parseInt(qtyEditText.getText().toString()) <= ps.getEsm()&&Integer.parseInt(fabrics1EditText.getText().toString())<=ps.getFabrics2()&&Integer.parseInt(fabrics2EditText.getText().toString())<=ps.getFabrics2()) {
                    int fabrics1 = 0;
                    if (!fabrics1EditText.getText().toString().isEmpty())
                        fabrics1 = Integer.parseInt(fabrics1EditText.getText().toString());

                    int fabrics2 = 0;
                    if (!fabrics2EditText.getText().toString().isEmpty())
                        fabrics2 = Integer.parseInt(fabrics2EditText.getText().toString());

                    int qty = 0;
                    if (!qtyEditText.getText().toString().isEmpty()) {
                        qty = Integer.parseInt(qtyEditText.getText().toString());
                    }
                    SocketConnector.sendProduction(qty, fabrics1, fabrics2);
                    sendProductionBtn.setEnabled(false);
                }
                else{ Toast.makeText(getActivity(), "Не хватает капитала или ЕСМ", Toast.LENGTH_LONG).show();}

            }
        });

        SocketConnector.getSocket().on("production_error", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===production_error===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendProductionBtn.setEnabled(true);
                        Toast.makeText(getActivity(), "ЕГП не произведено", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
