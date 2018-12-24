package com.example.solom.managmentgame;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.solom.managmentgame.dataLayer.GameStateHandler;
import com.example.solom.managmentgame.dataLayer.SocketConnector;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private Handler handler = new Handler();
    private Context context = this;

    private EsmFragment esmFragment = new EsmFragment();
    private ProductionFragment productionFragment = new ProductionFragment();
    private EgpFragment egpFragment = new EgpFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        initSocketCallbacks();

        if(GameStateHandler.getGame() != null){
            Intent intent = getIntent();
            GameStateHandler.getGame().setMarketLvl(intent.getIntExtra("marketLvl", 3));
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.esm);
        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);

        SocketConnector.updatePlayerState();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EsmFragment(), "Банк");
        adapter.addFragment(new FabricFragment(), "Фабрики");
        adapter.addFragment(new InfoFragment(), "Инфо");
        viewPager.setAdapter(adapter);
    }

    private void initSocketCallbacks(){
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        SocketConnector.getSocket().on("esm_orders_approved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===Called esm_orders_approved===");
                JSONArray ordersApproved = (JSONArray) args[0];
                System.out.println(ordersApproved.toString());
                boolean isApproved = false;
                for (int i = 0; i < ordersApproved.length(); i++){
                    try {
                        JSONArray order = ordersApproved.getJSONArray(i);
                        if(order.getInt(1) == GameStateHandler.getPlayer().getId()){
                            isApproved = true;
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (isApproved){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Запрос на ЕСМ удовлетворён", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        SocketConnector.getSocket().on("egp_orders_approved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===Called egp_orders_approved===");
                JSONArray ordersApproved = (JSONArray) args[0];
                boolean isApproved = false;
                for (int i = 0; i < ordersApproved.length(); i++){
                    try {
                        JSONArray order = ordersApproved.getJSONArray(i);
                        if(order.getInt(1) == GameStateHandler.getPlayer().getId()){
                            isApproved = true;
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (isApproved){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Запрос на ЕГП удовлетворён", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        SocketConnector.getSocket().on("wait_production", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_production===");
                Fragment productionFragment = new ProductionFragment();
                adapter.changeFragment(0, productionFragment, "Банк");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.hammer);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("produced", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===produced===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format("Произведено %d ЕГП за $%d", (Integer) args[0], (Integer) args[1]), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_egp_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_egp===");
                adapter.changeFragment(0, egpFragment, "Банк");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.egp);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("paid_percents", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===paid_percents===");
                JSONArray jsonArray = (JSONArray) args[0];
                int sum = 0;
                for (int i = 0; i < jsonArray.length(); i++){
                    try {
                        JSONArray tuple = jsonArray.getJSONArray(i);
                        if(tuple.getInt(0) == GameStateHandler.getPlayer().getId()){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(context, String.format("Выплачены проценты по ссудам: %d $", tuple.getInt(1)), Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        SocketConnector.getSocket().on("wait_credit_payoff", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_credit_payoff===");
                SocketConnector.sendCreditPayoff();
            }
        });

        SocketConnector.getSocket().on("paid_credit_sum", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===paid_credit_sum===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format("Выплаченно ссуд на сумму: %d $", ((Integer) args[0]) == null? 0 : (Integer)args[0]), Toast.LENGTH_LONG).show();
                        adapter.changeFragment(0, new CreditFragment(), "Банк");
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.bank);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_take_credit", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_take_credit===");


            }
        });

        SocketConnector.getSocket().on("taken_credit", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===taken_credit===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, String.format("Взята ссуда: %d $", (Integer) args[0]), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_build_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_build_request===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.changeFragment(0, new BuildFragment(), "Строительство");
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.building);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_upgrade_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_upgrade_request===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.changeFragment(0, new UpgradeFragment(), "Автоматизация");
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.bot);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("wait_next_turn", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===wait_next_turn===");
                SocketConnector.sendNextTurn();
            }
        });

        SocketConnector.getSocket().on("new_market_lvl", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===new_market_lvl===");
                GameStateHandler.getGame().setMarketLvl((Integer)args[0]);
                adapter.changeFragment(0, esmFragment, "Банк");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setIcon(R.drawable.esm);
                        tabLayout.getTabAt(1).setIcon(R.drawable.fabric);
                        tabLayout.getTabAt(2).setIcon(R.drawable.default_avatar);
                    }
                });
            }
        });

        SocketConnector.getSocket().on("bankrupt_left", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===bankrupt_left===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BankruptFragment bankruptFragment = new BankruptFragment();
                        bankruptFragment.show(getSupportFragmentManager(), "Банкрот");
                    }
                });
            }
        });

        SocketConnector.getSocket().on("final_scores", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("===final_scores===");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ScoresFragment scoresFragment = new ScoresFragment();
                        scoresFragment.show(getSupportFragmentManager(), "Счёт");
                    }
                });
            }
        });
    }
}
