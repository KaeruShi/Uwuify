package kaerushi.weeabooify.uwuify.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.HomePage;
import com.topjohnwu.superuser.Shell;

public class BackgroundService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "Background Service";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HomePage.isServiceRunning = true;

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        showNotification();
    }

    public void showNotification() {
        Intent notificationIntent = new Intent();
        notificationIntent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        notificationIntent.putExtra(Settings.EXTRA_CHANNEL_ID, NOTIFICATION_CHANNEL_ID);

        PendingIntent pendingIntent = null;


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_uwu)
                .setContentTitle("Background Service")
                .setContentText("Touch me, Please >.<")
                .setContentIntent(pendingIntent)
                .setSound(null, AudioManager.STREAM_NOTIFICATION)
                .setColor(getResources().getColor(R.color.ic_launcher_background));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public void createChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_LOW);
        channel.setDescription("You can turn off these notifications.");
        notificationManager.createNotificationChannel(channel);
    }

}