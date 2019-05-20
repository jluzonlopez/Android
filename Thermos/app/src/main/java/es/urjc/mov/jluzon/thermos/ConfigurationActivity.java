package es.urjc.mov.jluzon.thermos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
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
    }
}
