package jluzon.mov.urjc.xorapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public class InfLvl{
        private final int check = 1;
        private final int death = 2;
        private final int next = 3;
        private final int maxLvl = 4;
        private final int maxButt = 4;
        private boolean passLvlsArray[]; //which levels are passed
        private int passLvls; //number of games passed
        private int imgLvl; //current image number
        private Level lv;
        private LevelFactory lvFc;
        private View[] imgView; //0 endgame, 1 check, 2 death, 3 next
        private Switch[] chkB;
        private int imgAry[];
        private ImageView img;
        private int menArray[];

        private void setLv(Level lv) {
            this.lv = lv;
        }

        private InfLvl(){
            Resources rso = getResources();
            imgLvl = 0;
            passLvls = 0;
            passLvlsArray = new boolean[maxLvl];
            imgView = new View[4];
            chkB = new Switch[4];
            imgAry = new int[maxLvl];
            menArray = new int[maxLvl];
            img = findViewById(R.id.lvlimg);
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

        private boolean getMenArray(int id){
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
                Log.v("OVER",""+viewVis[i]);
                imgView[i].setVisibility(viewVis[i]);
            }
        }

        private int toggleVis(int nm){
            if(imgView[nm].getVisibility() == View.VISIBLE){
                return View.GONE;
            }
            return View.VISIBLE;
        }

        private void setSolutViews(){
            imgView[check].setVisibility(toggleVis(check));
            imgView[death].setVisibility(toggleVis(death));
            imgView[next].setVisibility(toggleVis(next));
        }

        //cambiar estas dos funciones creando valores estaticos para los indices
        private void setDeath(){
            imgView[check].setVisibility(View.GONE);
            imgView[death].setVisibility(View.VISIBLE);
            imgView[next].setVisibility(View.GONE);
        }

        private void setPassed(){
            imgView[check].setVisibility(View.VISIBLE);
            imgView[death].setVisibility(View.GONE);
            imgView[next].setVisibility(View.VISIBLE);
        }

        public int getCurrentLevel(){
            return imgLvl;
        }
    }

    InfLvl levelViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelViews = new InfLvl();
        if(savedInstanceState != null){
            int lvlImg = savedInstanceState.getInt("lvl");
            boolean [] passedLvl = savedInstanceState.getBooleanArray("passedLvl");
            boolean [] chbStatus = savedInstanceState.getBooleanArray("chbStatus");
            int [] resView = savedInstanceState.getIntArray("resViews");
            levelViews.setLvlImg(lvlImg);
            levelViews.passLvlsArray = passedLvl;
            levelViews.setChButtons(chbStatus);
            levelViews.setVisRes(resView);
            setLevelRecover(levelViews.getImglvl());
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

    private void setLevelRecover(int lv){
        Level l = levelViews.lvFc.produce(lv);
        l.initLevel();
        levelViews.setLv(l);
        levelViews.setLvlImg(lv);
    }

    private void setLevel(int lv){
       setLevelRecover(lv);
       levelViews.setAllGone();
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
            default:
                if(levelViews.getMenArray(itemId)){
                    setLevel(levelViews.imgLvl);
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }

    public void solutionButton(View v){
        String txt = "";
        boolean[] boolChk;

        boolChk = levelViews.getBoolBut(levelViews.chkB);

        if(!levelViews.lv.badOut(boolChk)){
            txt += "Estas muerto, intentalo de nuevo";
            levelViews.setDeath();
        }else if(levelViews.lv.goodOut(boolChk)){
            txt += "Salida correcta, enhorabuena!!";
            checkLevel();
            levelViews.setPassed();;
        }else{
            txt += "Las 2 a 0";
        }

        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(MainActivity.this,txt,time);
        msg.show();

        levelViews.setAllFalse(levelViews.chkB);
    }

    private void helpButton(View hb){
        Intent help = new Intent(MainActivity.this,HelpActivity.class);
        startActivity(help);
    }

    private void showPassed(View hb){
        Intent status = new Intent(MainActivity.this,StatusActivity.class);
        Bundle statusInfo = new Bundle();
        statusInfo.putBooleanArray("passed",levelViews.passLvlsArray);
        status.putExtras(statusInfo);
        startActivity(status);
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
        state.putBooleanArray("chbStatus",checkButtStatus);
        state.putBooleanArray("passedLvl",levelViews.passLvlsArray); // passed levels array
        state.putInt("lvl",levelViews.imgLvl); // num level
        state.putIntArray("resViews",levelViews.getViewVis()); // end game views
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
