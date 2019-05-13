package es.urjc.mov.jluzon.thermos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThermoService extends IntentService {
    private int pcThemp;
    private boolean conexionFail;
    private NotificationManager mNM;
    public static final int NOTIFICATION_ID = 1;
    private final String SERVERIP = "212.128.254.193";

    public ThermoService() {
        super("ThermoService");
        Log.d("OVER","Iniciando Service");
    }


    private void getThemp(String pc){
        Socket s;
        DataOutputStream out;
        DataInputStream in;
        try{
            s = new Socket(pc,ActivePcsActivity.SERVERPORT);
            s.setSoTimeout(2000);
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
            Messages m = new Messages.ReqMsg();
            out.writeUTF(m.buildMsg());

            //wait the answer
            String message = in.readUTF();
            Log.d("OVER", "Respuesta: "+message);
            pcThemp = Integer.parseInt(message)/1000;

            out.close();
            s.close();

        }catch (ConnectException e) {
            Log.d("OVER", "Connection refused " + e.getMessage());
            conexionFail = true;
            e.printStackTrace();
        }catch (UnknownHostException e){
            Log.d("OVER","cannot connect to host " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("OVER", "Socket: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void connect(String ipc){
        final String pc = ipc;
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
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(;;){
            connect(SERVERIP);
            Log.d("OVER","ThempService: "+pcThemp);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(pcThemp > 20 || conexionFail){
                break;
            }
        }
        //sendNotification();
        Log.d("OVER","No haciendo nada");
    }

    private void sendNotification(){
        Log.d("OVER","Handler");
        Log.d("OVER","Creando notificacion");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        Notification notification = new Notification.Builder(ThermoService.this)
                .setTicker("TICKER")
                .setContentTitle("Notification")
                .setContentText("Themperatura: "+pcThemp)
                .setSmallIcon(R.drawable.ic_casino_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        Log.d("OVER","Notificando");
        mNM.notify(NOTIFICATION_ID,notification);
        Log.d("OVER","Saliendo");
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("OVER","StopSelf");
        //mNM.cancel(NOTIFICATION_ID);
    }
}
