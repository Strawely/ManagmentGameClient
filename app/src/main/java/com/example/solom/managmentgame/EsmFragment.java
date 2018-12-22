package com.example.solom.managmentgame;


import android.content.Context;
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
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsmFragment extends Fragment {

    private EditText esmPriceEditText;
    private EditText esmQtyEditText;
    private Context context = getContext();
    Handler handler = new Handler();

    public EsmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_esm, container, false);
        double maxQty = 0;
        double minPrice = 0;
        if(GameStateHandler.getGame() != null) {
             maxQty = GameStateHandler.getGame().getMarket()[GameStateHandler.getGame().getMarketLvl() - 1][0];
             minPrice = GameStateHandler.getGame().getMarket()[GameStateHandler.getGame().getMarketLvl() - 1][1];
        }

        ((TextView)view.findViewById(R.id.esmQtyTextView)).setText(Double.toString(maxQty));
        ((TextView)view.findViewById(R.id.esmPriceTextView)).setText(Double.toString(minPrice));

        Button button = view.findViewById(R.id.esmSendRequestBtn);
        esmPriceEditText = view.findViewById(R.id.esmPriceEditText);
        esmQtyEditText = view.findViewById(R.id.esmQtyEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("===EsmOrderClick===");
                button.setEnabled(false);
                SocketConnector.sendEsmRequest(Integer.parseInt(esmQtyEditText.getText().toString()),
                        Integer.parseInt(esmPriceEditText.getText().toString()));
            }
        });

        SocketConnector.getSocket().on("esm_orders_approved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===Called esm_orders_approved===");
                JSONArray ordersApproved = (JSONArray) args[0];
                boolean isApproved = false;
                for (int i = 0; i < ordersApproved.length(); i++){
                    try {
                        JSONArray order = ordersApproved.getJSONArray(i);
                        isApproved = order.getInt(1) == GameStateHandler.getPlayer().getId();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (isApproved){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Запрос на ЕСМ удовлетворён", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return view;
    }

}
