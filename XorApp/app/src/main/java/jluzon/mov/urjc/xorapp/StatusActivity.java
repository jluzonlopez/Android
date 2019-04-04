package jluzon.mov.urjc.xorapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {
    final float scalator = 2;
    final float textSize = 25;
    final float pointSize = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Intent status = getIntent();
        Bundle statusInfo = status.getExtras();


        boolean passLvlsArray[] = null;
        int timeLvlsArray[] = null;
        int score = 0;
        LinearLayout lay = findViewById(R.id.statusLayout);
        ImageView img;
        TextView txt;
        TextView points;

        if(statusInfo != null){
            passLvlsArray = statusInfo.getBooleanArray("passed");
            timeLvlsArray = statusInfo.getIntArray("times");
            score = statusInfo.getInt("score");

        }

        points = new TextView(StatusActivity.this);

        for (int i=0;i<passLvlsArray.length;i++){
            txt = new TextView(StatusActivity.this);
            img = new ImageView(StatusActivity.this);
            txt.setText("Level: "+i+" ------ Time: "+timeLvlsArray[i]+"s");
            txt.setTextColor(Color.BLACK);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(0,40,0,40);
            txt.setTextSize(textSize);
            if(passLvlsArray[i]) {
                img.setImageResource(R.drawable.ic_check);
            }else{
                img.setImageResource(R.drawable.ic_death);
            }
            img.setScaleX(scalator);
            img.setScaleY(scalator);
            lay.addView(txt);
            lay.addView(img);
        }

        points.setText("SCORE: "+score);
        points.setTextColor(Color.BLACK);
        points.setGravity(Gravity.CENTER);
        points.setTextSize(pointSize);
        points.setPadding(0,80,0,50);
        lay.addView(points);
    }
}
