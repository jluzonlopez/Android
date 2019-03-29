package jluzon.mov.urjc.xorapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
        statusInfo.putString("name",name.getText()+"");
        game.putExtras(statusInfo);
        startActivity(game);
    }
}

