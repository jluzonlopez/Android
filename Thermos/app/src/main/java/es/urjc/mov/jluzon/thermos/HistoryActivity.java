package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class HistoryActivity extends AppCompatActivity {
    File [] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        checkPcFiles();
        for(int i=0;i<files.length;i++){
            printPcs(files[i].getName());
        }
    }

    private void checkPcFiles(){
        files = PaintGraph.readFiles(HistoryActivity.this);
    }

    private void printPcs(String pcIp){
        LinearLayout lay = findViewById(R.id.historyLayout);
        LinearLayout pcLay = new LinearLayout(HistoryActivity.this);
        pcLay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(pcLay);
        ImageView img = new ImageView(HistoryActivity.this);
        Button txt = new Button(HistoryActivity.this);
        img.setImageResource(R.drawable.ic_computer_black_24dp);
        img.setScaleX(3);
        img.setScaleY(3);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setPadding(0,40,0,15);
        txt.setPadding(0,20,0,0);
        txt.setText(pcIp);
        txt.setGravity(Gravity.CENTER);
        txt.setBackgroundColor(Color.TRANSPARENT);
        txt.setOnClickListener(new PcClick());
        pcLay.addView(img);
        pcLay.addView(txt);
    }

    public class PcClick implements View.OnClickListener{
        public void onClick(View b){
            Intent paint = new Intent(HistoryActivity.this,PaintGraphActivity.class);
            Bundle pcInfo = new Bundle();
            Button but = (Button)b;
            String butonString = but.getText().toString();
            pcInfo.putString("pc",butonString);
            paint.putExtras(pcInfo);
            paint.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(paint);
        }
    }
}
