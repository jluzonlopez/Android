package es.urjc.mov.jluzon.thermos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class ThermoService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private static final int SLEEP = 1000;
    private static final int MAXTRIES = 5;

    public ThermoService() {
        super("ThermoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String txt;
        String pcThemp;
        int cnt = 0;

        String [] cfg = Utility.readCfg(ThermoService.this);


        if(!Utility.checkCfg(cfg)){
            txt = "No CFG configure, please set some configuration";
            sendNotification(txt);
            stopSelf();
            return;
        }

        String serverIP = cfg[Utility.IPPOS];
        String alert = cfg[Utility.ALERTPOS];
        for(;;){

            pcThemp = Utility.connect(serverIP);
            if(pcThemp == null){
                cnt++;
            }else{
                cnt = 0;
            }

            if(cnt == MAXTRIES){
                txt = "Can not connect to IP";
                sendNotification(txt);
                stopSelf();
                break;
            }

            try{
                Thread.sleep(SLEEP);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{
                if(Integer.parseInt(pcThemp) > Integer.parseInt(alert)){
                    txt = "PC: "+serverIP+" Themp: "+pcThemp+ " Alert in: "+alert;
                    sendNotification(txt);
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String notfText){
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(this,MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(
                this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(ThermoService.this)
                .setContentTitle("Alert")
                .setContentText(notfText)
                .setSmallIcon(R.drawable.ic_new_releases_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationIntent)
                .build();
        mNM.notify(NOTIFICATION_ID,notification);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //stopSelf();
        Log.d("OVER","StopSelf");
    }
}
