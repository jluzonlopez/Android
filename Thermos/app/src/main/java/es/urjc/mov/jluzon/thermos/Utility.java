package es.urjc.mov.jluzon.thermos;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class Utility {
    //Old PaintGraph
    private static boolean mExternalStorageAvaiable = false;
    private static boolean mExternalStorageAWriteable = false;
    private static String state = Environment.getExternalStorageState();
    private static String myDir = "Thermos";
    private static String myCfg = "CfgThermos";
    private static ArrayList<String> temperatures;
    static final int maxTemperatures = 24;
    private static final int TIMEOUT = 2*1000; //time in sec
    static final int IPPOS = 0;
    static final int ALERTPOS = 1;
    private static final int CFGSIZE = 2;
    private static String pcThemp;
    private static final int LOWERIPNUM = 0;
    private static final int MAXERIPNUM = 255;

    private static void getThemp(String pc){
        Socket s;
        DataOutputStream out;
        DataInputStream in;
        try{
            pcThemp = null;
            s = new Socket();
            s.connect(new InetSocketAddress(pc,ActivePcsActivity.SERVERPORT),TIMEOUT);
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
            Messages m = new Messages.ReqMsg();
            out.writeUTF(m.buildMsg());

            //wait the answer
            String message = in.readUTF();
            pcThemp = Integer.parseInt(message)/1000+"";

            out.close();
            s.close();

        }catch (ConnectException e) {
            Log.d("OVER", "Connection refused " + e.getMessage());
            e.printStackTrace();
        }catch (UnknownHostException e){
            Log.d("OVER","Cannot connect to host " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("OVER", "Socket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static String connect(String ipc){
        final String pc = ipc;
        Log.d("OVER","Connecting to: "+ipc);
        Thread c = new Thread() {
            @Override
            public void run() {
                getThemp(pc);
            }
        };
        c.start();
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pcThemp;
    }

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

    static boolean checkAlert(String alert){
        try{
            return (Integer.parseInt(alert) > 0 && Integer.parseInt(alert) < 100 && !alert.equals(""));
        }catch (NumberFormatException e){
            return false;

        }
    }

    static boolean checkIP(String ip){
        if(ip == null){
            return false;
        }
        String ipNumbers[] = ip.split("\\.");
        boolean okIp = true;
        try{
            for(int i=0;i<ipNumbers.length;i++){
                okIp &=(Integer.parseInt(ipNumbers[i]) >=LOWERIPNUM && Integer.parseInt(ipNumbers[i]) <= MAXERIPNUM);
            }
        }catch (NumberFormatException e){
            okIp = false;
            e.printStackTrace();
        }

        return (ipNumbers.length == 4 && okIp);
    }

    // Configurations methods

    static void setCfg(Context c, String ip, String alert){
        checkdExternalStorageRead();
        if (mExternalStorageAWriteable) {
            File dir = c.getExternalFilesDir(myDir);
            File f = new File(dir, myCfg+".txt");
            try {
                FileOutputStream file = new FileOutputStream(f, false);
                DataOutputStream dout = new DataOutputStream(file);
                dout.writeBytes(ip+"\n"+alert);
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String[] readCfg(Context c){
        checkdExternalStorageRead();
        String [] cfg = new String[CFGSIZE];
        int pos = 0;
        if (mExternalStorageAvaiable) {
            File dir = c.getExternalFilesDir(myDir);
            File file = new File(dir, myCfg+".txt");
            try {
                FileReader f = new FileReader(file);
                BufferedReader br = new BufferedReader(f);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    cfg[pos] = strLine;
                    pos++;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cfg;
    }

    static boolean checkCfg(String [] cfg){
        String ip = cfg[IPPOS];
        String alert = cfg[ALERTPOS];

        return checkIP(ip) && checkAlert(alert);
    }

}
