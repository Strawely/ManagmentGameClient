package com.example.solom.managmentgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.nkzawa.socketio.client.Socket;

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
    EditText money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        spinner = (Spinner) findViewById(R.id.numOfPlayers);
        spinnerfab = (Spinner) findViewById(R.id.numOfFact);
        spinnerAfab = (Spinner) findViewById(R.id.numOfFactA);
        spinnerESM = (Spinner) findViewById(R.id.numOfESM);
        spinnerEGP = (Spinner) findViewById(R.id.numOfEGP);
        money=(EditText) findViewById(R.id.editText4);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numOfPlayers);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        //адаптер от 2 до 5
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, twoToFive);
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
            Socket socket=SocketHandler.getSocket();

            int plNum=Integer.parseInt(spinner.getSelectedItem().toString());
            int fab=Integer.parseInt(spinnerfab.getSelectedItem().toString());
            int aFab=Integer.parseInt(spinnerAfab.getSelectedItem().toString());
            int eGP=Integer.parseInt(spinnerEGP.getSelectedItem().toString());
            int eSM=Integer.parseInt(spinnerESM.getSelectedItem().toString());

            int moneyInGame=Integer.parseInt(money.getText().toString());
            Object arrObj[]={GameStateHandler.getPlayer().getId(),SocketConnector.getSocket().id(),eSM,eGP,moneyInGame,fab,aFab,plNum};
            SocketConnector.getSocket().emit("create_game",arrObj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
