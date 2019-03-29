package jluzon.mov.urjc.xorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {
    final float scalator = 2;
    final float textSize = 20;
    final float pointSize = 70;

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
        TextView points;
        int vPoints = 0;

        if(statusInfo != null){
            passLvlsArray = statusInfo.getBooleanArray("passed");
        }

        points = new TextView(StatusActivity.this);

        for (int i=0;i<passLvlsArray.length;i++){
            txt = new TextView(StatusActivity.this);
            img = new ImageView(StatusActivity.this);
            txt.setText("Level "+i);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(0,50,0,50);
            txt.setTextSize(textSize);
            if(passLvlsArray[i]) {
                img.setImageResource(R.drawable.ic_check);
                vPoints += 10;
            }else{
                img.setImageResource(R.drawable.ic_death);
            }
            img.setScaleX(scalator);
            img.setScaleY(scalator);
            lay.addView(txt);
            lay.addView(img);
        }

        points.setText("Puntuación: "+vPoints);
        points.setGravity(Gravity.CENTER);
        points.setTextSize(pointSize);
        points.setPadding(0,100,0,50);
        lay.addView(points);
    }
}
