package es.urjc.mov.jluzon.thermos;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintGraph {
    private static boolean mExternalStorageAvaiable = false;
    private static boolean mExternalStorageAWriteable = false;
    private static String state = Environment.getExternalStorageState();
    private static String myDir = "Thermos";

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

    static void writetoFile(Context c, String add, String pc){
        checkdExternalStorageRead();
        if (mExternalStorageAWriteable) {
            File dir = c.getExternalFilesDir(myDir);
            File f = new File(dir, pc+".txt");
            try {
                FileOutputStream file = new FileOutputStream(f, true);
                DataOutputStream dout = new DataOutputStream(file);
                dout.writeBytes(add+"\n");
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static File[] readFiles(Context c){
        File [] files= null;
        checkdExternalStorageRead();
        if (mExternalStorageAvaiable) {
            File dir = c.getExternalFilesDir(myDir);
            Log.d("OVER","PATH: "+dir);
            files = dir.listFiles();
        }
        return files;
    }
}
