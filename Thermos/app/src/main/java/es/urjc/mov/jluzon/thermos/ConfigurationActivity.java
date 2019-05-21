package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigurationActivity extends AppCompatActivity {
    private Intent service;
    private String currentCFG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        service = new Intent(ConfigurationActivity.this,ThermoService.class);
        stopService(service);
        checkCFG();
    }

    private void checkCFG(){
        String [] cfg = Utility.readCfg(ConfigurationActivity.this);
        currentCFG = "   --------";
        if(cfg[Utility.IPPOS] != null){
            currentCFG = "  IP: "+cfg[Utility.IPPOS]+ ", Alert: "+cfg[Utility.ALERTPOS];
        }

        TextView myCfg = findViewById(R.id.currentCFG);
        myCfg.setText(currentCFG);
    }

    public void setConfiguration(View hb){
        EditText edPcIP = findViewById(R.id.editIP);
        EditText edAlert = findViewById(R.id.editAlert);
        String pcIP = edPcIP.getText()+"";
        String alert = edAlert.getText()+"";
        String txt = "Wrong IP or alert";

        if(Utility.checkIP(pcIP) && Utility.checkAlert(alert)){
            Utility.setCfg(ConfigurationActivity.this,pcIP,alert);
            txt = "Saved";
        }

        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(ConfigurationActivity.this,txt,time);
        msg.show();

        if(txt.equals("Saved")){
            finish();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        startService(service);
    }
}
