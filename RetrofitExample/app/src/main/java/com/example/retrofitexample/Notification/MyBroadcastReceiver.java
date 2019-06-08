package com.example.retrofitexample.Notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.retrofitexample.MainActivity;
import com.example.retrofitexample.R;

import java.util.Random;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //Intent newIntend = new Intent(context,NotificationActivity.class);
        Intent newIntend = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,newIntend,0);

        if (action.equals("pe.edu.cibertec.broadcast.ACTION")) {
            Toast.makeText(context, "Acción ocurrió", Toast.LENGTH_SHORT).show();

            NotificationCompat.Builder notification = new NotificationCompat
                    .Builder(context,"Canal")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notificaion nueva")
                    .setContentText("Esta es una nueva notificacion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(0,notification.build());

        }

        if (action.equals("android.intent.action.AIRPLANE_MODE"))
        {
            NotificationCompat.Builder notification = new NotificationCompat
                    .Builder(context,"Canal")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Notificaion modo avion")
                    .setContentText("Modo Avion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            Random random = new Random();
            int unique_id = random.nextInt(999);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(unique_id,notification.build());
        }
    }
}
