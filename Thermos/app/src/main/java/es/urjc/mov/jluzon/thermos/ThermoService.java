package es.urjc.mov.jluzon.thermos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class ThermoService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private static final int SLEEP = 1000;
    private static final int MAXTRIES = 5;
    private final String THEMPTXT = "THEMP ALERT";
    private static boolean stopRunning;

    public ThermoService() {
        super("ThermoService");
        stopRunning = false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String txt,titl;
        String pcThemp;
        int cnt = 0;

        String [] cfg = Utility.readCfg(ThermoService.this);

        if(!Utility.checkCfg(cfg)){
            titl = "NO CONFIGURATION";
            txt = "Please set some configuration to start service";
            sendNotification(titl,txt);
            stopSelf();
            return;
        }

        String serverIP = cfg[Utility.IPPOS];
        String alert = cfg[Utility.ALERTPOS];
        for(;;){

            if(stopRunning){
                stopSelf();
                break;
            }

            pcThemp = Utility.connect(serverIP);
            if(pcThemp == null){
                cnt++;
            }else{
                cnt = 0;
            }

            if(cnt == MAXTRIES){
                titl = "HOST UNREACHABLE";
                txt = "Can not connect to IP";
                sendNotification(titl,txt);
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
                    titl = THEMPTXT;
                    txt = "PC: "+serverIP+" Themp: "+pcThemp+ " Alert in: "+alert;
                    sendNotification(titl,txt);
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String notfTitle,String notfText){
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(this,ConfigurationActivity.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent notificationIntent = PendingIntent.getActivity(
                this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(notfTitle.equals(THEMPTXT)){
            notificationIntent = null;
        }

        Notification notification = new Notification.Builder(ThermoService.this)
                .setContentTitle(notfTitle)
                .setContentText(notfText)
                .setSmallIcon(R.drawable.ic_new_releases_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationIntent)
                .setAutoCancel(true)
                .build();
        mNM.notify(NOTIFICATION_ID,notification);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopRunning =  true;
    }
}
