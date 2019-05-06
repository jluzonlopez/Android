package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showHistory(View hb){
        Intent history = new Intent(MainActivity.this,HistoryActivity.class);
        history.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(history);
    }

    public void activePcs(View hb){
        Intent activePcs = new Intent(MainActivity.this,ActivePcsActivity.class);
        activePcs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(activePcs);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("OVER","onStop");
        Intent service = new Intent(MainActivity.this,ThermoService.class);
        startService(service);
    }
}
