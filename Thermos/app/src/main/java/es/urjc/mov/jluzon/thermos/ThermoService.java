package es.urjc.mov.jluzon.thermos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class ThermoService extends IntentService {
    private String pcThemp;
    private NotificationManager mNM;
    public static final int NOTIFICATION_ID = 1;
    //private final String SERVERIP = "212.128.254.193";
    private final String SERVERIP = "192.168.1.48";

    public ThermoService() {
        super("ThermoService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        for(;;){
            pcThemp = Utility.connect(SERVERIP);
            Log.d("OVER","ThempService: "+pcThemp);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(Integer.parseInt(pcThemp) > 20){
                break;
            }
        }
        sendNotification();
    }

    private void sendNotification(){
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        Notification notification = new Notification.Builder(ThermoService.this)
                .setContentTitle("Notification")
                .setContentText("Themperatura: "+pcThemp)
                .setSmallIcon(R.drawable.ic_casino_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        mNM.notify(NOTIFICATION_ID,notification);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("OVER","StopSelf");
        //mNM.cancel(NOTIFICATION_ID);
    }
}
