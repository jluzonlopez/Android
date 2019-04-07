package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public class InfLvl{
        private final int check = 1;
        private final int death = 2;
        private final int next = 3;
        private final int maxLvl = 4;
        private final int maxButt = 4;
        private boolean passLvlsArray[]; //which levels are passed
        private int timeLvls[]; //time off passed levels
        private int passLvls; //number of games passed
        private int imgLvl; //current image number
        private Level lv;
        private LevelFactory lvFc;
        private View[] imgView; //0 endgame, 1 check, 2 death, 3 next
        private Switch[] chkB;
        private int imgAry[];
        private ImageView img;
        private int menArray[];
        private TextView playerName;
        private String player;
        private int score;
        private TextView levelName;
        private Chronometer chrono;

        private InfLvl(){
            Resources rso = getResources();
            imgLvl = 0;
            passLvls = 0;
            score = 0;
            passLvlsArray = new boolean[maxLvl];
            timeLvls = new int[maxLvl];
            imgView = new View[4];
            chkB = new Switch[4];
            imgAry = new int[maxLvl];
            menArray = new int[maxLvl];
            img = findViewById(R.id.lvlimg);
            playerName = findViewById(R.id.name);
            levelName = findViewById(R.id.lname);
            chrono = findViewById(R.id.chrono);
            lvFc = new LevelFactory(img,imgAry,chkB);

            for(int i=0;i<4;i++){
                int id = rso.getIdentifier("game"+i,"id",getPackageName());
                imgView[i] = findViewById(id);
            }

            for(int i=0; i<4; i++){
                int id = rso.getIdentifier("ent"+i,"id",getPackageName());
                chkB[i] = findViewById(id);
            }

            String lvl ="ic_nivel";
            for(int i=0;i<maxLvl;i++){
                int id = rso.getIdentifier(lvl+i+"v1","drawable",getPackageName());
                imgAry[i] = id;
            }

            for(int i=0;i<maxLvl;i++){
                int id = rso.getIdentifier("nivel"+i,"id",getPackageName());
                menArray[i] = id;
            }

            for(int i=0;i<maxLvl;i++) {
                passLvlsArray[i] = false;
            }
        }

        private void setLv(Level lv) {
            this.lv = lv;
        }

        private int getImglvl(){
            return imgLvl;
        }

        private void setLvlImg(int imgl){
            imgLvl = imgl;
        }

        private boolean getPassLvlArray(){
            return passLvlsArray[imgLvl];
        }

        private int getPassLvl() {
            return passLvls;
        }

        private void setLvlPass(){
            passLvlsArray[imgLvl] = true;
            passLvls=passLvls+1;
        }

        private void setTimeLvl(int t){
            timeLvls[imgLvl] = t;
        }

        private boolean[] getBoolBut(Switch[] chb){
            boolean chkBool[] = new boolean[4];
            for(int i=0;i<4;i++) {
                chkBool[i] = chb[i].isChecked();
            }
            return chkBool;
        }

        private void setAllGone(){
            for(int i=0;i<4;i++){
                imgView[i].setVisibility(View.GONE);
            }
        }

        private void setAllFalse(Switch[] chb){
            for(int i = 0; i < chb.length; i++){
                chb[i].setChecked(false);
            }
        }

        private void setChButtons(boolean [] buttSt){
            for(int i=0;i<maxButt;i++){
                chkB[i].setChecked(buttSt[i]);
            }
        }

        private boolean getMenuArray(int id){
            for(int i=0;i<maxLvl;i++) {
                if(menArray[i] == id){
                    imgLvl = i;
                    return true;
                }
            }
            return false;
        }

        private int[] getViewVis(){
            int[] visibArray = new int[imgView.length];
            for(int i=0;i<imgView.length;i++) {
                visibArray[i] = imgView[i].getVisibility();
            }
            return visibArray;
        }

        private void setVisRes(int[] viewVis){
            for(int i=0;i<imgView.length;i++) {
                imgView[i].setVisibility(viewVis[i]);
            }
        }

        private void setScore(int sc){
            score = sc;
        }

        private void setSolution(boolean p){
            if(p){
                imgView[check].setVisibility(View.VISIBLE);
                imgView[death].setVisibility(View.GONE);
                imgView[next].setVisibility(View.VISIBLE);
                return;
            }
            imgView[check].setVisibility(View.GONE);
            imgView[death].setVisibility(View.VISIBLE);
            imgView[next].setVisibility(View.GONE);
        }

        //para poder coger las cosas del test
        public int getCurrentLevel(){
            return imgLvl;
        }

        public void setPlayerName(String s){
            playerName.setText(s);
        }
    }

    InfLvl levelViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelViews = new InfLvl();
        Intent status = getIntent();
        Bundle nameInfo = status.getExtras();
        String name = nameInfo.getString("name");
        levelViews.player = name;
        name = "Player: "+ name;
        levelViews.playerName.setText(name);
        if(savedInstanceState != null){
            int lvlImg = savedInstanceState.getInt("lvl");
            boolean [] passedLvl = savedInstanceState.getBooleanArray("passedLvl");
            int [] timeLvl = savedInstanceState.getIntArray("timeLvl");
            boolean [] chbStatus = savedInstanceState.getBooleanArray("chbStatus");
            int [] resView = savedInstanceState.getIntArray("resViews");
            long time = savedInstanceState.getLong("time");
            levelViews.setLvlImg(lvlImg);
            levelViews.passLvlsArray = passedLvl;
            levelViews.timeLvls = timeLvl;
            levelViews.setChButtons(chbStatus);
            levelViews.setVisRes(resView);
            setLevelRecover(levelViews.getImglvl(),time);
            return;
        }
        setLevel(levelViews.getImglvl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.conf,menu);
        return true;
    }

    private String writeTimes(){
        String s = "";
        for(int i=0;i<levelViews.timeLvls.length;i++){
            s = s+" Time-"+i+": "+ levelViews.timeLvls[i]+"s ";
        }
        return s;
    }

    private void setLevelRecover(int lv, long t){
        Level l = levelViews.lvFc.produce(lv);
        l.initLevel();
        levelViews.setLv(l);
        levelViews.setLvlImg(lv);
        String txt = "Level "+levelViews.getImglvl();
        levelViews.levelName.setText(txt);
        if(levelViews.imgView[levelViews.next].getVisibility() == View.VISIBLE){
            t = 0;
        }
        levelViews.chrono.setBase(SystemClock.elapsedRealtime()- t);
        if(levelViews.imgView[levelViews.next].getVisibility() == View.GONE){
            levelViews.chrono.start();
        }
    }

    private void setLevel(int lv){
       setLevelRecover(lv,0);
       levelViews.setAllGone();
       levelViews.chrono.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        View b;
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.help:
                b = findViewById(R.id.help);
                helpButton(b);
                return true;
            case R.id.passed:
                b = findViewById(R.id.passed);
                showPassed(b);
                return true;
            case R.id.topscores:
                b = findViewById(R.id.topscores);
                showTopScores(b);
                return true;
            case R.id.save:
                b = findViewById(R.id.save);
                saveGame(b);
                return true;
            default:
                if(levelViews.getMenuArray(itemId)){
                    setLevel(levelViews.imgLvl);
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }

    public void solutionButton(View v){
        String txt = "";
        boolean[] boolChk;
        long timeStopped;

        boolChk = levelViews.getBoolBut(levelViews.chkB);

        if(!levelViews.lv.badOut(boolChk)){
            txt += "Estas muerto, intentalo de nuevo";
            levelViews.setSolution(false);
        }else if(levelViews.lv.goodOut(boolChk)){
            txt += "Salida correcta, enhorabuena!!";
            checkLevel();
            levelViews.setSolution(true);
            timeStopped = SystemClock.elapsedRealtime() - levelViews.chrono.getBase();
            levelViews.chrono.stop();
            levelViews.setTimeLvl((int)timeStopped/1000);
            levelViews.setScore(levelViews.lv.calcScore(levelViews.timeLvls));
        }else{
            txt += "Las 2 a 0";
        }

        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(MainActivity.this,txt,time);
        msg.show();

        levelViews.setAllFalse(levelViews.chkB);
    }

    //write in external storage
    private void saveGame(View hb){
        String times = writeTimes();
        TopScores.writetoFile(MainActivity.this,levelViews.timeLvls,levelViews.player,levelViews.score);
    }

    private void helpButton(View hb){
        Intent help = new Intent(MainActivity.this,HelpActivity.class);
        help.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(help);
    }

    private void showPassed(View hb){
        Intent status = new Intent(MainActivity.this,StatusActivity.class);
        status.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle statusInfo = new Bundle();
        statusInfo.putBooleanArray("passed",levelViews.passLvlsArray);
        statusInfo.putIntArray("times",levelViews.timeLvls);
        statusInfo.putInt("score",levelViews.score);
        status.putExtras(statusInfo);
        startActivity(status);
    }

    private void showTopScores(View hb){
        Intent scores = new Intent(MainActivity.this,TopScoresActivity.class);
        scores.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(scores);
    }

    private int nextL(){
        int nl = levelViews.getImglvl();
        return (nl+1)%(levelViews.maxLvl);
    }

    private void checkLevel(){
        if(!levelViews.getPassLvlArray()){
            levelViews.setLvlPass();
        }
    }

    public void nextLevel(View b){
        int lv;
        int time = Toast.LENGTH_SHORT;

        lv = levelViews.getPassLvl();
        if(lv == levelViews.maxLvl){
            levelViews.setAllGone();
            levelViews.imgView[0].setVisibility(View.VISIBLE);
            Toast msg = Toast.makeText(MainActivity.this,"Te pasaste el juego",time);
            msg.show();
        }else{
            setLevel(nextL());
        }
    }

    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        boolean[] checkButtStatus = levelViews.getBoolBut(levelViews.chkB);
        long timeStopped = SystemClock.elapsedRealtime() - levelViews.chrono.getBase();
        state.putBooleanArray("chbStatus",checkButtStatus);
        state.putBooleanArray("passedLvl",levelViews.passLvlsArray); // passed levels array
        state.putIntArray("timeLvl",levelViews.timeLvls); // time of passed levels
        state.putInt("lvl",levelViews.imgLvl); // num level
        state.putIntArray("resViews",levelViews.getViewVis()); // end game views
        state.putLong("time",timeStopped);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.v("OVER","OnStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("OVER","OnResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.v("OVER","OnPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.v("OVER","OnStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("OVER","OnDestroy");
    }
}
