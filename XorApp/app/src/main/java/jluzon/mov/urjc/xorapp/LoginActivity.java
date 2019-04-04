package jluzon.mov.urjc.xorapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void startButton(View v){
        Intent game = new Intent(LoginActivity.this,MainActivity.class);
        Bundle statusInfo = new Bundle();
        TextView name = findViewById(R.id.name);
        String player = name.getText()+"";
        if(!player.equals("") && player.length() < 8){
            statusInfo.putString("name",name.getText()+"");
            game.putExtras(statusInfo);
            startActivity(game);
        }else{
            int time = Toast.LENGTH_SHORT;
            String txt = "No valid name (7 letters max)";
            Toast msg = Toast.makeText(LoginActivity.this,txt,time);
            msg.show();
        }
    }
}

