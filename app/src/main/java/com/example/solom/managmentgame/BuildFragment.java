package com.example.solom.managmentgame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.solom.managmentgame.dataLayer.SocketConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuildFragment extends Fragment {


    public BuildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_build, container, false);
        Button button=view.findViewById(R.id.buttonBuild);
        RadioButton auto=view.findViewById(R.id.radioButtonAuto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("===BuildOrderClick===");
                button.setEnabled(false);
                SocketConnector.sendBuildingReqeust(auto.isChecked());
            }
        });
        return view;
    }

}
