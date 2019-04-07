package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TopScores {
    private static final int indexName = 0;
    private static final int indexScore = 5;
    private static final int indexTimes = 4;

    private static boolean mExternalStorageAvaiable = false;
    private static boolean mExternalStorageAWriteable = false;
    private static String state = Environment.getExternalStorageState();
    private static String myDir = "Xorapp";
    private static String myFile = "myData.txt";
    private static ArrayList<Player> players;

    static String getMyDir(){
        return myDir;
    }

    static String getMyFile(){
        return myFile;
    }

    static int getIndexName(){return indexName;}
    static int getIndexScore(){return indexScore;}
    static int getIndexTimes(){return indexTimes;}

    private static void checkdExternalStorageRead(){
        if (Environment.MEDIA_MOUNTED.equals(state)){
            //podemos leer y escribir
            mExternalStorageAvaiable = mExternalStorageAWriteable = true;
        }else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            mExternalStorageAvaiable = true;
            mExternalStorageAWriteable = false;
        }else{
            mExternalStorageAvaiable = mExternalStorageAWriteable = false;
        }
    }

    private static void readFromFile(Context c) {
        checkdExternalStorageRead();
        if (mExternalStorageAvaiable) {
            File dir = c.getExternalFilesDir(myDir);
            File file = new File(dir, myFile);
            try {
                FileReader f = new FileReader(file);
                BufferedReader br = new BufferedReader(f);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    parseToPlayer(strLine);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writetoFile(Context c,int[] times,String player,int score){
        players = new ArrayList<>();
        readFromFile(c);
        checkPlayer(player,times,score);
        Collections.sort(players);
        if (mExternalStorageAWriteable) {
            File dir = c.getExternalFilesDir(myDir);
            File f = new File(dir, myFile);
            try {
                FileOutputStream file = new FileOutputStream(f, false);
                DataOutputStream dout = new DataOutputStream(file);
                for(int i=0;i<players.size();i++){
                    dout.writeBytes(players.get(i).toString()+"\n");
                }
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkPlayer(String name, int[]times,int score){
        int i;
        boolean founded = false;
        Player p = new Player(name,times,score);

        for(i=0;i<players.size();i++){
            if(players.get(i).getPlayerName().equals(name)){
                founded = true;
                break;
            }
        }

        if(!founded){
            players.add(p);
            return;
        }

        if(founded && players.get(i).getScore()<score){
            players.set(i,p);
        }
    }

    private static void parseToPlayer(String line){
        Player p;
        String player[] = line.split(",");
        int t[] = new int[4];
        for(int i=0;i<indexTimes;i++){
            t[i]= Integer.parseInt(player[i+1]);
        }
        p = new Player(player[indexName],t,Integer.parseInt(player[indexScore]));
        players.add(p);
    }
}
