package jluzon.mov.urjc.xorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Intent status = getIntent();
        Bundle statusInfo = status.getExtras();

        boolean passLvlsArray[] = null;
        LinearLayout lay = findViewById(R.id.statusLayout);
        ImageView img;
        TextView txt;

        if(statusInfo != null){
            passLvlsArray = statusInfo.getBooleanArray("passed");
        }

        for (int i=0;i<passLvlsArray.length;i++){
            txt = new TextView(StatusActivity.this);
            img = new ImageView(StatusActivity.this);
            txt.setText("Level "+i);
            txt.setGravity(Gravity.CENTER);
            if(passLvlsArray[i]) {
                img.setImageResource(R.drawable.ic_check);
            }else{
                img.setImageResource(R.drawable.ic_death);
            }
            lay.addView(txt);
            lay.addView(img);
        }
    }
}
