package es.urjc.mov.jluzon.thermos;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class PaintGraph {
    private static boolean mExternalStorageAvaiable = false;
    private static boolean mExternalStorageAWriteable = false;
    private static String state = Environment.getExternalStorageState();
    private static String myDir = "Thermos";
    private static ArrayList<String>temperatures;
    static final int maxTemperatures = 24;

    static String getDir(){
        return myDir;
    }

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

    private static void readFromFile(Context c, ArrayList<String> temp, String pc) {
        checkdExternalStorageRead();
        if (mExternalStorageAvaiable) {
            File dir = c.getExternalFilesDir(myDir);
            File file = new File(dir, pc+".txt");
            try {
                FileReader f = new FileReader(file);
                BufferedReader br = new BufferedReader(f);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    temp.add(strLine);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writetoFile(Context c, ArrayList<String> temps, String pc){
        checkdExternalStorageRead();
        if (mExternalStorageAWriteable) {
            File dir = c.getExternalFilesDir(myDir);
            File f = new File(dir, pc+".txt");
            try {
                FileOutputStream file = new FileOutputStream(f, false);
                DataOutputStream dout = new DataOutputStream(file);
                for(int i=0;i<temps.size();i++){
                    dout.writeBytes(temps.get(i)+"\n");
                }
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void upgrade(String newTh){
        if(temperatures.size() == maxTemperatures){
            temperatures.remove(0);
        }
        temperatures.add(newTh);
    }

    static void update(Context c, String newTh, String pc){
        temperatures = new ArrayList<>();
        readFromFile(c,temperatures,pc);
        upgrade(newTh);
        writetoFile(c,temperatures,pc);
    }

    static File[] readFiles(Context c){
        File [] files= null;
        checkdExternalStorageRead();
        if (mExternalStorageAvaiable) {
            File dir = c.getExternalFilesDir(myDir);
            files = dir.listFiles();
        }
        return files;
    }
}
