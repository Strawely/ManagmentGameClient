package com.example.solom.managmentgame;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.Player;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitorFragment extends DialogFragment {

    private TextView esmTextView;
    private TextView egpTextView;
    private TextView fabrics1TextView;
    private TextView fabrics2TextView;
    private TextView moneyTextView;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        View view = inflater.inflate(R.layout.fragment_competitor, null);
//
//        Bundle bundle = this.getArguments();
//
//        esmTextView = view.findViewById(R.id.competitorEsmTextView);
//        egpTextView = view.findViewById(R.id.competitorEgpTextView);
//        fabrics1TextView = view.findViewById(R.id.competitorFabrics1TextView);
//        fabrics2TextView = view.findViewById(R.id.competitorFabrics2TextView);
//        moneyTextView = view.findViewById(R.id.competitorMoneyTextView);
//
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                esmTextView.setText(Integer.toString(bundle.getInt("esm")));
//                egpTextView.setText(Integer.toString(bundle.getInt("egp")));
//                fabrics1TextView.setText(Integer.toString(bundle.getInt("fabrics1")));
//                fabrics2TextView.setText(Integer.toString(bundle.getInt("fabrics2")));
//                moneyTextView.setText(Integer.toString(bundle.getInt("money")));
//            }
//        });
//
//        builder.setView(inflater.inflate(R.layout.fragment_competitor, null))
//                .setCancelable(true);
//
//        return builder.create();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competitor, null);

        Bundle bundle = this.getArguments();

        esmTextView = view.findViewById(R.id.competitorEsmTextView);
        egpTextView = view.findViewById(R.id.competitorEgpTextView);
        fabrics1TextView = view.findViewById(R.id.competitorFabrics1TextView);
        fabrics2TextView = view.findViewById(R.id.competitorFabrics2TextView);
        moneyTextView = view.findViewById(R.id.competitorMoneyTextView);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                esmTextView.setText(Integer.toString(bundle.getInt("esm")));
                egpTextView.setText(Integer.toString(bundle.getInt("egp")));
                fabrics1TextView.setText(Integer.toString(bundle.getInt("fabrics1")));
                fabrics2TextView.setText(Integer.toString(bundle.getInt("fabrics2")));
                moneyTextView.setText(Integer.toString(bundle.getInt("money")));
            }
        });

        return view;
    }
}
