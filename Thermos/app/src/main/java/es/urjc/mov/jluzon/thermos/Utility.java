package es.urjc.mov.jluzon.thermos;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class Utility {
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
            s.connect(new InetSocketAddress(pc,ActivePcsActivity.SERVERPORT),2000);
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
            Log.d("OVER","cannot connect to host " + e);
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

    static boolean checkAlert(String alert){
        return (Integer.parseInt(alert) > 0 && Integer.parseInt(alert) < 100);
    }

    static boolean checkIP(String ip){
        String ipNumbers[] = ip.split("\\.");
        boolean okIp = true;
        for(int i=0;i<ipNumbers.length;i++){
            okIp &=(Integer.parseInt(ipNumbers[i]) >=LOWERIPNUM && Integer.parseInt(ipNumbers[i]) <= MAXERIPNUM);
        }
        return (ipNumbers.length == 4 && okIp);
    }
}
