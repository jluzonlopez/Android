package es.urjc.mov.jluzon.thermos;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class ThermoService extends IntentService {

    public ThermoService() {
        super("ThermoService");
        Log.d("OVER","Iniciando Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }

    private void sendNotification(){
        Log.d("OVER","Handler");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ThermoService.this)
                .setContentText("Ejemplo")
                .setContentTitle("Ejemplo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //NotificationManagerCompat notificationMAnager = NotificationManagerCompat.from(ThermoService.this);
        //notificationMAnager.notify(0,builder.build());
    }
}
