package com.example.solom.managmentgame;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;

public class FabricFragment extends Fragment {
    private PlayerState playerState;
    ImageView fabric;
    ImageView fabricA;
    private Context context ;
    AlertDialog.Builder adB;
    AlertDialog.Builder adN;
    Handler handler = new Handler();
    TextView eSM;
    TextView eGP;
    TextView money;
    TextView round;
    LinearLayout liner;
    FloatingActionButton bankrupt,next;


    public FabricFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fabric, container, false);
        context = getActivity();
        liner = view.findViewById(R.id.linerFabric);
        eSM=view.findViewById(R.id.textViewEsm);
        eGP=view.findViewById(R.id.textViewEgp);
        money=view.findViewById(R.id.textViewMoney);
        round=view.findViewById(R.id.textViewRound);
        bankrupt=view.findViewById(R.id.floatingActionButtonBankrupt);
        next=view.findViewById(R.id.floatingActionButtonNext);


        update();

        String titleB = "Банкротство";
        String messageB = "Вы правда хотите объявить о банкротстве?";
        String button1StringB = "Да";

        String titleN = "Пропустить ход";
        String messageN = "Вы правда хотите пропустить операцию?";
        String button1StringN = "Да";

        String titleF = "Постройка фабрики";
        String messageF = "Какую фабрику вы хотит построить?";
        String button1StringF = "Автоматизированная";
        String button2StringF="Обычная";

        adB = new AlertDialog.Builder(getActivity());
        adB.setTitle(titleB);  // заголовок
        adB.setMessage(messageB); // сообщение

        adN = new AlertDialog.Builder(getActivity());
        adN.setTitle(titleN);  // заголовок
        adN.setMessage(messageN); // сообщение

        adB.setPositiveButton(button1StringB, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                SocketConnector.sendBankruptLeave();
                Toast.makeText(getActivity(), "Вы банкрот",
                        Toast.LENGTH_LONG).show();
            }
        });

        adB.setCancelable(true);
        adB.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getActivity(), "Вы ничего не выбрали",
                        Toast.LENGTH_LONG).show();
            }
        });

        adN.setPositiveButton(button1StringN, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "Следующая опреация",
                        Toast.LENGTH_LONG).show();
            }
        });

        adN.setCancelable(true);
        adN.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getActivity(), "Вы ничего не выбрали",
                        Toast.LENGTH_LONG).show();
            }
        });

        bankrupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adB.show();
            }
            });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adN.show();
            }
        });


 return view;
    }
@Override
public void onStart(){
        super.onStart();
        update();
}
@Override
public void onPause(){
super.onPause();
}
@Override
public void onResume(){

        super.onResume();
        update();
}

    protected void update(){
        liner.removeAllViews();
        SocketConnector.updatePlayerState();
        if(GameStateHandler.getPlayerState() != null) {
            playerState = GameStateHandler.getPlayerState();
            for (int i = 0; i < playerState.getFabrics1(); i++) {
                fabric = new ImageView(getActivity());
                fabric.setScaleType(ImageView.ScaleType.FIT_XY);
                fabric.setBackgroundResource(R.drawable.fabric);
                liner.addView(fabric);
            }
            for (int i = 0; i < playerState.getFabrics2(); i++) {
                fabricA = new ImageView(getActivity());
                fabricA.setScaleType(ImageView.ScaleType.FIT_XY);
                fabricA.setBackgroundResource(R.drawable.auto_fabric);
                liner.addView(fabricA);
            }
            eGP.setText(Integer.toString(playerState.getEgp()));
            eSM.setText(Integer.toString(playerState.getEsm()));
            String m = Integer.toString(playerState.getMoney()) + "$";
            money.setText(m);
            round.setText(Integer.toString(GameStateHandler.getGame().getTurnNum()));
        }
    }
}




