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
import com.example.solom.managmentgame.dataLayer.SocketConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends Fragment {

    private EditText amountEditText;
    private Button takeCreditBtn;

    public CreditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        Handler handler = new Handler();
        amountEditText = view.findViewById(R.id.creditAmountEditText);
        takeCreditBtn = view.findViewById(R.id.takeCreditBtn);

        takeCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("===take_credit===");
                if(!amountEditText.getText().toString().isEmpty()){
                    SocketConnector.getSocket().emit("take_credit", GameStateHandler.getPlayer().getId(),
                            Integer.parseInt(amountEditText.getText().toString()));
                    ((Button)view).setEnabled(false);
                }
                else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Введены некорректные данные", Toast.LENGTH_SHORT).show();
                            ((Button)view).setEnabled(true);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
