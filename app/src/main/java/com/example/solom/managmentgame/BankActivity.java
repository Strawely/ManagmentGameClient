package com.example.solom.managmentgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

public class BankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        SocketConnector.getSocket().on("wait_esm_order", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                setContentView(R.layout.esm_auction_layout);
            }
        });
    }

    public void onEsmRequestClick(View view) {
        EditText priceEditText = findViewById(R.id.esmPriceEditText);
        EditText qtyEditText = findViewById(R.id.esmQtyEditText);
        SocketConnector.sendEsmRequest(Integer.getInteger(qtyEditText.getText().toString()),
                Integer.getInteger(priceEditText.getText().toString()));
    }
}
