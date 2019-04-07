package jluzon.mov.urjc.xorapp;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TopScoresActivity extends AppCompatActivity {
    private boolean mExternalStorageAvaiable = false;
    private String state = Environment.getExternalStorageState();
    private String myDir = TopScores.getMyDir();
    private String myFile = TopScores.getMyFile();
    private int indexName = TopScores.getIndexName();
    private int indexTimes = TopScores.getIndexTimes();
    private int indexScore = TopScores.getIndexScore();
    final float textSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        readExternalStorage();
    }

    private void checkdExternalStorageRead(){
        if (Environment.MEDIA_MOUNTED.equals(state)){
            //podemos leer y escribir
            mExternalStorageAvaiable = true;
        }else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //Podemos leer
            mExternalStorageAvaiable = true;
        }
    }

    private void readExternalStorage(){
        checkdExternalStorageRead();
        int count = 1;
        if(mExternalStorageAvaiable){
            File dir = getExternalFilesDir(myDir);
            File file = new File(dir,myFile);
            try{
                FileReader fil = new FileReader(file);
                BufferedReader br = new BufferedReader(fil);
                String strLine;
                while((strLine = br.readLine()) != null){
                    writeScores(count,strLine);
                    count++;
                }
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void writeScores(int cnt,String s){
        String playerTxt;
        String times="";
        String player[] = s.split(",");

        for(int i=1;i<=indexTimes;i++){
            times = times+" Time-"+i+": "+ player[i]+"s";
        }

        playerTxt = cnt+". "+player[indexName]+times+" SCORE: "+player[indexScore];

        LinearLayout lay = findViewById(R.id.topScoresLayout);
        TextView txt = new TextView(TopScoresActivity.this);
        txt.setTextColor(Color.BLACK);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(0,0,0,25);
        txt.setTextSize(textSize);
        txt.setText(playerTxt);
        lay.addView(txt);
    }
}