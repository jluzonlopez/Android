package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ActivePcsActivity extends AppCompatActivity {
    final static int SERVERPORT = 25050;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_pcs);
    }


    private String getThemp(String pc){
        String pcThemp = Utility.connect(pc);
        if(pcThemp != null){
            Utility.update(ActivePcsActivity.this,pcThemp,pc);
        }
        return pcThemp;
    }

    public void connectPc(View b){
        String thmp;
        EditText pcIP = findViewById(R.id.ipPc);
        String pc = pcIP.getText().toString();
        if(Utility.checkIP(pc)) {
            thmp = getThemp(pc);
            Intent themp = new Intent(ActivePcsActivity.this, ThempActivity.class);
            Bundle thempInfo = new Bundle();
            thempInfo.putString("themp", thmp);
            thempInfo.putString("pc", pc);
            themp.putExtras(thempInfo);
            startActivity(themp);
            return;
        }
        String txt = "No allowed IP";
        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(ActivePcsActivity.this,txt,time);
        msg.show();
    }
}
