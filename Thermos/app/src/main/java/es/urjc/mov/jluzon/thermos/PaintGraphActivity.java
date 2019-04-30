package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PaintGraphActivity extends AppCompatActivity {
    private boolean mExternalStorageAvaiable = false;
    private String state = Environment.getExternalStorageState();
    private final String myDir = PaintGraph.getDir();
    final float txtSize = 30;
    final String [] xeje= new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24"};
    private String myPc;
    private GraphView graph;
    BarGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_graph);
        graph = findViewById(R.id.graph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(xeje);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        series = new BarGraphSeries<>();
        series.setSpacing(-100);
        Intent paint = getIntent();
        Bundle pcInfo = paint.getExtras();

        if(pcInfo != null){
            myPc = pcInfo.getString("pc");
        }

        setScreen();
    }

    private void setScreen(){
        LinearLayout lay = findViewById(R.id.graphLayout);
        TextView txt = new TextView(PaintGraphActivity.this);
        txt.setText("PC IP: "+myPc);
        txt.setTextColor(Color.BLUE);
        txt.setBackgroundColor(Color.WHITE);
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(txtSize);
        txt.setPadding(0,40,0,40);
        lay.addView(txt);
        readPcThemps();
    }

    private void checkdExternalStorageRead(){
        if (Environment.MEDIA_MOUNTED.equals(state)){
            //podemos leer
            mExternalStorageAvaiable = true;
        }else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //Podemos leer
            mExternalStorageAvaiable = true;
        }
    }

    private void readPcThemps() {
        checkdExternalStorageRead();
        if (mExternalStorageAvaiable) {
            File dir = getExternalFilesDir(myDir);
            File file = new File(dir, myPc);
            int x = 0;
            try {
                FileReader f = new FileReader(file);
                BufferedReader br = new BufferedReader(f);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    addPointGraph(x,Integer.parseInt(strLine)/1000);
                    x=x+1;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPointGraph(int x, int y){
        Log.d("OVER","X: "+x+" Y: "+y);
        DataPoint d = new DataPoint(x,y);
        series.appendData(d,true,100);
        series.setColor(Color.GREEN);
        if(y>35){
            series.setColor(Color.RED);
        }
        graph.addSeries(series);
    }
}

