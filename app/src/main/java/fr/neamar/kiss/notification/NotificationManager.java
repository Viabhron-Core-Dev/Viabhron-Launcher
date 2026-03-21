package fr.neamar.kiss.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/**
 * Manages system notifications for resource monitoring and firewall alerts.
 */
public class NotificationManager {

    private static final String CHANNEL_ID = "viabhron_alerts";
    private static final String CHANNEL_NAME = "Viabhron Alerts";

    /**
     * Initializes the notification channel.
     * @param context Android context
     */
    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(android.app.NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * Sends a high resource usage alert.
     * @param message The alert message
     */
    public static void sendHighResourceAlert(String message) {
        android.app.NotificationManager manager = (android.app.NotificationManager) 
            ContextHolder.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(ContextHolder.getContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("High Resource Usage")
            .setContentText(message)
            .setPriority(Notification.PRIORITY_HIGH)
            .build();

        manager.notify(1, notification);
    }

    /**
     * Sends a network block alert.
     * @param url The blocked URL
     */
    public static void sendNetworkBlockAlert(String url) {
        android.app.NotificationManager manager = (android.app.NotificationManager) 
            ContextHolder.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(ContextHolder.getContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Network Call Blocked")
            .setContentText("Blocked: " + url)
            .setPriority(Notification.PRIORITY_HIGH)
            .build();

        manager.notify(2, notification);
    }

    /** Simple context holder for static methods */
    private static class ContextHolder {
        private static Context context;

        public static void setContext(Context ctx) {
            context = ctx;
        }

        public static Context getContext() {
            return context;
        }
    }
}