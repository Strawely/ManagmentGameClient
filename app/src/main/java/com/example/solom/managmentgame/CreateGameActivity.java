package com.example.solom.managmentgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateGameActivity extends Activity {

    private String[] numOfPlayers ={"2","3","4"};
    private String[] twoToFive={"2","3","4","5"};
    //спинер выбора игроков
    Spinner spinner;
    //спинер фабрик
    Spinner spinnerfab;
    //спинер автофабрик
    Spinner spinnerAfab;
    Spinner spinnerESM;
    Spinner spinnerEGP;
    EditText gameNameEditText;
    EditText money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        spinner = findViewById(R.id.playersSpinner);
        spinnerfab = findViewById(R.id.fabrics1Spinner);
        spinnerAfab = findViewById(R.id.fabrics2Spinner);
        spinnerESM = findViewById(R.id.esmSpinner);
        spinnerEGP = findViewById(R.id.egpSpinner);
        money= findViewById(R.id.moneyEditText);
        gameNameEditText = findViewById(R.id.gameNameEditText);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numOfPlayers);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        //адаптер от 2 до 5
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, twoToFive);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinnerfab.setAdapter(adapter1);
        spinnerAfab.setAdapter(adapter1);
        spinnerEGP.setAdapter(adapter1);
        spinnerESM.setAdapter(adapter1);

    }

    public void createGameOnClick(View view){
        try{
            int plNum=Integer.parseInt(spinner.getSelectedItem().toString());
            int fab=Integer.parseInt(spinnerfab.getSelectedItem().toString());
            int aFab=Integer.parseInt(spinnerAfab.getSelectedItem().toString());
            int eGP=Integer.parseInt(spinnerEGP.getSelectedItem().toString());
            int eSM=Integer.parseInt(spinnerESM.getSelectedItem().toString());
            String name = gameNameEditText.getText().toString();

            int moneyInGame=Integer.parseInt(money.getText().toString());
            Object arrObj[]={GameStateHandler.getPlayer().getId(),SocketConnector.getSocket().id(),name, eSM,eGP,moneyInGame,fab,aFab,plNum};
            SocketConnector.getSocket().emit("create_game",arrObj);
            Intent intent = new Intent(this, PlayersWaitActivity.class);
            startActivity(intent);
            finish();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    }

