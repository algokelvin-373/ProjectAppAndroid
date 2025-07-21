package com.algokelvin.timerbackgroundservice;

public class Utils {
    public static String formatTime(long millis) {
        int hours = (int) (millis / 1000 / 3600);
        int minutes = (int) ((millis / 1000) / 60) % 60;
        int seconds = (int) (millis / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
