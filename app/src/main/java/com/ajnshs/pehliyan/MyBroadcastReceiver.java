package com.ajnshs.pehliyan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.Random;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "anokhi_paheliyan";
    private static final String CHANNEL_NAME = "Anokhi Paheliyan";
    private static final String CHANNEL_DEC = "Anokhi paheliyan notification";

    ArrayList<Pahli> data;

    int randCateogry;
    int randPaheli;

    @Override
    public void onReceive(Context context, Intent intent) {
        Random random = new Random();
        randCateogry = random.nextInt(Categories.getJsonFilePath().length);
        DataProvider dataProvider = new DataProvider(context, randCateogry);
        data = dataProvider.getPehlis();

        randPaheli = random.nextInt(data.size());

        //Channel create
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DEC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("बूझो तो जाने!!!")
                        .setContentText(data.get(randPaheli).getQues())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(data.get(randPaheli).getQues()))
                        .setAutoCancel(true);

        Intent mainIntent = new Intent(context, MainActivity.class);

        Intent resultIntent = new Intent(context, PahliActivity.class);
        resultIntent.putExtra("posCatg", randCateogry);
        resultIntent.putExtra("posItem", randPaheli);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

//        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(mainIntent);
        stackBuilder.addNextIntent(resultIntent);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
