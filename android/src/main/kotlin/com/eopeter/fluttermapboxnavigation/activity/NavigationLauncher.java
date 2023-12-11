package com.eopeter.fluttermapboxnavigation.activity;

import android.app.Activity;
import android.content.Intent;

import com.eopeter.fluttermapboxnavigation.models.Waypoint;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Map;

public class NavigationLauncher {
    public static final String KEY_ADD_WAYPOINTS = "com.my.mapbox.broadcast.ADD_WAYPOINTS";
    public static final String KEY_STOP_NAVIGATION = "com.my.mapbox.broadcast.STOP_NAVIGATION";
    public static void startNavigation(Activity activity, List<Waypoint> wayPoints, Map predefinedRoute) {
        Intent navigationIntent = new Intent(activity, NavigationActivity.class);
        navigationIntent.putExtra("waypoints", (Serializable) wayPoints);

        // Write predefinedRoute to file since it can be too big to pass as an intent extra
        if(predefinedRoute != null) {
            File file = new File(activity.getDataDir().getPath(), "predefinedRoute.json");
            try {
                Writer output;
                output = new BufferedWriter(new FileWriter(file));
                output.write(new JSONObject(predefinedRoute).toString());
                output.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            navigationIntent.putExtra("predefinedRoute", file.getAbsolutePath());
        }

        activity.startActivity(navigationIntent);
    }

    public static void addWayPoints(Activity activity, List<Waypoint> wayPoints) {
        Intent navigationIntent = new Intent(activity, NavigationActivity.class);
        navigationIntent.setAction(KEY_ADD_WAYPOINTS);
        navigationIntent.putExtra("isAddingWayPoints", true);
        navigationIntent.putExtra("waypoints", (Serializable) wayPoints);
        activity.sendBroadcast(navigationIntent);
    }

    public static void stopNavigation(Activity activity) {
        Intent stopIntent = new Intent();
        stopIntent.setAction(KEY_STOP_NAVIGATION);
        activity.sendBroadcast(stopIntent);
    }
}
