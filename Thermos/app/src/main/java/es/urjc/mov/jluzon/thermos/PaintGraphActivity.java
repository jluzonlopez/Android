package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PaintGraphActivity extends AppCompatActivity {
    private boolean mExternalStorageAvaiable = false;
    private String state = Environment.getExternalStorageState();
    private final String myDir = PaintGraph.getDir();
    private final String title = "Last 24h";
    final float txtSize = 30;
    final String [] xEje = new String[PaintGraph.maxTemperatures];
    private String myPc;
    private ArrayList<String> temperatures;
    private EditText edTxt;
    private int alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_graph);
        Intent paint = getIntent();
        Bundle pcInfo = paint.getExtras();

        if(pcInfo != null){
            myPc = pcInfo.getString("pc");
        }
        alert = 100;

        for(int i=0;i<PaintGraph.maxTemperatures;i++){
            xEje[i] = (i+1)+"";
        }

        setScreen();
    }

    private void setScreen(){
        LinearLayout lay = findViewById(R.id.graphLayout);
        TextView txt = new TextView(PaintGraphActivity.this);
        edTxt = new EditText(PaintGraphActivity.this);
        Button setTh = new Button(PaintGraphActivity.this);
        edTxt.setHint("Set Alert");
        setTh.setText("Submit");
        setTh.setOnClickListener(new butGraph());
        setTh.setBackgroundColor(Color.TRANSPARENT);
        txt.setText("PC IP: "+myPc);
        txt.setTextColor(Color.BLUE);
        txt.setBackgroundColor(Color.WHITE);
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(txtSize);
        txt.setPadding(0,40,0,40);
        edTxt.setPadding(0,35,0,20);
        setTh.setPadding(0,20,0,0);
        lay.addView(txt);
        lay.addView(edTxt);
        lay.addView(setTh);
        setGraph();
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
        temperatures = new ArrayList<>();
        Log.d("OVER","SIZE: "+temperatures.size());
        if (mExternalStorageAvaiable) {
            File dir = getExternalFilesDir(myDir);
            File file = new File(dir, myPc);
            try {
                FileReader f = new FileReader(file);
                BufferedReader br = new BufferedReader(f);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    temperatures.add(strLine);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void paintGraph(){
        GraphView graph = findViewById(R.id.graph);
        graph.removeAllSeries();


        DataPoint[] myData = new DataPoint[temperatures.size()];
        for(int i=0;i<temperatures.size();i++){
            DataPoint p = new DataPoint(i,Integer.parseInt(temperatures.get(i))/1000);
            myData[i] = p;
        }

        Log.d("OVER","Data length: "+myData.length);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(myData);


        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);

        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY()>alert){
                    return Color.RED;
                }
                return Color.GREEN;
            }
        });

        series.setTitle(title);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        series.setSpacing(5);
        series.setDataWidth(0.7);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);
    }

    private void setGraph(){
        readPcThemps();
        paintGraph();
    }

    public class butGraph implements View.OnClickListener{
        public void onClick(View b){
            alert = Integer.parseInt(edTxt.getText()+"");
            setGraph();
        }
    }

}