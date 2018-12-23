package com.example.solom.managmentgame;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BankFragment extends Fragment {

    private String text = "Банк";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
