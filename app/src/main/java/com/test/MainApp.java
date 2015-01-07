package com.test;

import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;

public class MainApp extends Application {
	
    private static final String parse_app_id = "YOUR_APP_ID";
    private static final String parse_client_key = "YOUR_CLIENT_KEY";

	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, parse_app_id, parse_client_key);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
}
