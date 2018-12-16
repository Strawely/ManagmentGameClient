package com.example.solom.managmentgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class AvatarChoiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_choice);
    }

    public void onSaveClick(View view) {
        CheckBox[] checkBoxes = {findViewById(R.id.avatar0CheckBox), findViewById(R.id.avatar1CheckBox),
                findViewById(R.id.avatar2CheckBox), findViewById(R.id.avatar3CheckBox),
                findViewById(R.id.avatar4CheckBox), findViewById(R.id.avatar5CheckBox),
                findViewById(R.id.avatar6CheckBox), findViewById(R.id.avatar7CheckBox)};
        int result = -1;
        for (int i = 0; i < checkBoxes.length; i++){
            if(checkBoxes[i].isChecked()){
                if(result != -1){
                    result = -1;
                    break;
                }
                else {
                    result = i;
                }
            }
        }
        if(result == -1){
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            return;
        }
        GameStateHandler.setPlayer(new Player(0, "", result));
        finish();
    }
}
