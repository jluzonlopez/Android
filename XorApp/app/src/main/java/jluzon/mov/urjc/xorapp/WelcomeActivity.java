package jluzon.mov.urjc.xorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void welcomeButton(View b){
        Intent level = new Intent(WelcomeActivity.this,MainActivity.class);
        level.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(level);
    }
}
