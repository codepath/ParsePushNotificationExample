package com.test;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Iterator;

public class MyCustomReceiver extends BroadcastReceiver {
   private static final String TAG = "MyCustomReceiver";
   public static final String intentAction = "com.parse.push.intent.RECEIVE";

   @Override
   public void onReceive(Context context, Intent intent) {
       if (intent == null) {
           Log.d(TAG, "Receiver intent null");
       } else {
          // Parse push message and handle accordingly
          processPush(context, intent);
       }
   }
   
   private void processPush(Context context, Intent intent) {
       String action = intent.getAction();
       Log.d(TAG, "got action " + action );
       if (action.equals(intentAction))
       {
           String channel = intent.getExtras().getString("com.parse.Channel");
           try {       	
               JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
               Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
               // Iterate the parse keys if needed
               Iterator<String> itr = json.keys();
               while (itr.hasNext()) {
                   String key = (String) itr.next();
         	   // Extract custom push data
         	   if (key.equals("customdata")) {    
         	 	// Handle push notification by invoking activity directly
         		launchSomeActivity(context, json.getString(key));
         		// OR trigger a broadcast to activity
         		triggerBroadcastToActivity(context);
         		// OR create a local notification
         		createNotification(context);
         	    }
                    Log.d(TAG, "..." + key + " => " + json.getString(key));
               }
       	    } catch (JSONException ex) {
       		Log.d(TAG, "JSON failed!");
       	    }
       }
   }
   
   public static final int NOTIFICATION_ID = 45;
   // Create a local dashboard notification to tell user about the event
   private void createNotification(Context context) {
       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(
               R.drawable.ic_launcher).setContentTitle("Successfully logged in");
       NotificationManager mNotificationManager = (NotificationManager) context
          		.getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify(45, mBuilder.build());
   }
   
   // Handle push notification by invoking activity directly
   private void launchSomeActivity(Context context, String datavalue) {
       Intent pupInt = new Intent(context, ShowPopUp.class);
       pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
       pupInt.putExtra("customdata", datavalue);
       context.getApplicationContext().startActivity(pupInt);	
   }
   
   // Handle push notification by sending a local broadcast 
   // to which the activity subscribes to
   private void triggerBroadcastToActivity(Context context) {
       LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(intentAction));
   }
}
