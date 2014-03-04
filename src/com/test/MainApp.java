package com.test;

import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;

public class MainApp extends Application {
	
    private static final String parse_app_id = "4UgZtiv4tiEQrmPVGsct6XS6SVLGnrXA0kNggThY";
    private static final String parse_client_key = "P4L7TLK7dlWh94cUTR6R0GYs8mDos5savWVlWXqV";

	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, parse_app_id, parse_client_key);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
}
