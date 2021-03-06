package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThempActivity extends AppCompatActivity {
    private String themp;
    private String pc;
    private LinearLayout lay;
    final float THMEPSIZEPORTRAIT = 80;
    final float THMEPSIZELANDING = 40;
    final float IPSIZEPORTRAIT = 50;
    final float IPSIZELANDING = 25;
    final float NOTFOUENDPORTRAIT = 110;
    final float NOTFOUENDLANDING = 70;
    private Button showGraph;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themp);
        Resources rso = getResources();

        orientation = rso.getConfiguration().orientation;

        Intent paint = getIntent();
        Bundle thempInfo = paint.getExtras();
        lay = findViewById(R.id.thempLayout);
        showGraph = findViewById(R.id.show);

        if(thempInfo != null){
            themp = thempInfo.getString("themp");
            pc = thempInfo.getString("pc");
        }

        if(themp!= null){
            paintThemp();
        }else{
            paintError();
        }
    }

    private void paintThemp(){
        String th = "La temperatura actual es: "+themp+"ºC";
        String ip = "Te has conectado al PC: "+pc;
        TextView txth = new TextView(ThempActivity.this);
        TextView iptxt = new TextView(ThempActivity.this);
        showGraph.setVisibility(View.VISIBLE);
        txth.setText(th);
        iptxt.setText(ip);
        txth.setPadding(0,200,0,40);
        txth.setGravity(Gravity.CENTER);
        txth.setTextColor(Color.BLUE);
        txth.setBackgroundColor(Color.WHITE);
        iptxt.setPadding(0,40,0,100);
        iptxt.setGravity(Gravity.CENTER);
        iptxt.setTextColor(Color.BLUE);
        iptxt.setBackgroundColor(Color.WHITE);

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            txth.setTextSize(THMEPSIZEPORTRAIT);
            iptxt.setTextSize(IPSIZEPORTRAIT);
        }else{
            txth.setTextSize(THMEPSIZELANDING);
            iptxt.setTextSize(IPSIZELANDING);
        }

        lay.addView(txth);
        lay.addView(iptxt);
    }

    private void paintError(){
        showGraph.setVisibility(View.INVISIBLE);
        String t = "PC not Found \n Go back and try again";
        TextView txt = new TextView(ThempActivity.this);
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.BLACK);
        txt.setGravity(Gravity.CENTER);
        txt.setText(t);
        txt.setPadding(0,70,0,30);
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            txt.setTextSize(NOTFOUENDPORTRAIT);
        }else{
            txt.setTextSize(NOTFOUENDLANDING);
        }

        lay.addView(txt);
    }

    public void goGraph(View b){
        Intent paint = new Intent(ThempActivity.this,PaintGraphActivity.class);
        Bundle pcInfo = new Bundle();
        pcInfo.putString("pc",pc);
        paint.putExtras(pcInfo);
        paint.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(paint);
    }
}
