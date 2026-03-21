package fr.neamar.kiss.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Monitors system resources like RAM and logs permission creep.
 */
public class ResourceMonitor {

    private static final long WARNING_RAM_THRESHOLD = 50 * 1024 * 1024; // 50MB
    private static final long CRITICAL_RAM_THRESHOLD = 100 * 1024 * 1024; // 100MB

    /**
     * Tracks RAM usage and logs warnings if thresholds are exceeded.
     * @param context Android context
     */
    public static void trackRAMUsage(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        long usedMemory = memoryInfo.totalMem - memoryInfo.availMem;

        if (usedMemory > CRITICAL_RAM_THRESHOLD) {
            NotificationManager.sendHighResourceAlert("Critical RAM usage: " + formatBytes(usedMemory));
        } else if (usedMemory > WARNING_RAM_THRESHOLD) {
            NotificationManager.sendHighResourceAlert("High RAM usage: " + formatBytes(usedMemory));
        }
    }

    /**
     * Logs permission creep for a given permission.
     * @param permission The permission being requested
     */
    public static void logPermissionCreep(String permission) {
        // In a real implementation, this would log to a file or database
        System.out.println("Permission creep detected: " + permission);
    }

    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
}