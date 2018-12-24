package com.example.solom.managmentgame;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.PlayerState;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private TextView esmTextView;
    private TextView egpTextView;
    private TextView fabrics1TextView;
    private TextView fabrics2TextView;
    private TextView moneyTextView;
    private Button[] buttons;

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

        buttons = new Button[]{view.findViewById(R.id.player1InfoBtn), view.findViewById(R.id.player2InfoBtn),
            view.findViewById(R.id.player3InfoBtn), view.findViewById(R.id.player4InfoBtn)};

        for(Button btn : buttons){
            btn.setVisibility(View.GONE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nick = ((Button)view).getText().toString();
                    SocketConnector.getSocket().emit("get_competitor_info", new Object[]{nick}, new Ack() {
                        @Override
                        public void call(Object... args) {
                            System.out.println("Socket.emit(\"get_competitors_info\")");
                            JSONArray jsonArray = (JSONArray) args[0];
                            System.out.println(jsonArray.toString());
                            PlayerState ps = null;
                            try {
                                ps = new PlayerState(jsonArray.getInt(0), jsonArray.getInt(1),
                                        jsonArray.getInt(2), jsonArray.getInt(3), jsonArray.getInt(4),
                                        jsonArray.getInt(5), jsonArray.getInt(6), jsonArray.getInt(7));
                                CompetitorFragment competitorFragment = new CompetitorFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("esm", ps.getEsm());
                                bundle.putInt("egp", ps.getEgp());
                                bundle.putInt("fabrics1", ps.getFabrics1());
                                bundle.putInt("fabrics2", ps.getFabrics2());
                                bundle.putInt("money", ps.getMoney());
                                competitorFragment.setArguments(bundle);
                                competitorFragment.show(getFragmentManager(), "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

        update();

        return view;
    }

    private void update(){
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
            }
        });
        SocketConnector.getSocket().emit("get_player_info", new Object[]{GameStateHandler.getPlayer().getId()}, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("===get_player_info===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = ((JSONArray) args[0]).getJSONArray(8);
                            System.out.println(jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                buttons[i].setText(jsonArray.getString(i));
                                buttons[i].setVisibility(View.VISIBLE);
                            }
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
